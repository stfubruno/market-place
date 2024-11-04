package me.bruno.utils;

import java.util.Random;

public class CodeGenerator {

    public static long create12DigitLong() {
        Random random = new Random();
        long randomLong = random.nextLong();

        // Ensure the long is 12 digits or less
        while (Long.toString(randomLong).length() > 12) {
            randomLong = random.nextLong();
        }

        // Pad with leading zeros if necessary
        String randomLongStr = Long.toString(randomLong);
        while (randomLongStr.length() < 12) {
            randomLongStr = "0" + randomLongStr;
        }

        return Long.parseLong(randomLongStr);
    }

}