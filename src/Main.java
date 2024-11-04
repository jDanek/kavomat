import eu.jdanek.coffeemat.Automat;
import eu.jdanek.coffeemat.Recipe;
import eu.jdanek.coffeemat.component.*;
import eu.jdanek.coffeemat.enums.ResourceEnum;
import eu.jdanek.coffeemat.enums.ValidCoinEnum;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) {

        Automat automat = getAutomat();

        // nastaveni zasobniku surovin
        automat.setMagazines(
                // dalsi napad oproti prikladu 1
                // nekonecny zasobnik vody -> napr. automat pripojeny k vode
                new InfiniteStorageComponent(ResourceEnum.WATER),

                //new MagazineComponent(ResourceEnum.WATER, 400),
                new StorageComponent(ResourceEnum.MILK, 150),
                new StorageComponent(ResourceEnum.COFFEE, 300),

                // par dalsich zasobniku oproti prikladu 1
                new StorageComponent(ResourceEnum.SUGAR, 100),
                new StorageComponent(ResourceEnum.CHOCOLATE, 100),
                new StorageComponent(ResourceEnum.TEA, 100)
        );

        // nastaveni receptu pro jednotlive napoje
        automat.setRecipes(
                new Recipe("Espresso", 40, new HashMap<>() {{
                    put(ResourceEnum.WATER, 50);
                    put(ResourceEnum.MILK, 0);
                    put(ResourceEnum.COFFEE, 18);
                }}),
                new Recipe("Latte", 50, new HashMap<>() {{
                    put(ResourceEnum.WATER, 200);
                    put(ResourceEnum.MILK, 150);
                    put(ResourceEnum.COFFEE, 24);
                }}),
                new Recipe("Cappuccino", 60, new HashMap<>() {{
                    put(ResourceEnum.WATER, 250);
                    put(ResourceEnum.MILK, 100);
                    put(ResourceEnum.COFFEE, 24);
                }}),

                // par dalsich receptu oproti prikladu 1
                new Recipe("Moccaccino", 45, new HashMap<>() {{
                    put(ResourceEnum.WATER, 200);
                    put(ResourceEnum.MILK, 150);
                    put(ResourceEnum.COFFEE, 24);
                    put(ResourceEnum.CHOCOLATE, 30);
                }}),
                new Recipe("Tea", 20, new HashMap<>() {{
                    put(ResourceEnum.WATER, 200);
                    put(ResourceEnum.TEA, 20);
                    put(ResourceEnum.SUGAR, 5);
                }}),
                new Recipe("Chocolate", 15, new HashMap<>() {{
                    put(ResourceEnum.WATER, 200);
                    put(ResourceEnum.CHOCOLATE, 20);
                    put(ResourceEnum.SUGAR, 5);
                }})
        );

        // spustení automatu po inicializaci
        automat.start();
    }

    private static Automat getAutomat() {
        DisplayComponent displayComponent = new DisplayComponent();

        // vytvoření instance automatu
        Automat automat = new Automat(
                displayComponent, // komponent pro zobrazování informaci
                new CoinReceiverComponent(displayComponent, ValidCoinEnum.getEnumSet()), // komponent pro vkladani minci
                new CoinDispenserComponent(displayComponent, ValidCoinEnum.getEnumSet()), // komponent pro vydavani minci
                true // debug rezim
        );
        return automat;
    }
}