package com.github.yokalona.yacr.reference;

public sealed interface Reference<T> permits GuardedReference, ShareReference {

    T get();

}
