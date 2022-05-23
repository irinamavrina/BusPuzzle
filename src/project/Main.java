package project;

import java.util.*;
import java.util.stream.Collectors;

import static project.FileIOManager.readFromFile;
import static project.FileIOManager.writeToFile;

public class Main {//
    public static void main(String[] args) {
        List<Entry> entries = new ArrayList<>();
        readFromFile(entries);
        List<Entry> entries1 = entries.stream().distinct().collect(Collectors.toList());
        excludeAllInefficient(entries1);
        entries1.sort(Comparator.comparing(Entry::getDepartureTime));
        writeToFile(entries1);
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
