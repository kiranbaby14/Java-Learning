package model.universityAttributes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Room Model
 * Room informations are stored here.
 * It also contains list of bookings.
 */
public class Room implements Serializable {
    private final String name;
    private final ArrayList<Booking> bookings = new ArrayList<>();

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

        // Sort the booking array using Date and time
        Collections.sort(this.bookings, new Comparator<Booking>(){
            public int compare(Booking booking1, Booking booking2)
            {
                return booking1.getDateAndTime().compareTo(booking2.getDateAndTime());
            }
        });
    }

    public void removeBooking(Booking booking) {
        this.bookings.remove(booking);
    }

    public String toString() {
        return this.name;
    }
}
