package com.github.yokalona.yacr.reference;

import java.lang.ref.WeakReference;

public final class MultipleShareReference<T> implements ShareReference<T> {
    private final T object;
    private WeakReference<Thread> currentOwner;

    private MultipleShareReference(T object) {
        this.object = object;
    }

    @Override
    public synchronized T get() {
        if (amOwner()) {
            return object;
        }
        throw new NotAnOwnerException();
    }

    @Override
    public synchronized T getOrDefault(T def) {
        if (amOwner()) {
            return object;
        }
        return def;
    }

    @Override
    public synchronized void own() {
        if (this.currentOwner != null) {
            this.currentOwner.enqueue();
        }
        this.currentOwner = new WeakReference<>(Thread.currentThread());
    }

    @Override
    public synchronized boolean tryOwn() {
        own();
        return true;
    }

    @Override
    public synchronized boolean amOwner() {
        if (this.currentOwner != null) {
            return this.currentOwner.refersTo(Thread.currentThread());
        }
        return false;
    }

    public static <T> Reference<T> share(T object) {
        return new MultipleShareReference<>(object);
    }
}
