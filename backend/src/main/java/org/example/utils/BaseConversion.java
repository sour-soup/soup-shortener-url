package org.example.utils;

public class BaseConversion {

    private static final String ALPHABET = "_-0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final Long BASE = (long) ALPHABET.length();

    public static String toBase(Long num) {
        if (num == 0)
            return String.valueOf(ALPHABET.charAt(0));
        long x = num;
        StringBuilder res = new StringBuilder();
        while (x > 0) {
            res.append(ALPHABET.charAt((int) (x % BASE)));
            x = x / BASE;
        }
        return res.reverse().toString();
    }

    public static Long fromBase(String str) {
        long res = 0;
        for (char c : str.toCharArray()) {
            res *= BASE;
            long cur = ALPHABET.indexOf(c);
            res += cur;
        }
        return res;
    }

}
