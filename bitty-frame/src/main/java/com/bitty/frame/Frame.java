package com.bitty.frame;

public class Frame {
    byte magicCode;
    // 消息id
    byte mid;
    byte length;
    //消息长度
    byte body;
}

// bitty
// 001
// 10000 \r\n
