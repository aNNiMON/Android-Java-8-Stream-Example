package com.annimon.java8streamexample;

import com.annimon.stream.Objects;

public final class Word implements Comparable<Word> {

    private final String word, translate;

    public Word(Word other) {
        this.word = other.word;
        this.translate = other.translate;
    }

    public Word(String word, String translate) {
        this.word = word;
        this.translate = translate;
    }

    public String getWord() {
        return word;
    }

    public Word setWord(String word) {
        return new Word(word, translate);
    }

    public String getTranslate() {
        return translate;
    }

    public Word setTranslate(String translate) {
        return new Word(word, translate);
    }

    @Override
    public int compareTo(Word other) {
        int cmp = Objects.compare(word, other.word, String::compareToIgnoreCase);
        if (cmp != 0) return cmp;
        return Objects.compare(translate, other.translate, String::compareToIgnoreCase);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Word other = (Word) o;
        return Objects.equals(translate, other.translate) &&
               Objects.equals(word, other.word);
    }

    @Override
    public int hashCode() {
        int result = word.hashCode();
        result = 31 * result + translate.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return word + " — " + translate;
    }
}
