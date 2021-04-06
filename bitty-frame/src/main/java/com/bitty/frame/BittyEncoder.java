package com.bitty.frame;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class BittyEncoder {
    byte[] magicCode=new byte[1];
    long timeBase=0L;
    int mid=0;
    ByteBuf buffer= Unpooled.buffer();
    MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

    public BittyEncoder() throws NoSuchAlgorithmException {
        Calendar dateStart = Calendar.getInstance();
        dateStart.setTimeInMillis(System.currentTimeMillis());
        dateStart.set(Calendar.MINUTE, 0);
        dateStart.set(Calendar.SECOND, 0);
        dateStart.set(Calendar.MILLISECOND,0);
        timeBase=dateStart.getTimeInMillis();
    }

    ByteBuf encode(byte[] source) throws NoSuchAlgorithmException {
        buffer.clear();
        var enSource= messageDigest.digest(source);

        buffer.writeByte(0xff);
        buffer.writeInt(source.length);
        buffer.writeInt(mid++);
        buffer.writeInt((int) (System.currentTimeMillis()-timeBase));
        buffer.writeBytes(source);
        return buffer;
    }
}
