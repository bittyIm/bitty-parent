package com.bitty.utils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;

public class RSA {
    static Cipher cipher = null;
    static public Key privateKey = null;
    static public Key publicKey = null;

    static {
        try {
            cipher = Cipher.getInstance("RSA");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

    public static byte[] enc(byte[] source) {

        byte[] newData = null;
        try {
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            newData = cipher.doFinal(source);
        } catch (BadPaddingException | IllegalBlockSizeException | InvalidKeyException e) {
            e.printStackTrace();
        }

        return newData;
    }

    public static byte[] dec(byte[] source) {
        byte[] newData = null;
        try {
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            newData = cipher.doFinal(source);
        } catch (BadPaddingException | IllegalBlockSizeException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return newData;
    }
}
