package com.bitty.utils;

import io.netty.handler.codec.base64.Base64Encoder;
import org.junit.jupiter.api.Test;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

class RSATest {

    @Test
    void enc() {


            KeyPairGenerator generator = null;
            try {
                generator = KeyPairGenerator.getInstance("RSA");
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (generator != null) {
                generator.initialize(2048);
                KeyPair keyPair = generator.genKeyPair();
                var publicKey=keyPair.getPublic();
                var privateKey=keyPair.getPrivate();
                System.out.println("私钥");
                System.out.println(new String(privateKey.getEncoded()));
                System.out.println("公钥");
                System.out.println(new String(publicKey.getEncoded()));
                RSA.privateKey=privateKey;
                RSA.publicKey=publicKey;

                var data="hello bitty RSAhello bitty RSAhello bitty RSA";
                var newData = RSA.enc(data.getBytes());
                var originData= RSA.dec(newData);
                System.out.println(data);
                System.out.println(new String(originData));
                assertEquals(data,new String(originData));
            }



    }
}