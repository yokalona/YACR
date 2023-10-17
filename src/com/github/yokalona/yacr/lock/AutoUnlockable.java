package com.github.yokalona.yacr.lock;

@FunctionalInterface
public interface AutoUnlockable extends AutoCloseable {
    @Override
    void close();
}
