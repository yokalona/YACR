package com.github.yokalona.yacr.reference;

public sealed interface ShareReference<T> extends Reference<T>
        permits SingleShareReference, MultipleShareReference {

    T get();

    T getOrDefault(T def);

    void own();

    boolean tryOwn();

    default boolean canOwn() {
        return true;
    }

    boolean amOwner();

    class AlreadyOwnedException extends RuntimeException {
    }

    class NotAnOwnerException extends RuntimeException {
    }
}
