package com.fastenal.myapp.password;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashingAlgorithm {

    private static final Logger LOGGER = LogManager.getLogger(HashingAlgorithm.class);

    public static String hashText (String pass) {
        LOGGER.info("HashingAlgorithm:hashText entered the method");
        try
        {
            MessageDigest msgDst = MessageDigest.getInstance("MD5");
            byte[] msgArr = msgDst.digest(pass.getBytes());
            BigInteger bi = new BigInteger(1, msgArr);
            String hshtxt = bi.toString(16);
            while (hshtxt.length() < 32) {
                hshtxt = "0" + hshtxt;
            }
            LOGGER.info("HashingAlgorithm:hashText exiting the method");
            return hshtxt;
        }
        catch (NoSuchAlgorithmException e) {
            LOGGER.error("Can not save the password" + e.getMessage());
            return null;
        }
    }
}
