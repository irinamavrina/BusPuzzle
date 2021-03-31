package project;

import java.time.LocalTime;
import java.util.*;

import static java.time.temporal.ChronoUnit.MINUTES;

public class Entry {
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private String busCompany;

    public Entry(LocalTime departureTime, LocalTime arrivalTime, String busCompany) {
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.busCompany = busCompany;
    }

    public long getRouteTime() {
        if (!this.isPastMidnight())
            return MINUTES.between(this.departureTime, this.arrivalTime);
        return MINUTES.between(this.departureTime, this.arrivalTime) + 1440;

    }

    public boolean isPastMidnight() {
        return MINUTES.between(this.departureTime, this.arrivalTime) < 0;
    }

    public boolean isMoreEfficient(Entry entry) {
        if (this.departureTime.equals(entry.departureTime) && this.arrivalTime.equals(entry.arrivalTime)) {
            if (this.busCompany.equals("Posh")) return true;
        }
        if (this.departureTime.equals(entry.departureTime) && this.getRouteTime() < entry.getRouteTime()) return true;
        if (this.arrivalTime.equals(entry.arrivalTime) && this.getRouteTime() < entry.getRouteTime()) return true;

        if (this.isPastMidnight() && !entry.isPastMidnight()) {
           return false;
        } else if (!this.isPastMidnight() && entry.isPastMidnight()) {
            if (this.departureTime.getHour() == entry.departureTime.getHour()) {
                return this.departureTime.isAfter(entry.departureTime) || this.departureTime.equals(entry.departureTime);
            }
            if (this.arrivalTime.getHour() == entry.arrivalTime.getHour()) {
                return this.arrivalTime.isBefore(entry.arrivalTime) || this.arrivalTime.equals(entry.arrivalTime);
            }
            return false;
        }

        return this.departureTime.isAfter(entry.departureTime) && this.arrivalTime.isBefore(entry.arrivalTime);
    }


    @Override
    public String toString() {
        return busCompany +
                " " + departureTime +
                " " + arrivalTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entry entry = (Entry) o;
        return Objects.equals(departureTime, entry.departureTime) &&
                Objects.equals(arrivalTime, entry.arrivalTime) &&
                Objects.equals(busCompany, entry.busCompany);
    }

    @Override
    public int hashCode() {
        return Objects.hash(departureTime, arrivalTime, busCompany);
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public String getBusCompany() {
        return busCompany;
    }
}
