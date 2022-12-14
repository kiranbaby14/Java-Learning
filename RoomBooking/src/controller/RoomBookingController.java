package controller;

import model.University;
import model.universityAttributes.Booking;
import model.universityAttributes.Building;
import model.universityAttributes.Person;
import model.universityAttributes.Room;
import view.guiview.CliGUI;
import view.guiview.SwingGUI;

import java.time.LocalDateTime;

public class RoomBookingController {

    private University university;
    private CliGUI cliGUI;
    private SwingGUI swingGUI;

//    public RoomBookingController(University university, CliGUI cliGUI, SwingGUI swingGUI)
    public RoomBookingController(University university) {
        this.university = university;
        this.cliGUI = cliGUI;
        this.swingGUI = swingGUI;
    }

    public void addPerson(Person person) {
        // email id should be unique for people
        boolean emailAlreadyExists = false;
        for(Person person1: this.university.getPeople()) {
            if(person1.getEmail().equals(person.getEmail())) {
                emailAlreadyExists = true;
                break;
            }
        }
        if(!emailAlreadyExists) {
            this.university.addPerson(person);
        } else {
            System.out.println("Sorry, we already have a student with the same Mail ID!");
        }
    }
    public void removePerson(Person person) {
        this.university.removePerson(person);
    }

    public void addBuilding(Building building) {
        //building names should be unique
        boolean buiildingNameExists = false;
        for(Building building1: this.university.getBuildings()) {
            if(!building1.getName().equals(building.getName())) {
                buiildingNameExists = true;
                break;
            }
        }
        if(!buiildingNameExists) {
            this.university.addBuilding(building);
        } else {
            System.out.println("Sorry, that building name already exists. Try another name!");
        }
    }

    public void removeBuilding(Building building) {
        this.university.removeBuilding(building);
    }

    public void addRoom(Room room, Building building) {
        // room name/number inside a building should be unique
        boolean roomNameExists = false;
        for(Room room1: building.getRooms()) {
            if (room1.getName().equals(room.getName())) {
                roomNameExists = true;
                break;
            }
        }

        if(!roomNameExists) {
            building.addRoom(room);
        } else {
            System.out.println("Sorry, that room name/number already exists in the building!");
        }

    }

    public void removeRoom(Room room, Building building) {
        building.removeRoom(room);
    }

    public void addBooking(Room room, Building building, Person person, LocalDateTime duration) {
        if(duration.getMinute() >= 5 && duration.getMinute()%5 == 0) { // checkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk
            // more conditions to comeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee
            boolean timeClash = false;
            for(Booking booking: room.getBookings()) {
                if(booking.getDuration() == duration) {
                    timeClash = true;
                    break;
                }
            }
            if(!timeClash) {
                Booking booking = new Booking(person, duration);
                room.addBooking(booking);
            } else {
                System.out.println("Sorry, already booked for this time slot. Choose other timings!");
            }

        } else {
            System.out.println("Choose > 5 min and multiple of 5 mins..");
        }

    }
}
