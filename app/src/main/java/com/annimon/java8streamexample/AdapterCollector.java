package com.annimon.java8streamexample;

import com.annimon.stream.Collector;
import com.annimon.stream.function.BiConsumer;
import com.annimon.stream.function.Function;
import com.annimon.stream.function.Supplier;
import java.util.ArrayList;
import java.util.List;

public final class AdapterCollector implements Collector<Word, List<Word>, WordAdapter> {

    private final WordAdapter adapter;

    public AdapterCollector(WordAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public Supplier<List<Word>> supplier() {
        return ArrayList::new;
    }

    @Override
    public BiConsumer<List<Word>, Word> accumulator() {
        return (list, word) -> list.add(word);
    }

    @Override
    public Function<List<Word>, WordAdapter> finisher() {
        return (list) -> {
            adapter.clear();
            adapter.addAll(list);
            return adapter;
        };
    }
}
