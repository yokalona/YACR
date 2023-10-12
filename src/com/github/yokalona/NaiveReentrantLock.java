package com.github.yokalona;

public class NaiveReentrantLock {

    private final GuardedValue<Integer> mutex = GuardedValue.guard(0);
    private Thread owner = null;

    public boolean lock() {
        final Thread current = Thread.currentThread();
        Integer value = mutex.get();
        if (value == 0 && mutex.set(0, 1)) {
            owner = current;
            return true;
        } else if (owner == current) {
            mutex.set(++value);
            return true;
        }
        return false;
    }

    public boolean unlock() {
        Thread current = Thread.currentThread();
        int value = mutex.get() - 1;
        if (owner == current) {
            if (value == 0) {
                owner = null;
            }
            mutex.set(value);
            return value == 0;
        }
        return false;
    }

    public boolean isLocked() {
        return mutex.get() > 0;
    }

}