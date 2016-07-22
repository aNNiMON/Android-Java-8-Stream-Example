package com.annimon.java8streamexample;

public class IntPair<T> {

    private final int index;
    private final T object;

    public IntPair(int index, T object) {
        this.index = index;
        this.object = object;
    }

    public int getIndex() {
        return index;
    }

    public T getObject() {
        return object;
    }
}
