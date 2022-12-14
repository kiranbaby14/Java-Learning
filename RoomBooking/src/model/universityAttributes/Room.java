package model.universityAttributes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Room {
    private String name;
    private ArrayList<Booking> bookings = new ArrayList<>();

    public Room(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<Booking> getBookings() {
        return this.bookings;
    }

    public void addBooking(Booking booking) {
        this.bookings.add(booking);
    }

    public void removeBooking(Booking booking) {
        this.bookings.remove(booking);
    }

    public String toString() {
        StringBuffer result = new StringBuffer();
        result.append(this.name);
        return result.toString();
    }
}
