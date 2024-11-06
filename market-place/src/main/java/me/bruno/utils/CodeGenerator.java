package me.bruno.utils;

import java.util.Random;

public class CodeGenerator {

    public static long create12DigitLong() {
        Random random = new Random();

        long randomLong = Math.abs(random.nextLong()) % 1_000_000_000_000L;

        while (Long.toString(randomLong).length() < 12) {
            randomLong = randomLong * 10;
        }

        return randomLong;
    }

}