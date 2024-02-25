package eu.jdanek.coffeemat.component;

import eu.jdanek.coffeemat.enums.ValidCoinEnum;

import java.util.*;

/**
 * Trida reprezentujici zarizeni pro vydej minci
 */
public class CoinDispenserComponent {

    private final DisplayComponent display;
    private final EnumSet<ValidCoinEnum> VALID_COINS;

    public CoinDispenserComponent(DisplayComponent displayComponent, EnumSet<ValidCoinEnum> validCoins) {
        this.display = displayComponent;
        this.VALID_COINS = validCoins;
    }

    /**
     * Vraceni minci zakaznikovi
     */
    public void dispense(int coins) {

        // pokud jsou mince k vraceni nulové, nic nevykreslujeme
        if (coins <= 0) {
            return;
        }

        // seradit v mince v poradi od nejvetsi po nejmensi (50...1)
        List<Integer> sortedValidCoins = this.VALID_COINS.stream()
                .map(ValidCoinEnum::getValue)// ziskani hodnot klicu
                .sorted(Comparator.reverseOrder()) // seradit v opacnem poradi
                .toList(); // vratit jako list

        // predpripravit asociativni mapu vysledku
        Map<Integer, Integer> result = new HashMap<>();
        // cyklicke vlozeni vsech platnych minci a jejich nuloveho poctu
        for (int i : sortedValidCoins) {
            // vlozit zaznam (hodnota, pocet)
            result.put(i, 0);
        }

        // projit hodnoty validnich minci
        // a vypocitat vracene mince, vypocit je tak aby se vracely vzdy nejvyssi mozne hodnoty
        for (int coinValue : sortedValidCoins) {
            // pocet minci potrebnych k vraceni aktualni castky
            int count = (coins / coinValue);
            // pridat pocet minci do vysledku
            result.put(coinValue, count);
            // odecitat vracene mince od vysledku
            coins -= (count * coinValue);
        }

        // kvuli serazeni hodnot, jinak je to vraci na prehazene
        Map<Integer, Integer> sortedResult = new TreeMap<>(result);

        // vypis seznamu vracenych minci
        this.display.debug("DISPENSER vratí následující mince:");
        for (Map.Entry<Integer, Integer> entry : sortedResult.entrySet()) {
            this.display.debug(entry.getValue() + " x " + entry.getKey() + " Kč");
        }
    }
}
