package com.bitty.codec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;

public class E {


    /**
     * TODO 加密 默认明文传输
     *
     * @param source
     * @return
     */
    public static byte[] enc(byte[] source) {
        return source;
    }

    /**
     * TODO 解密
     *
     * @param source
     * @return
     */
    public static byte[] dec(byte[] source) {
        return source;
    }
}
