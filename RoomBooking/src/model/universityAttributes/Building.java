package model.universityAttributes;

import java.util.ArrayList;

public class Building {
    private String name;
    private String address;
    private ArrayList<Room> rooms = new ArrayList<>();

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

    public void removeRoom(Room room) {
        this.rooms.remove(room);
    }

    public String toString() {
        StringBuffer result = new StringBuffer();
        result.append("Name: " + this.name + ", Address: " + this.address + "\n");
        result.append(" Rooms: [ ");
        for (Room room : rooms) {
            result.append(room + ", ");
        }
        result.append(" ]\n");
        return result.toString();
    }
}
