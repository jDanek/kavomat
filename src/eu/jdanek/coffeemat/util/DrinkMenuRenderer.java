package eu.jdanek.coffeemat.util;

import eu.jdanek.coffeemat.Recipe;

import java.util.Collection;

public class DrinkMenuRenderer {

    public static void renderMenu(Collection<Recipe> recipes) {
        System.out.format("+-----------------+-----------------+---------+%n");
        System.out.format("| Kod             | Nazev           | Cena    |%n");
        System.out.format("+=================+=================+=========+%n");

        for (Recipe recipe : recipes) {
            System.out.format(
                    "| %-15s | %-15s | %-4s Kč |%n",
                    recipe.getName().toLowerCase(),
                    recipe.getName(),
                    recipe.getCost()
            );
            System.out.format("+-----------------+-----------------+---------+%n");
        }
    }
}
