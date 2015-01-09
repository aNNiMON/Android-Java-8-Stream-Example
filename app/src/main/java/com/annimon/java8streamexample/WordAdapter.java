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

    private final List<Word> mWords, mCurrentList;
    private final LayoutInflater mInflater;

    public WordAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mWords = new ArrayList<>();
        mCurrentList = new ArrayList<>();
    }

    public WordAdapter(Context context, List<Word> items) {
        this(context);
        // We need new copy of words for immutability
        Stream.of(items)
                .map(Word::new)
                .forEach(mWords::add);
        mCurrentList.addAll(items);
    }

    public List<Word> getWords() {
        return new ArrayList<>(mWords);
    }

    public List<Word> getCurrentList() {
        return mCurrentList;
    }

    public void addAll(List<Word> words) {
        mCurrentList.addAll(words);
        notifyDataSetChanged();
    }

    public void add(Word word) {
        mCurrentList.add(word);
        notifyDataSetChanged();
    }

    public void clear() {
        mCurrentList.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mCurrentList.size();
    }

    @Override
    public Word getItem(int position) {
        return mCurrentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_word, parent, false);
            holder = new ViewHolder();
            holder.word = (TextView) convertView.findViewById(R.id.word);
            holder.translate = (TextView) convertView.findViewById(R.id.translate);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Word word = mCurrentList.get(position);
        holder.word.setText(word.getWord());
        holder.translate.setText(word.getTranslate());

        return convertView;
    }

    private static class ViewHolder {
        TextView word;
        TextView translate;
    }

}