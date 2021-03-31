package project;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

import static project.FileIOManager.readFromFile;
import static project.FileIOManager.writeToFile;

public class Main {
    public static void main(String[] args) {
        List<Entry> entries = new ArrayList<>();
        try {
            readFromFile(entries);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        List<Entry> entries1 = entries.stream().distinct().collect(Collectors.toList());

        excludeAllInefficient(entries1);
        entries1.sort(new Comparator<Entry>() {
            @Override
            public int compare(Entry o1, Entry o2) {
                return o1.getDepartureTime().compareTo(o2.getDepartureTime());
            }
        });

        try {
            writeToFile(entries1, "Posh","Grotty");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }


    public static void excludeAllInefficient(List<Entry> entries1) {
        SortedSet<Integer> setOfIndexesToExclude = new TreeSet<>(Collections.reverseOrder());
        for (int i = 0; i < entries1.size(); i++) {
            Entry outerEntry = entries1.get(i);
            for (int j = 0; j < entries1.size(); j++) {
                if (j == i) continue;
                Entry innerEntry = entries1.get(j);
                if (outerEntry.isMoreEfficient(innerEntry)) {
                    setOfIndexesToExclude.add(j);
                }
            }
        }
        for (int index : setOfIndexesToExclude) {
            entries1.remove(index);
        }
    }

}
