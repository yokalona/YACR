package com.github.yokalona.yacr.lock;

public class Condition {

    private volatile boolean mutex = false;

    public void await() {
        while (!mutex) Thread.onSpinWait();
    }

    public void signal() {
        mutex = true;
    }

    public void repeat() {
        mutex = false;
    }

}
