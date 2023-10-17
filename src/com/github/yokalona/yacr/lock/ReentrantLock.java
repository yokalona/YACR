package com.github.yokalona.yacr.lock;

import com.github.yokalona.yacr.annotations.ProbablySafe;
import com.github.yokalona.yacr.reference.GuardedReference;

@ProbablySafe
public class ReentrantLock implements Lock {

    private Thread owner = null;
    private final GuardedReference<Integer> mutex = GuardedReference.guard(0);

    @Override
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

    public AutoUnlockable doLock() {
        while (!lock()) Thread.onSpinWait();
        return this::unlock;
    }

    @Override
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