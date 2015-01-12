package com.annimon.java8streamexample;

import android.content.Context;
import android.util.Log;
import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public final class Utils {

    private static AdapterCollector sAdapterCollectorHolder;

    public static AdapterCollector collectAdapter(WordAdapter adapter) {
        if (sAdapterCollectorHolder == null) {
            sAdapterCollectorHolder = new AdapterCollector(adapter);
        }
        return sAdapterCollectorHolder;
    }

    public static List<Word> readWords(Context context) {
        final List<String> lines = new LinkedList<>();
        try (final InputStream is = context.getAssets().open("words.txt");
             final InputStreamReader isr = new InputStreamReader(is, "UTF-8");
             final BufferedReader reader = new BufferedReader(isr)) {
            String line;
            while ( (line = reader.readLine()) != null ) {
                lines.add(line);
            }
        } catch (IOException e) {
            Log.e("Java 8 Example", "Utils.readWords", e);
        }

        return Stream.of(lines)
                .map(str -> str.split("\t"))
                .filter(arr -> arr.length == 2)
                .map(arr -> new Word(arr[0], arr[1]))
                .collect(Collectors.toList());
    }
}
