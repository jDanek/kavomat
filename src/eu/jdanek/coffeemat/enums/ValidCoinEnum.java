package eu.jdanek.coffeemat.enums;

import java.util.EnumSet;

public enum ValidCoinEnum {
    COIN_1(1),
    COIN_2(2),
    COIN_5(5),
    COIN_10(10),
    COIN_20(20),
    COIN_50(50);

    public final int value;

    ValidCoinEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static EnumSet<ValidCoinEnum> getEnumSet() {
        return EnumSet.allOf(ValidCoinEnum.class);
    }
}
