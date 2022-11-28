package model.universityAttributes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Room {
    private String name;
    private Building building;

    private ArrayList<HashMap<Person, LocalDateTime>> bookedList = new ArrayList<>(); // [{personName: duration}, ...]
    // {roomName: [{personName: duration}, ...], ...}
    private HashMap<String, ArrayList<HashMap<Person, LocalDateTime>>> roomInfo = new HashMap<>();

    public Room(String name, Building building) {
        this.name = name;
        this.building = building;
    }

    public String getName() {
        return this.name;
    }

    public Building getBuilding() {
        return this.building;
    }

    public void addBooking(String name, Person person, LocalDateTime duration) {
        HashMap<Person, LocalDateTime> newBooking = new HashMap<>(); //{personName: duration}
        newBooking.put(person, duration);
        bookedList.add(newBooking); //[{personName: duration}, ...]
        roomInfo.put(name, bookedList); // {roomName: [{personName: duration}, ...], ...}
    }

    public void removeBooking(String name, Person person) {
        bookedList.remove(person);
        roomInfo.put(name, bookedList);
    }
}
