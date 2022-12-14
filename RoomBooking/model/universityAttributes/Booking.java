package model.universityAttributes;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Bookings model
 * Booking data related to a room is stored here
 */
public class Booking implements Serializable {
    private final Person person;
    private final int duration;
    private final LocalDate date;
    private final LocalTime bookingStartTime;
    private LocalTime bookingEndTime;
    private final LocalDateTime dateAndTime;

    /**
     * Constructor
     * @param person person booked the room
     * @param date datee at which booked
     * @param bookingStartTime booking start time
     * @param duration booking duration
     */
    public Booking (Person person, LocalDate date, LocalTime bookingStartTime, int duration) {
        this.person = person;
        this.duration = duration;
        this.date = date;
        this.bookingStartTime = bookingStartTime;
        this.dateAndTime = LocalDateTime.of(this.date, this.bookingStartTime);
        setBookedDuration(bookingStartTime, duration);
    }

    /**
     * Method to set the ending time of booking.
     * @param bookingStartTime start time
     * @param duration duartion
     */
    public void setBookedDuration(LocalTime bookingStartTime, int duration) {
        this.bookingEndTime = bookingStartTime.plusMinutes(duration);
    }

    /**
     * Getter method to get the people who've booked rooms.
     * @return Person
     */
    public Person getPerson() {
        return this.person;
    }

    /**
     * Method to get the duration of stay.
     * @return duration
     */
    public int getDuration() {
        return this.duration;
    }

    /**
     * Method to get the date  of booking.
     * @return LocalDate
     */
    public LocalDate getDate() {
        return this.date;
    }

    /**
     * Method to get the date and time of booking.
     * @return Local date and time
     */
    public LocalDateTime getDateAndTime() {
        return this.dateAndTime;
    }

    /**
     * Method to get starting time of booking.
     * @return LocalTime
     */
    public LocalTime getBookingStartTime() {
        return this.bookingStartTime;
    }

    /**
     * Method to get booking end time
     * @return end time
     */
    public LocalTime getBookingEndTime() {
        return this.bookingEndTime;
    }

}
