package model.universityAttributes;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Building model
 * It has all the details about the rooms it have.
 */
public class Building implements Serializable {
    private final String name;
    private final String address;
    private final ArrayList<Room> rooms = new ArrayList<>();

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
        StringBuilder result = new StringBuilder();
        result.append("Name: ").append(this.name).append(", Address: ").append(this.address).append("\n");
        result.append(" Rooms: [ ");
        for (Room room : rooms) {
            result.append(room).append(", ");
        }
        result.append(" ]\n");
        return result.toString();
    }
}
