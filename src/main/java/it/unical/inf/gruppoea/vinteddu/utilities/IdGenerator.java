package it.unical.inf.gruppoea.vinteddu.utilities;

public class IdGenerator {
    private static Long lastIdPurchase = Long.valueOf(0);
    private static Long lastIdUser = Long.valueOf(0);
    private static Long lastIdItem = Long.valueOf(0);

    public static Long generateId() {
        lastIdPurchase++;
        return lastIdPurchase;
    }

    public static Long generateIdUser() {
        lastIdUser++;
        return lastIdUser;
    }

    public static Long generateIdItem() {
        lastIdItem++;
        return lastIdItem;
    }

}
