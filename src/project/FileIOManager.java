package project;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class FileIOManager {
    public static final String OUTPUT = "output.txt";

    public static void readFromFile(List<Entry> list) {
        Scanner scanner = new Scanner(System.in);
        String fileName = scanner.nextLine();
        Path path = Paths.get(fileName);
        try (Scanner scanner1 = new Scanner(path)) {
            while (scanner1.hasNext()) {
                Entry e = parseLine(scanner1.nextLine());
                if (e.routeTime() > 60 || e.getBusCompany().equals("Error")) continue;
                if (e.getBusCompany().equals("Posh") || e.getBusCompany().equals("Grotty"))
                    list.add(e);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeToFile(List<Entry> list) {
        try (PrintWriter printWriter = new PrintWriter(OUTPUT)) {
            int counter = 0;
            for (Entry e : list) {
                if (e.getBusCompany().equals("Posh")) {
                    printWriter.println(e);
                    ++counter;
                }
            }
            printWriter.println();
            int remainingSize = list.size() - counter;
            for (Entry e : list) {
                if (e.getBusCompany().equals("Grotty")) {
                    if (remainingSize == 1) printWriter.print(e);
                    else {
                        printWriter.println(e);
                        --remainingSize;
                    }
                }
            }
            printWriter.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static Entry parseLine(String line) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        Scanner scanner = new Scanner(line);
        scanner.useDelimiter(" ");
        String busCompany = scanner.next();
        String dTime = scanner.next();
        String aTime = scanner.next();
        LocalTime departureTime = null;
        LocalTime arrivalTime = null;
        try {
            departureTime = LocalTime.parse(dTime, formatter);
            arrivalTime = LocalTime.parse(aTime, formatter);
        } catch (DateTimeParseException e) {
            busCompany = "Error";
            e.printStackTrace();
        }
        return new Entry(departureTime, arrivalTime, busCompany);
    }
}
