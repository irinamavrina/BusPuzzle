package project;

import java.time.LocalTime;
import java.util.*;

import static java.time.temporal.ChronoUnit.MINUTES;

public class Entry {
	private LocalTime departureTime;
	private LocalTime arrivalTime;
	private String busCompany;
	private static final int MINUTES_IN_DAY = 1440;

	public Entry(LocalTime departureTime, LocalTime arrivalTime, String busCompany) {
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.busCompany = busCompany;
	}

	public long routeTime() {
		if (!this.pastMidnight()) {
			return MINUTES.between(this.departureTime, this.arrivalTime);
		}
		return MINUTES.between(this.departureTime, this.arrivalTime) + MINUTES_IN_DAY;

	}

	public boolean pastMidnight() {
		return MINUTES.between(this.departureTime, this.arrivalTime) < 0;
	}

	public boolean isMoreEfficient(Entry e) {
		LocalTime d1 = this.departureTime;
		LocalTime d2 = e.departureTime;
		LocalTime a1 = this.arrivalTime;
		LocalTime a2 = e.arrivalTime;

		if (d1.equals(d2) && a1.equals(a2)) {
			if (this.busCompany.equals("Posh")) {
				return true;
			}
		}
		if (d1.equals(d2) && this.routeTime() < e.routeTime()) {
			return true;
		}
		if (a1.equals(a2) && this.routeTime() < e.routeTime()) {
			return true;
		}

		if (this.pastMidnight() && !e.pastMidnight()) {
			return false;
		} else if (!this.pastMidnight() && e.pastMidnight()) {
			if (d1.getHour() == d2.getHour()) {
				return d1.isAfter(d2) || d1.equals(d2);
			}
			if (a1.getHour() == a2.getHour()) {
				return a1.isBefore(a2) || a1.equals(a2);
			}
			return false;
		}
		return d1.isAfter(d2) && a1.isBefore(a2);
	}


	@Override
	public String toString() {
		return busCompany +
		       " " + departureTime +
		       " " + arrivalTime;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
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