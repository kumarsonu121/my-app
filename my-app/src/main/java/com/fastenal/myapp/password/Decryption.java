package com.fastenal.myapp.password;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Decryption {

    private static final Logger LOGGER = LogManager.getLogger(Decryption.class);

    public static boolean checkPassword(String enteredPassword, String originalPassword, String email) {
        LOGGER.info("Decryption:checkPassword entered the method");
            int at = email.indexOf('@');
            String salt = email.substring(0, at);
            String newText = enteredPassword + salt;
            String hashedPassword = HashingAlgorithm.hashText(newText);
            if(hashedPassword.equals(originalPassword)) {
                LOGGER.info("Decryption:checkPassword exiting the method with true value");
                return true;
            }
        LOGGER.info("Decryption:checkPassword exiting the method with false value");
        return false;
    }
}
