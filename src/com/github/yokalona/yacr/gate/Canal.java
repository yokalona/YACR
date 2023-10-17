package com.github.yokalona.yacr.gate;

public final class Canal implements Gate {

    private volatile int gates;

    @Override
    public boolean isOpen() {
        return gates <= 0;
    }

    @Override
    public void passThrough() {
        while (gates > 0) Thread.onSpinWait();
    }

    @Override
    public synchronized void open() {
        this.gates --;
    }
}
