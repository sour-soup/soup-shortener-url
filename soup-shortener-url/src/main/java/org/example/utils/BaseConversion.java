package org.example.utils;

public class BaseConversion {

    private static final Long BASE = 62L;
    private static final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final Long MAX_LENGTH = 10L;

    public static String toBase(Long num) throws BaseConversionException {
        if (num < 0)
            throw new BaseConversionException("x must not be negative");
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

    public static Long fromBase(String str) throws BaseConversionException {
        if (str.length() > MAX_LENGTH)
            throw new BaseConversionException("string is too long");
        long res = 0;
        for (char c : str.toCharArray()) {
            res *= BASE;
            long cur = ALPHABET.indexOf(c);
            if (cur == -1L)
                throw new BaseConversionException("the string contains non-alphanumeric characters");
            res += cur;
        }
        return res;
    }

}
