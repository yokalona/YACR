package com.github.yokalona.yacr.gate;

public final class Door implements Gate {

    private volatile boolean mutex = false;

    @Override
    public boolean isOpen() {
        return mutex;
    }

    @Override
    public void passThrough() {
        while (!mutex) Thread.onSpinWait();
    }

    @Override
    public void open() {
        mutex = true;
    }

}
