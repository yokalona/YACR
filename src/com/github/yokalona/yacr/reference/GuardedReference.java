package com.github.yokalona.yacr.reference;

import com.github.yokalona.yacr.annotations.ProbablySafe;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

@ProbablySafe
public final class GuardedReference<T> implements Reference<T> {

    private static final VarHandleHolder VAR_HANDLE_HOLDER = new VarHandleHolder();

    private volatile T value;

    private GuardedReference(T value) {
        this.value = value;
    }

    public static <T> GuardedReference<T> guard() {
        return new GuardedReference<>(null);
    }

    public static <T> GuardedReference<T> guard(T value) {
        return new GuardedReference<>(value);
    }

    public T get() {
        return value;
    }

    public boolean set(T expected, T next) {
        VarHandle varHandle = VAR_HANDLE_HOLDER.get();
        return varHandle.compareAndSet(this, expected, next);
    }

    public void set(T next) {
        value = next;
    }

    private static final class VarHandleHolder {
        private final VarHandle handle;

        public VarHandleHolder() {
            try {
                handle = MethodHandles.lookup().findVarHandle(GuardedReference.class, "value", Object.class);
            } catch (ReflectiveOperationException e) {
                throw new Error(e);
            }
        }

        public VarHandle get() {
            return handle;
        }
    }

}
