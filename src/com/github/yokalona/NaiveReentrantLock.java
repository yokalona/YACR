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
        Integer value = mutex.get();
        if (owner == current) {
            mutex.set(--value);
            if (value == 0) {
                owner = null;
                return true;
            }
        }
        return false;
    }

    public boolean isLocked() {
        return mutex.get() > 0;
    }

}