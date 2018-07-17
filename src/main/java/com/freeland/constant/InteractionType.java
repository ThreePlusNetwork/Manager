package com.freeland.constant;

/**
 *
 * @author heiqie
 * @date 2018/7/17
 */
public enum InteractionType {
    SEND_ROSE(10, 10, "送花"),
    BAR(15, 10, "酒吧");

    private int number;
    private int burnedGold;
    private String desc;

    InteractionType(int number, int burnedGold, String desc) {
        this.number = number;
        this.burnedGold = burnedGold;
        this.desc = desc;
    }

    public static InteractionType getByNumber(int number) {
        for (InteractionType type : values()) {
            if (type.getNumber() == number) {
                return type;
            }
        }
        throw new IllegalArgumentException("unknown transaction number: " + number);
    }

    public int getNumber() {
        return number;
    }

    public int getBurnedGold() {
        return burnedGold;
    }

    public String getDesc() {
        return desc;
    }
}
