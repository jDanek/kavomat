package eu.jdanek.coffeemat.enums;

import java.util.EnumSet;

public enum ResourceEnum {
    WATER("Water"),
    COFFEE("Coffee"),
    MILK("Milk"),

    // par dalsich surovin oproti prikladu 1
    SUGAR("Sugar"),
    CHOCOLATE("Chocolate"),
    TEA("Tea");

    private final String label;

    ResourceEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }

    public static EnumSet<ResourceEnum> getEnumSet() {
        return EnumSet.allOf(ResourceEnum.class);
    }
}
