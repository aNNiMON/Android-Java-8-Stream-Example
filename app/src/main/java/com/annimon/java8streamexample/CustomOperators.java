package com.annimon.java8streamexample;

import com.annimon.stream.Stream;
import com.annimon.stream.function.Function;
import java.util.Iterator;

public final class CustomOperators {

    public static <T> Index<T> index() {
        return index(0);
    }

    public static <T> Index<T> index(int from) {
        return new Index<>(from);
    }

    private static class Index<T> implements Function<Stream<T>, Stream<IntPair<T>>> {

        private final int startFrom;

        public Index(int startFrom) {
            this.startFrom = startFrom;
        }

        @Override
        public Stream<IntPair<T>> apply(Stream<T> stream) {
            final Iterator<? extends T> iterator = stream.iterator();
            return Stream.of(new Iterator<IntPair<T>>() {

                private int index = startFrom;

                @Override
                public boolean hasNext() {
                    return iterator.hasNext();
                }

                @Override
                public IntPair<T> next() {
                    return new IntPair<>(index++, iterator.next());
                }

                @Override
                public void remove() {
                    throw new UnsupportedOperationException();
                }
            });
        }
    }
}
