package eu.jdanek.coffeemat;

import eu.jdanek.coffeemat.enums.ResourceEnum;

import java.util.HashMap;

/**
 * Trida reprezentujici recept pro pripravu napoje
 */
public class Recipe {

    private final String name;
    private final int cost;

    private final HashMap<ResourceEnum, Integer> resources;

    public Recipe(String name, int cost, HashMap<ResourceEnum, Integer> resources) {
        this.name = name;
        this.cost = cost;
        this.resources = resources;
    }

    /**
     * Vraci nazev receptu
     */
    public String getName() {
        return this.name;
    }

    /**
     * Vraci cenu receptu
     */
    public int getCost() {
        return this.cost;
    }

    /**
     * Vraci suroviny nutenych pro dany recept
     */
    public HashMap<ResourceEnum, Integer> getResources() {
        return this.resources;
    }
}
