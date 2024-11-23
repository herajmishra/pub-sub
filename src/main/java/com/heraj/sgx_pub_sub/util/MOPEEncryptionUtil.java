package com.heraj.sgx_pub_sub.util;

import java.util.Base64;

public class MOPEEncryptionUtil {

    private static final int KEY = 10;
    private static final int MODULO = 100; // Modulo value to constrain the result to a certain range

    // Encrypts the message content
    public static String encrypt(String value) {
        System.out.println(value);
        int intValue = Integer.parseInt(value);
        int encryptedValue = (intValue + KEY) % MODULO; // Modular arithmetic for MOPE
        return Base64.getEncoder().encodeToString(String.valueOf(encryptedValue).getBytes());
    }

    // Decrypts the message content
    public static String decrypt(String encryptedValue) {
        String decrypted = new String(Base64.getDecoder().decode(encryptedValue));
        int value = Integer.parseInt(decrypted);
        return String.valueOf((value - KEY + MODULO) % MODULO); // Ensure correct modular reverse operation
    }
}

