package eu.jdanek.coffeemat.component;

import eu.jdanek.coffeemat.enums.ValidCoinEnum;

import java.util.EnumSet;
import java.util.List;
import java.util.Scanner;

/**
 * Trida reprezentujici zarizeni pro vkladani minci
 */
public class CoinReceiverComponent {


    private final DisplayComponent display;
    private final EnumSet<ValidCoinEnum> VALID_COINS;
    private List<Integer> validCoinsCache = null;

    // stav vlozenych minci do automatu
    private int insertedCoins = 0;

    public CoinReceiverComponent(DisplayComponent display, EnumSet<ValidCoinEnum> validCoins) {
        this.display = display;
        this.VALID_COINS = validCoins;
    }

    /**
     * Obsluha vkladani minci
     */
    public void insertCoins() {
        // uzivatelsky vstup
        Scanner userInput = new Scanner(System.in);
        this.display.userInput("Prosím vložte mince...\n\n");

        // cyklicke dotatovani na vkladani jednotlivych hodnot
        for (ValidCoinEnum validCoin : this.VALID_COINS) {
            int coinValue = validCoin.getValue();

            // cyklicka kontrola ze se jedna o ciselne hodnoty
            int intValue = -1;
            do {
                this.display.userInput("Kolik " + coinValue + " Kč chcete vložit?: > ");
                String next = userInput.next();

                try {
                    // pokud neni ciselna hodnota, vyhodi vyjimku a vrati se to zase na dotaz
                    intValue = Integer.parseInt(next);
                } catch (NumberFormatException exp) {
                }
            } while (intValue < 0);

            // pokud je vse v poradku, mince pricteme
            int amount = intValue;
            this.addCoin(coinValue, amount);
        }
    }

    /**
     * Pouziti vlozenych minci, pokud neni dostatek, vrati false
     *
     * @param amount pozadovana castka ktera ma byt odectena od vlozenych prostredku
     */
    public boolean useCoins(int amount) {
        // pokud je vlozena castke mensi nez pozadovana
        if (this.insertedCoins < amount) {
            return false;
        }
        // pokud je dostatek minci, tak je odecteme a vratime true
        this.insertedCoins -= amount;
        return true;
    }

    public void resetCoins() {
        this.insertedCoins = 0;
    }

    /**
     * Ziskani poctu vhozenych penez (celkova castka)
     */
    public int getInsertedCoins() {
        return this.insertedCoins;
    }

    /**
     * Vratit pocet zbyvanych minci
     */
    public int getRemainingCoins() {
        return this.insertedCoins;
    }

    /**
     * Pripocitavani hodnoty vlozenych minci
     *
     * @param coinValue hodnota mince
     * @param count     pocet minci teto hodnoty
     */
    private void addCoin(int coinValue, int count) {
        // pokud se nejedna o validni hodnotu mince, vyhodi vyjimku
        if (!this.isValidCoin(coinValue)) {
            throw new IllegalArgumentException("Neplatná hodnota mince: " + coinValue);
        }
        // pricteme celkovou hodnotu poctu minci
        this.insertedCoins += (count * coinValue);
    }

    /**
     * Validace hodnoty mince podle registrovych
     */
    private boolean isValidCoin(int coin) {
        // pokud neni inicializovano, naplnime jinak se tato podminka preskoci
        // a pouziji se hodnoty drive nastavene
        if (this.validCoinsCache == null) {
            this.validCoinsCache = this.VALID_COINS.stream()
                    .map(ValidCoinEnum::getValue)
                    .toList();
        }

        // kontrola existence hodnoty
        return this.validCoinsCache.contains(coin);
    }

}
