package com.github.yokalona.yacr.reference;

import java.lang.ref.WeakReference;

public final class SingleShareReference<T> implements ShareReference<T> {

    private final T object;
    private WeakReference<Thread> owner = null;

    private SingleShareReference(T object) {
        this.object = object;
    }

    @Override
    public T get() {
        if (amOwner()) {
            return object;
        }
        throw new NotAnOwnerException();
    }

    @Override
    public T getOrDefault(T def) {
        if (amOwner()) {
            return object;
        }
        return def;
    }

    @Override
    public synchronized void own() {
        if (!tryOwn()) {
            throw new AlreadyOwnedException();
        }
    }

    @Override
    public synchronized boolean tryOwn() {
        if (canOwn()) {
            this.owner = new WeakReference<>(Thread.currentThread());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public synchronized boolean canOwn() {
        return owner == null;
    }

    @Override
    public boolean amOwner() {
        return !canOwn() && this.owner.refersTo(Thread.currentThread());
    }

    public static <T> SingleShareReference<T> share(T object) {
        return new SingleShareReference<>(object);
    }

}
