package com.github.yokalona;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

public final class GuardedValue<T> {

    private static final VarHandleHolder VAR_HANDLE_HOLDER = new VarHandleHolder();

    private volatile T value;

    private GuardedValue(T value) {
        this.value = value;
    }

    public static <T> GuardedValue<T> guard() {
        return new GuardedValue<>(null);
    }

    public static <T> GuardedValue<T> guard(T value) {
        return new GuardedValue<>(value);
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
                handle = MethodHandles.lookup().findVarHandle(GuardedValue.class, "value", Object.class);
            } catch (ReflectiveOperationException e) {
                throw new Error(e);
            }
        }

        public VarHandle get() {
            return handle;
        }
    }

}
