package eu.jdanek.coffeemat;

import eu.jdanek.coffeemat.component.CoinDispenserComponent;
import eu.jdanek.coffeemat.component.CoinReceiverComponent;
import eu.jdanek.coffeemat.component.DisplayComponent;
import eu.jdanek.coffeemat.component.StorageComponent;
import eu.jdanek.coffeemat.enums.ResourceEnum;
import eu.jdanek.coffeemat.util.DrinkMenuRenderer;

import java.util.*;

public class Automat {

    // components
    private final HashMap<ResourceEnum, StorageComponent> magazines = new HashMap<>();
    private final DisplayComponent display;
    private final CoinReceiverComponent coinReceiver;
    private final CoinDispenserComponent coinDispenser;


    private final HashMap<String, Recipe> recipes = new HashMap<>();

    public Automat(
            DisplayComponent display,
            CoinReceiverComponent coinReceiver,
            CoinDispenserComponent coinDispenser,
            boolean debug
    ) {
        this.display = display;
        this.coinReceiver = coinReceiver;
        this.coinDispenser = coinDispenser;

        this.display.setDebug(debug);
    }

    /**
     * Nastaveni zasobniku automatu
     */
    public void setMagazines(StorageComponent... magazines) {
        for (StorageComponent magazine : magazines) {
            this.magazines.put(magazine.getResourceType(), magazine);
        }
    }

    /**
     * Nastaveni receptu napoju
     */
    public void setRecipes(Recipe... recipes) {
        for (Recipe recipe : recipes) {
            this.recipes.put(recipe.getName().toLowerCase(), recipe);
        }
    }

    /**
     * Spustit automat
     */
    public void start() {
        // kontrola pripravenosti automatu
        if (!this.isReady()) {
            return;
        }

        // nastaveni plnych zasobniku po startu
        this.refillResources();

        // cyklus pro provozu
        while (true) {

            // vykresleni hlavicky
            this.renderStartHeader();
            // vykresleni nabidky napoju
            this.renderMenu();

            this.display.userInput("\nCo byste si dal/a?: > ");

            // uzivatelsky vstup - objednávka
            Scanner userInput = new Scanner(System.in);
            String userChoice = userInput.nextLine();

            // pokud uzivatel zada 'exit', ukonceni automatu
            if (userChoice.equalsIgnoreCase("exit")) {
                break;
            }

            // predat uzivatelskou volbu do rozhodovaci struktury
            this.selectMenu(userChoice.toLowerCase());
        }

    }

    private void renderStartHeader() {
        this.display.show("\n\n");
        this.display.show("##################################");
        this.display.show("#          KAVOMAT 3000          #");
        this.display.show("##################################");
    }

    private void selectMenu(String name) {
        // prepinani akci na zaklade uzivatelskeho vstupu
        switch (name) {
            case "report": // vypis stavu vsech zasobniku
                this.report();
                break;
            case "refill": // doplneni vsech zasobniku
                this.refillResources();
                break;
            default: // priprava napoje

                // kontrola zda je volba platna
                if (!recipes.containsKey(name)) {
                    // neplatná volba = preruseni programu
                    this.display.show("Neplatná volba!");
                    break;
                }

                // pokud je volba spravna, zahajime proces vyroby
                this.prepareDrink(recipes.get(name));
                break;
        }
    }

    /**
     * Vykreslit nabidku napoju
     */
    private void renderMenu() {
        DrinkMenuRenderer.renderMenu(recipes.values());
    }

    private boolean isReady() {
        List<String> errors = new ArrayList<>();

        if (this.magazines.isEmpty()) {
            errors.add("Automat nemá zásobníky surovin");
        }

        if (this.recipes.isEmpty()) {
            errors.add("Automat nemá nastavené recepty");
        }

        if (!errors.isEmpty()) {
            this.display.show("Automat mimo provoz!");
            for (String error : errors) {
                this.display.debug(error);
            }
            return false;
        }
        return true;
    }

    private void prepareDrink(Recipe recipe) {

        // kontrola zda na napoj ma automat dostatek surovin
        if (!this.checkResources(recipe)) {
            // pokud ne, vypsat chybu a ukoncit process
            this.display.debug("Nedostatek surovin pro výrobu!");
            return;
        }

        // vypiseme informaci o cene napoje
        this.display.show("\nCena nápoje: " + recipe.getCost() + " Kč");

        // vyzadame vlozeni minci
        this.coinReceiver.insertCoins();
        int insertedCoins = this.coinReceiver.getInsertedCoins();

        // vypiseme stav vlozenych minci
        this.display.debug("Vloženo: " + insertedCoins + " Kč");

        // pokusime se odecist od vlozenych minci cenu napoje
        if (!this.coinReceiver.useCoins(recipe.getCost())) {
            // pokud se nepodari, ukoncit proces
            this.display.show("\nNedostatek peněz!");
            // vratime vlozene nedostatecne prostredky
            this.coinDispenser.dispense(insertedCoins);
            this.coinReceiver.resetCoins();
            return;
        }

        // odecteme suroviny zasobniku, podle pozadavku receptu
        this.consumeResources(recipe);

        // podekovani za nakup
        this.display.show("\nDěkujeme za nákup nápoje: " + recipe.getName());

        // vraceni zbylych minci
        this.display.show("=========================================================");
        this.coinDispenser.dispense(this.coinReceiver.getRemainingCoins());
        this.coinReceiver.resetCoins();
        this.display.show("=========================================================");
    }

    /**
     * Kontrola dostupnosti vsech pozadovanych surovin podle receptu
     */
    private boolean checkResources(Recipe recipe) {
        for (Map.Entry<ResourceEnum, Integer> entry : recipe.getResources().entrySet()) {
            // pokud jakakoliv surovina neni dostupna, neni mozne napoj vyrobi a vracime false
            if (!this.magazines.get(entry.getKey()).hasAmount(entry.getValue())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Odecteni vsech pozadovanych surovin
     */
    private void consumeResources(Recipe recipe) {
        // odecteme v cyklu vsechny suroviny potrebne na vyrobu
        for (Map.Entry<ResourceEnum, Integer> entry : recipe.getResources().entrySet()) {
            this.magazines.get(entry.getKey()).use(entry.getValue());
        }
    }

    /**
     * Doplneni vsech zasobniku surovin
     */
    private void refillResources() {
        for (StorageComponent magazine : magazines.values()) {
            magazine.refill();
        }
    }

    /**
     * Vypis stav zasobniku
     */
    private void report() {
        for (StorageComponent magazine : magazines.values()) {
            this.display.show(magazine.getResourceType() + ": " + magazine.getAmount());
        }
    }
}
