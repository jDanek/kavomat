package eu.jdanek.coffeemat.component;

import eu.jdanek.coffeemat.enums.ResourceEnum;

/**
 * Trida zasobniku surovin
 */
public class InfiniteMagazineComponent extends StorageComponent {

    public InfiniteMagazineComponent(ResourceEnum resourceType) {
        super(resourceType, 999999);
    }

    /**
     * Overeni zda ma zasobnik potrebny pocet suroviny
     */
    public boolean hasAmount(int amount) {
        // nekonecny zasobnik ma vzdy dostatek suroviny
        return true;
    }

    /**
     * Pouziti / odecteni suroviny ze zasobniku
     */
    public void use(int amount) {
        // nekonecny zasobnik nemusi snizovat stav suroviny
    }
}
