package eu.jdanek.coffeemat.component;

import eu.jdanek.coffeemat.enums.ResourceEnum;

/**
 * Trida zasobniku surovin
 */
public class StorageComponent {

    private final ResourceEnum resourceType;

    private int capacity = 0; // celkova kapacita zasobniku
    private int amount = 0; // aktualni zasoba

    public StorageComponent(ResourceEnum resourceType, int capacity) {
        this.resourceType = resourceType;
        this.capacity = capacity;
    }

    /**
     * Vrati typ suroviny v zasobniku
     */
    public ResourceEnum getResourceType() {
        return this.resourceType;
    }

    /**
     * Vrati aktualni zasobu suroviny
     */
    public int getAmount() {
        return this.amount;
    }

    /**
     * Overeni zda ma zasobnik potrebny pocet suroviny
     */
    public boolean hasAmount(int amount) {
        return this.amount >= amount;
    }

    /**
     * Pouziti / odecteni suroviny ze zasobniku
     */
    public void use(int amount) {
        if (!this.hasAmount(amount)) {
            throw new IllegalArgumentException("Not enough resources: " + this.getResourceType() + "!");
        }
        this.amount -= amount;
    }

    /**
     * Doplneni suroviny do zasobniku
     */
    public void refill() {
        this.amount = this.capacity;
    }

}
