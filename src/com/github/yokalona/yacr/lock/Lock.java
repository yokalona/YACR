package com.github.yokalona.yacr.lock;

public interface Lock {
    boolean lock();
    boolean unlock();
}
