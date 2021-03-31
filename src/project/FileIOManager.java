package project;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class FileIOManager {
    public static final String OUTPUT = "output.txt";

    public static void readFromFile(List<Entry> list) throws IOException, ParseException {

        Scanner scanner = new Scanner(System.in);
        String fileName = scanner.nextLine();
        Path path = Paths.get(fileName);
        scanner = new Scanner(path);
        while (scanner.hasNext()) {
            Entry entry = parseLine(scanner.nextLine());
            if (entry.getRouteTime() > 60) continue;
            list.add(entry);
        }
    }

    public static void writeToFile(List<Entry> list, String busCompany1, String busCompany2) throws FileNotFoundException {
        PrintWriter printWriter = new PrintWriter(OUTPUT);

        int counter = 0;
        for (Entry entry : list) {
            if (entry.getBusCompany().equals(busCompany1)) {
                printWriter.println(entry);
                ++counter;
            }
        }
        printWriter.println();

        int remainingSize = list.size() - counter;
        for (Entry entry : list) {
            if (entry.getBusCompany().equals(busCompany2)) {
                if (remainingSize == 1) printWriter.print(entry);
                else {
                    printWriter.println(entry);
                    --remainingSize;
                }
            }
        }
        printWriter.flush();
    }

    private static Entry parseLine(String line) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        Scanner scanner = new Scanner(line);
        scanner.useDelimiter(" ");
        String busCompany = scanner.next();
        String dTime = scanner.next();
        String aTime = scanner.next();
        LocalTime departureTime = LocalTime.parse(dTime, formatter);
        LocalTime arrivalTime = LocalTime.parse(aTime, formatter);
        return new Entry(departureTime, arrivalTime, busCompany);
    }
}
