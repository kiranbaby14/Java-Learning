package model.universityAttributes;

import java.time.LocalDateTime;

public class Booking {
    private Person person;
    private LocalDateTime duration;
    public Booking(Person person, LocalDateTime duration) {
        this.person = person;
        this.duration = duration;
    }

    public Person getPerson() {
        return this.person;
    }

    public LocalDateTime getDuration() {
        return this.duration;
    }

}
