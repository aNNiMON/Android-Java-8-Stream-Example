package com.annimon.java8streamexample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.annimon.stream.Stream;
import java.util.ArrayList;
import java.util.List;

public final class WordAdapter extends BaseAdapter {

    private final List<Word> words, currentList;
    private final LayoutInflater inflater;

    public WordAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        words = new ArrayList<>();
        currentList = new ArrayList<>();
    }

    public WordAdapter(Context context, List<Word> items) {
        this(context);
        // We need new copy of words for immutability
        Stream.of(items)
                .map(Word::new)
                .forEach(words::add);
        currentList.addAll(items);
    }

    public List<Word> getWords() {
        return new ArrayList<>(words);
    }

    public List<Word> getCurrentList() {
        return currentList;
    }

    public void addAll(List<Word> words) {
        currentList.addAll(words);
        notifyDataSetChanged();
    }

    public void add(Word word) {
        currentList.add(word);
        notifyDataSetChanged();
    }

    public void clear() {
        currentList.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return currentList.size();
    }

    @Override
    public Word getItem(int position) {
        return currentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_word, parent, false);
            holder = new ViewHolder();
            holder.word = (TextView) convertView.findViewById(R.id.word);
            holder.translate = (TextView) convertView.findViewById(R.id.translate);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Word word = currentList.get(position);
        holder.word.setText(word.getWord());
        holder.translate.setText(word.getTranslate());

        return convertView;
    }

    private static class ViewHolder {
        TextView word;
        TextView translate;
    }

}