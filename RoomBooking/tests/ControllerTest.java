package tests;

import controller.Controller;
import model.University;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.guiview.CliGUI;
import view.guiview.SwingGUI;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    //Model
    private final University university = new University();

    //Views
    private final CliGUI cliGUI = new CliGUI();
    private final SwingGUI swingGUI = new SwingGUI();
    //controller
    private final Controller controller = new Controller(university, cliGUI, swingGUI);

    @BeforeEach
    public void initializeObjects() {

        //Adding people
        controller.addPerson("kiran", "kb@gmail.com");
        controller.addPerson("dan", "dan@gmail.com");

        //Adding  buildings
        controller.addBuilding("Balfour", "xyz");
        controller.addBuilding("Nisbet", "abc");

        //Adding rooms to buildings
        controller.addRoom("BF1", "Balfour");
        controller.addRoom("BF2", "Balfour");
        controller.addRoom("BF3", "Balfour");

        controller.addRoom("NB1", "Nisbet");
        controller.addRoom("NB2", "Nisbet");
        controller.addRoom("NB3", "Nisbet");
    }


    @Test
    public void addPersonShouldReturnAlreadyExists() { // trying to add "kiran" again
        String expected = controller.addPerson("kiran", "kb@gmail.com");
        assertEquals(expected,
                "Sorry, we already have a student with the same Mail ID!");
    }

    @Test
    public void removePersonShouldReturnNotExist() {
        assertEquals(controller.removePerson("vijay@gmail.com"),
                "The person does not exist!");
    }

    @Test
    public void addBuildingShouldReturnAlreadyExists() { //"Balfour" is already added
        assertEquals(controller.addBuilding("Balfour", "xyz"),
                "Sorry, that building name already exists. Try another name!");
    }

    @Test
    public void removeBuildingShouldReturnNotExist() { // "Kilrule" is not added to the university
        assertEquals(controller.removeBuilding("Kilrule"),
                "The building doesn't exist in the university!");
    }

    @Test
    public void addRoomShouldReturnAlreadyExist() { // "BF1" already in Balfour
        assertEquals(controller.addRoom("BF1", "Balfour"),
                "Sorry, that room name/number already exists in the building!");

    }

    @Test
    public void removeRoomShouldReturnRoomNotExist() {
        assertEquals(controller.removeRoom("BF5", "Balfour"),
                "Sorry, the room name/number does not exist in the building!");
    }

    @Test
    public void removeRoomShouldReturnBuildingNotExist() {
        assertEquals(controller.removeRoom("BF1", "Garret"),
                "Sorry, the building entered is not in the University!");
    }

    @Test
    public void addBookingShouldReturnSuccess() {
        // BF1 Booking
        String expected = controller.addBooking("kb@gmail.com",
                "Balfour", "BF1",
                "2022-08-13", "16:00", "60");
        assertEquals(expected, "Room booked successfully!");
    }

    @Test
    public void addBookingShouldReturnPersonNotExist() {
        // BF1 Booking, email id not registered
        assertEquals(controller.addBooking("kiran@gmail.com",
                        "Balfour", "BF1",
                        "2022-08-13", "16:00", "60"),
                "The person does not exist!");
    }


    @Test
    public void addBookingShouldReturnAlreadyBooked() {
        // Booking the room
        controller.addBooking("kb@gmail.com",
                "Balfour", "BF1",
                "2022-12-08", "10:00",
                "60");
        //trying to book on the same day
        assertEquals(controller.addBooking("dan@gmail.com",
                        "Balfour", "BF1",
                        "2022-12-08", "10:00",
                        "60"),
                "Sorry, already booked for this time slot. Choose other timings!");
    }

    @Test
    public void addBookingShouldReturnBuildingNotExist() {
        assertEquals(controller.addBooking("dan@gmail.com",
                        "Garret", "BF1",
                        "2022-12-08", "10:00",
                        "60"),
                "Sorry, the building entered is not in the University!");
    }

    @Test
    public void addBookingShouldReturnRoomNotExist() {
        assertEquals(controller.addBooking("dan@gmail.com",
                        "Balfour", "BF6",
                        "2022-12-08", "10:00",
                        "60"),
                "Given room does not exist in the building!");
    }

    @Test
    public void removeBookingShouldReturnPersonNotExist() {
        assertEquals(controller.removeBooking("vijay@gmail.com",
                        "Balfour", "BF1",
                        "2022-12-08", "10:00"),
                "The person does not exist!");
    }

    @Test
    public void removeBookingShouldReturnRoomNotExist() {
        assertEquals(controller.removeBooking("kb@gmail.com",
                        "Balfour", "BF6",
                        "2022-12-08", "10:00"),
                "Given room does not exist in the building!");
    }

    @Test
    public void removeBookingShouldReturnBuildingNotExist() {
        assertEquals(controller.removeBooking("kb@gmail.com",
                        "Garret", "BF1",
                        "2022-12-08", "10:00"),
                "Sorry, the building entered is not in the University!");
    }

    @Test
    public void removeBookingShouldReturnNoBookings() {
        assertEquals(controller.removeBooking("kb@gmail.com",
                        "Balfour", "BF1",
                        "2022-12-08", "10:00"),
                "No bookings for the given details!");
    }

    @Test
    public void roomsAvailAtTimeShouldReturnRooms() {
        String expected = controller.roomsAvailableAtGivenTime("10:30", "2022-11-08").toString();
        // will be equal as none of the rooms are booked!
        assertEquals(expected, "bf1\n" + "bf2\n" +
                "bf3\n" + "nb1\n" + "nb2\n" + "nb3\n");
    }

    @Test
    public void roomSAvailATTimeSlotShouldReturn() {
        String expected = String.valueOf(controller.roomsAvailableAtGivenTimePeriod(
                "00:30", "07:30", "2022-12-08"));
        // will be equal as none of the rooms are booked!
        assertEquals(expected, "bf1\n" + "bf2\n" +
                "bf3\n" + "nb1\n" + "nb2\n" + "nb3\n");
    }

    @Test
    public void roomScheduleShouldReturnNoReservations() {
        String expected = String.valueOf(controller.roomSchedule("BF1", "Balfour"));
        assertEquals(expected, "No reservations yet for this room");
    }

    @Test
    public void viewBookingsByPersonShouldReturnNoReservation() {
        String expected = String.valueOf(controller.viewBookingsByPerson(
                "dan@gmail.com"));
        assertEquals(expected, "No reservations yet");
    }
}