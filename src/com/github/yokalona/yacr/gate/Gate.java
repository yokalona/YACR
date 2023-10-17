package com.github.yokalona.yacr.gate;

public sealed interface Gate permits Door, Canal {
    boolean isOpen();

    void passThrough();

    void open();
}
