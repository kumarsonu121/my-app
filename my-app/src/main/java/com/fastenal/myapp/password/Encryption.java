package com.fastenal.myapp.password;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Encryption {

    private static final Logger LOGGER = LogManager.getLogger(Encryption.class);

    public static String passwordEncyption(String passcode, String email) {
        LOGGER.info("Encryption:passwordEncryption entered the method");
        int at = email.indexOf('@');
        String salt = email.substring(0, at);
        String newPassword = passcode + salt;
        LOGGER.info("Encryption:passwordEncryption exiting the method");
        return HashingAlgorithm.hashText(newPassword);
    }

}

