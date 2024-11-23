package com.heraj.sgx_pub_sub.util;

import java.util.Base64;

public class OPEEncryptionUtil {

    private static final int KEY = 10;  // A simple static key for illustration, consider dynamic key management for real-world apps.

    // Encrypts the message content
    public static String encrypt(String value) {
        // Convert the string to ASCII values and apply the encryption
        StringBuilder encryptedString = new StringBuilder();

        // Iterate over each character in the string
        for (char c : value.toCharArray()) {
            // Convert character to ASCII value
            int asciiValue = (int) c;

            // Apply the shift encryption (OPE)
            int encryptedValue = asciiValue + KEY;

            // Append the encrypted value to the result
            encryptedString.append((char) encryptedValue);
        }

        // Return the encrypted string in Base64 encoding
        return Base64.getEncoder().encodeToString(encryptedString.toString().getBytes());
    }

    // Decrypts the message content
    public static String decrypt(String encryptedValue) {
        // Decode the Base64 encoded string
        String decodedString = new String(Base64.getDecoder().decode(encryptedValue));

        StringBuilder decryptedString = new StringBuilder();

        // Iterate over each character in the decoded string
        for (char c : decodedString.toCharArray()) {
            // Convert the character back to its ASCII value
            int encryptedAsciiValue = (int) c;

            // Apply the reverse of the encryption (subtract the shift)
            int decryptedAsciiValue = encryptedAsciiValue - KEY;

            // Append the decrypted character
            decryptedString.append((char) decryptedAsciiValue);
        }

        return decryptedString.toString();
    }
}

