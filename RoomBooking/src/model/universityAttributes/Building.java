package model.universityAttributes;

import java.util.ArrayList;

public class Building {
    private String name;
    private String address;
    private ArrayList<Room> rooms;

    public Building(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return this.name;
    }

    public String getAddress() {
        return this.address;
    }

    public ArrayList<Room> getRooms() {
        return this.rooms;
    }

    public void addRoom(Room room) {
        this.rooms.add(room);
    }
}
