package eu.jdanek.coffeemat.component;

/**
 * Trida zastupujici display automatu
 */
public class DisplayComponent {

    protected boolean debug = false;

    /**
     * Nastaveni debug rezimu
     */
    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    /**
     * Vykresleni debug informace
     */
    public void debug(String message) {
        if (this.debug) {
            System.out.println("===== DEBUG ===== : " + message);
        }
    }

    /**
     * Vykresleni bezne informace
     */
    public void show(String message) {
        System.out.println(message);
    }

    /**
     * Vykresleni dotazu na uzivatele
     */
    public void userInput(String message) {
        System.out.print(message);
    }
}
