package com.bitty.plugin;

public interface Plugin {
    void getDescription();

    void getName();

    void install();

    void uninstall();
}
