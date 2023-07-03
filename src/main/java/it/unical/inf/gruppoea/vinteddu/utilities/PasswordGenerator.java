package it.unical.inf.gruppoea.vinteddu.utilities;

import java.util.Random;

public class PasswordGenerator{

    private static String stringa = "";

    public String generaStringaCasuale(int lunghezza) {
        String caratteri = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder stringaCasuale = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < lunghezza; i++) {
            int index = random.nextInt(caratteri.length());
            char carattereCasuale = caratteri.charAt(index);
            stringaCasuale.append(carattereCasuale);
        }

        return stringaCasuale.toString();
    }
}
