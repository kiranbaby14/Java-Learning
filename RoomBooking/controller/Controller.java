package controller;

import model.University;
import model.universityAttributes.Booking;
import model.universityAttributes.Building;
import model.universityAttributes.Person;
import model.universityAttributes.Room;
import view.guiview.CliGUI;
import view.guiview.SwingGUI;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Controller implementation of MVC model for Room Bookings.
 */
public class Controller {

    private University university; // university model
    private CliGUI cliGUI; // view
    private SwingGUI swingGUI; // view

    //Regex to check email pattern
    private static final String EMAIL_REGEX_PATTERN = "^(.+)@(.+).(.+)$";

    /**
     * Constructor of controller.
     * @param university model
     * @param cliGUI view
     * @param swingGUI view
     */
    public Controller(University university, CliGUI cliGUI, SwingGUI swingGUI) {
        this.university = university;
        this.cliGUI = cliGUI;
        this.swingGUI = swingGUI;

        this.cliGUI.setController(this); // calls setter method inside CLI view
        this.swingGUI.setController(this); // calls setter method inside Swing view

    }

    /**
     * Method to add person into the university.
     * @param name name of the person
     * @param email email of the person which has to be unique
     * @return status message
     */
    public String addPerson(String name, String email) {
        try {
            isValueNull(name); // null case validation
            isValueNull(email); // null case validation
            isValidEmail(email); // email validation
            name = name.toLowerCase();
            email = email.toLowerCase();
            Person person = new Person(name, email);
            // email id should be unique for people
            boolean emailAlreadyExists = emailExistsOrNot(email); // calls method to check whether person already exists or not!

            if (!emailAlreadyExists) {
                this.university.addPerson(person);
                return "Person added Successfully to the University";
            } else {
                return "Sorry, we already have a student with the same Mail ID!";
            }
        } catch (Exception e) { // catches all exceptions
            return e.getMessage();
        }
    }

    /**
     * Method to remove a person from the university.
     * @param email email of the person which has to be unique
     * @return
     */
    public String removePerson(String email) {
        try {
            isValueNull(email);
            isValidEmail(email);
            email = email.toLowerCase();

            boolean emailExists = emailExistsOrNot(email); // check person already in the university or not

            if (emailExists) {
                Person person = getPersonFromUniversity(email); // get the person from the university  if he exist
                this.university.removePerson(person); // removes the person from the university
                return "Person removed from the University";
            } else {
                return "The person does not exist!";
            }

        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * Method to add a building into the university.
     * @param name name of the building which has to be unique
     * @param address address of the university (can be same for different buildings
     *                as university buildings may reside in the same address
     * @return status of adding building to the university
     */
    public String addBuilding(String name, String address) {
        try {
            isValueNull(name);
            isValueNull(address);
            name = name.toLowerCase();
            address = address.toLowerCase();
            Building building = new Building(name, address);
            //building names should be unique
            boolean buildingNameExists = buildingExistsOrNot(name); // check whether building exists or not using its name as it is unique

            if (!buildingNameExists) {
                this.university.addBuilding(building); // if building not exist add to the university
                return "Building added to the University";

            } else {
                return "Sorry, that building name already exists. Try another name!";
            }
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * Method to remove building from the university.
     * @param name name of the building; as it is unique to buildings
     * @return status of removal of the building from the university
     */
    public String removeBuilding(String name) {
        try {
            isValueNull(name);
            name = name.toLowerCase();
            boolean buildingNameExists = buildingExistsOrNot(name); // check whether  building is in the university

            if (buildingNameExists) {
                Building building = getBuildingFromUniversity(name);
                this.university.removeBuilding(building); // remove building from university
                return "Building removed from the University";
            } else {
                return "The building doesn't exist in the university!";
            }
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * Method to add room to a particular building.
     * @param roomName room number/name
     * @param buildingName building name
     * @return status of adding the room to the building
     */
    public String addRoom(String roomName, String buildingName) {
        try {

            isValueNull(roomName);
            isValueNull(buildingName);
            roomName = roomName.toLowerCase();
            buildingName = buildingName.toLowerCase();
            // room name/number inside a building should be unique
            Room room = new Room(roomName);
            boolean buildingExists = buildingExistsOrNot(buildingName); // check whether building exists or not

            if (buildingExists) {
                Building building = getBuildingFromUniversity(buildingName); // get the building from the university
                boolean roomNameExists = roomExistsOrNot(roomName, building); // check whether room exists in the building or not

                if (!roomNameExists) {
                    building.addRoom(room); // add room to the building
                    return "Room added Successfully";
                } else {
                    return "Sorry, that room name/number already exists in the building!";
                }
            } else {
                return "Sorry the building entered is not in the University";
            }
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * Method to remove the room from the building.
     * @param roomName name of the room
     * @param buildingName mname of the building
     * @return status of removing the room from the building
     */
    public String removeRoom(String roomName, String buildingName) {
        try {

            isValueNull(roomName);
            isValueNull(buildingName);
            roomName = roomName.toLowerCase();
            buildingName = buildingName.toLowerCase();

            boolean buildingExists = buildingExistsOrNot(buildingName); // check for building in the university

            if (buildingExists) {
                Building building = getBuildingFromUniversity(buildingName); // get building from university
                boolean roomExistsInBuilding = roomExistsOrNot(roomName, building);

                if (roomExistsInBuilding) {
                    Room room = getRoomFromBuilding(roomName, building); // get room from the building
                    building.removeRoom(room);
                    return "Room successfully removed from the building";
                } else {
                    return "Sorry, the room name/number does not exist in the building!";
                }

            } else {
                return "Sorry, the building entered is not in the University!";
            }
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * Method to add booking to a room for a particular duration in a particular building during a given date and time.
     * @param personEmail email inorder to uniquely identify the person
     * @param buildingName name of the building
     * @param roomName name of the room
     * @param date date on which booking to be done
     * @param bookingStartTime start time of booking
     * @param duration duration of stay
     * @return status of booking
     */
    public String addBooking(String personEmail, String buildingName,
                             String roomName, String date,
                             String bookingStartTime, String duration) {

        try {
            //exception cases
            isValueNull(personEmail);
            isValueNull(buildingName);
            isValueNull(roomName);
            isValueNull(date);
            isValueNull(bookingStartTime);
            isValueNull(duration);
            isValidEmail(personEmail);

            personEmail = personEmail.toLowerCase();
            buildingName = buildingName.toLowerCase();
            roomName = roomName.toLowerCase();

            LocalDate convertedDate = getConvertedDate(date); // convert string to LocalDate format
            LocalTime currentBookingTime = getConvertedTime(bookingStartTime); //convert string to LocalTime format
            int convertedDuration = getConvertedDuration(duration); // convert string to int

            isValidDuration(convertedDuration);  // check for 5 mins, and > 5mins cases

            boolean emailExists = emailExistsOrNot(personEmail); // check whether the person is in the university

            if (emailExists) {
                Person person = getPersonFromUniversity(personEmail); // get person from the university

                boolean buildingExists = buildingExistsOrNot(buildingName); // check building exists or not

                if (buildingExists) {
                    Building building = getBuildingFromUniversity(buildingName); // get building from the university

                    boolean roomExistsInBuilding = roomExistsOrNot(roomName, building);

                    if (roomExistsInBuilding) {
                        Room room = getRoomFromBuilding(roomName, building); // get the room from the building
                        boolean timeClash = false;
                        LocalTime currentBookingEndTime = currentBookingTime.plusMinutes(convertedDuration); // get booking end time
                        for (Booking booking : room.getBookings()) {
                            if (booking.getDate().equals(convertedDate) && //if the given booking time matches any other booking
                                    ((currentBookingTime.isAfter(booking.getBookingStartTime()) &&
                                            currentBookingTime.isBefore(booking.getBookingEndTime())) || // checks if the start time is in between the other booking range
                                            (currentBookingEndTime.isAfter(booking.getBookingStartTime()) &&
                                                    currentBookingEndTime.isBefore(booking.getBookingEndTime())) || // checks if end time is in between other ranges
                                            currentBookingTime.equals(booking.getBookingStartTime()))) {
                                timeClash = true;
                                break;
                            }
                        }
                        if (!timeClash) {
                            Booking booking = new Booking(person, convertedDate,
                                    currentBookingTime, convertedDuration);
                            room.addBooking(booking);
                            return "Room booked successfully!";
                        } else {
                            return "Sorry, already booked for this time slot. Choose other timings!";
                        }

                    } else {
                        return "Given room does not exist in the building!";
                    }
                } else {
                    return "Sorry, the building entered is not in the University!";
                }

            } else {
                return "The person does not exist!";
            }
        } catch (Exception e) {
            return e.getMessage();
        }

    }

    /**
     * Method to remove booking of a room from a particular building.
     * @param personEmail email to uniquely identify a person
     * @param buildingName name of the building
     * @param roomName room name
     * @param date date of booking
     * @param bookingStartTime start  time of booking
     * @return status of remove booking
     */
    public String removeBooking(String personEmail, String buildingName,
                                String roomName, String date,
                                String bookingStartTime) {

        try {
            //exception cases
            isValueNull(personEmail);
            isValueNull(buildingName);
            isValueNull(roomName);
            isValueNull(date);
            isValueNull(bookingStartTime);
            isValidEmail(personEmail);

            personEmail = personEmail.toLowerCase();
            buildingName = buildingName.toLowerCase();
            roomName = roomName.toLowerCase();

            LocalDate convertedDate = getConvertedDate(date); // converts string to LocalDate
            LocalTime currentBookingTime = getConvertedTime(bookingStartTime); // converts string to LocalTime

            boolean emailExists = emailExistsOrNot(personEmail);
            if (emailExists) {
                Person person = getPersonFromUniversity(personEmail); // get person from the email

                boolean buildingExists = buildingExistsOrNot(buildingName);

                if (buildingExists) {
                    Building building = getBuildingFromUniversity(buildingName); //get building from the university

                    boolean roomExistsInBuilding = roomExistsOrNot(roomName, building);

                    if (roomExistsInBuilding) {

                        Room room = getRoomFromBuilding(roomName, building);
                        for (Booking booking : room.getBookings()) {
                            if (booking.getPerson().equals(person) && //if the person matches the given person
                                    booking.getDate().equals(convertedDate) && //if date matches the given date
                                    booking.getBookingStartTime().equals(currentBookingTime)) { // if starttime of booking matches the given one
                                room.removeBooking(booking);
                                return "Booking Cancelled successfully";
                            }
                        }
                    } else {
                        return "Given room does not exist in the building!";
                    }
                } else {
                    return "Sorry, the building entered is not in the University!";
                }
            } else {
                return "The person does not exist!";
            }

            return "No bookings for the given details!";
        } catch (Exception e) {
            return e.getMessage();
        }

    }

    /**
     * Method to see the rooms available at a particular time.
     * @param time search time
     * @param date search date
     * @return status of the search
     */
    public StringBuffer roomsAvailableAtGivenTime(String time, String date) {
        try {
            isValueNull(time);
            isValueNull(date);

            LocalTime convertedTime = getConvertedTime(time); // convert string to LocalTime
            LocalDate convertedDate = getConvertedDate(date);
            ArrayList<Room> rooms = new ArrayList<>();
            for (Building building : this.university.getBuildings()) {
                for (Room room : building.getRooms()) {
                    if (room.getBookings().isEmpty()) { //available at all time and date
                        rooms.add(room);
                    } else {
                        boolean roomAvailable = true;
                        for (Booking booking : room.getBookings()) {
                            if (booking.getDate().isEqual(convertedDate)) { //if given date matches the existing dates
                                if (booking.getBookingStartTime().equals(convertedTime) || // if given starttime matches any existing one
                                        (convertedTime.isAfter(booking.getBookingStartTime()) &&
                                                convertedTime.isBefore(booking.getBookingEndTime()))) { //checks if given time is in between any other bookings
                                    roomAvailable = false;
                                    break;
                                }
                            }
                        }
                        if (roomAvailable) {
                            rooms.add(room);
                        }
                    }

                }
            }
            if (!rooms.isEmpty()) {
                StringBuffer roomsAvailableAtGivenTime = new StringBuffer();
                for (Room room : rooms) {
                    roomsAvailableAtGivenTime.append(room).append("\n");
                }
                return roomsAvailableAtGivenTime;
            } else {
                return new StringBuffer("No rooms available");
            }


        } catch (Exception e) {
            StringBuffer errorMessage = new StringBuffer();
            errorMessage.append(e.getMessage());
            return errorMessage;
        }

    }


    /**
     * Method to get all the rooms in a given range.
     * @param startTime start time of the search
     * @param endTime end time of the  search
     * @param date search date
     * @return status of the search
     */
    public StringBuffer roomsAvailableAtGivenTimePeriod(String startTime,
                                                        String endTime,
                                                        String date) {
        try {
            isValueNull(startTime);
            isValueNull(endTime);
            isValueNull(date);
            LocalTime convertedStartTime = getConvertedTime(startTime);
            LocalTime convertedEndTime = getConvertedTime(endTime);
            LocalDate convertedDate = getConvertedDate(date);
            ArrayList<Room> rooms = new ArrayList<>();
            for (Building building : this.university.getBuildings()) {
                for (Room room : building.getRooms()) {
                    if (room.getBookings().isEmpty()) { // available for date and time
                        rooms.add(room);
                    } else {
                        boolean roomAvailable = true;
                        for (Booking booking : room.getBookings()) {
                            if (booking.getDate().isEqual(convertedDate)) { // if date matches
                                if (booking.getBookingStartTime().equals(convertedStartTime) || // if start time matches
                                        (convertedStartTime.isAfter(booking.getBookingStartTime()) &&
                                                convertedStartTime.isBefore(booking.getBookingEndTime())) || // if start time is in between other booking times
                                        (convertedEndTime.isAfter(booking.getBookingStartTime()) && // if end time is in range of other booking times
                                                convertedEndTime.isBefore(booking.getBookingEndTime()))) {
                                    roomAvailable = false;
                                    break;
                                }

                                for (LocalTime time = convertedStartTime; time.isBefore(convertedEndTime); time = time.plusMinutes(1)) {
                                    //loop from start time to end time
                                    if (booking.getBookingStartTime().equals(time) || // if any time matches start time
                                            (time.isAfter(booking.getBookingStartTime()) && // checks if time is in between other bookings
                                                    time.isBefore(booking.getBookingEndTime()))) {
                                        roomAvailable = false;
                                        break;
                                    }
                                }

                            }
                        }
                        if (roomAvailable) {
                            rooms.add(room);
                        }
                    }

                }
            }

            if (!rooms.isEmpty()) {
                StringBuffer roomsAvailableAtGivenTime = new StringBuffer();
                for (Room room : rooms) {
                    roomsAvailableAtGivenTime.append(room).append("\n");
                }
                return roomsAvailableAtGivenTime;
            } else {
                return new StringBuffer("No rooms available");
            }

        } catch (Exception e) {
            StringBuffer errorMessage = new StringBuffer();
            errorMessage.append(e.getMessage());
            return errorMessage;
        }
    }

    /**
     * get all bookings and free times of a room.
     * @param roomName name of the room
     * @param buildingName building name
     * @return status of the search
     */
    public StringBuffer roomSchedule(String roomName, String buildingName) {

        try {
            StringBuffer roomSchedule = new StringBuffer();
            isValueNull(roomName);
            isValueNull(buildingName);

            roomName = roomName.toLowerCase();
            buildingName = buildingName.toLowerCase();

            boolean buildingExists = buildingExistsOrNot(buildingName);

            if (buildingExists) {
                Building building = getBuildingFromUniversity(buildingName);
                boolean roomExistsInBuilding = roomExistsOrNot(roomName, building);
                if (roomExistsInBuilding) {
                    Room room = getRoomFromBuilding(roomName, building);
                    if (!room.getBookings().isEmpty()) { // if the bookings list is empty for a room
                        roomSchedule.append("Bookings\n");
                        roomSchedule.append("--------\n");

                        for (Booking booking : room.getBookings()) { // output style format
                            roomSchedule.append(booking.getBookingStartTime()).append("--").
                                    append(booking.getBookingEndTime()).append(" (").
                                    append(booking.getDate()).
                                    append(")").append("\n");
                        }

                        roomSchedule.append("\nFree Periods\n");
                        roomSchedule.append("------------\n");
                        LocalDate firstDateInBookings = room.getBookings().get(0).getDate(); // get the first date from
                                                                                            // booking list of the room (which is sorted)
                        LocalDate lastDateInBookings = room.getBookings().get(room.getBookings().size() - 1).getDate(); // get the last date from the list of bookings
                        LocalTime startTimeOfDay = getConvertedTime("00:00");
                        LocalTime endTimeOfDay = getConvertedTime("23:59");
                        LocalTime tempTime;
                        for (LocalDate date = firstDateInBookings;
                             date.isBefore(lastDateInBookings.plusDays(1));
                             date = date.plusDays(1)) { // loop through the dates in the booking list
                            tempTime = startTimeOfDay;
                            for (LocalTime time = startTimeOfDay;
                                 time.isBefore(endTimeOfDay);
                                 time = time.plusMinutes(1)) { //loop through each minute from the starting of the day
                                for (Booking booking : room.getBookings()) {
                                    if (booking.getBookingStartTime().equals(time) && booking.getDate().isEqual(date)) {
                                        roomSchedule.append(tempTime).append("--").
                                                append(time).append(" (").
                                                append(booking.getDate()).
                                                append(")").append("\n");
                                        tempTime = time.plusMinutes(booking.getDuration());
                                        time = tempTime;
                                    }
                                }
                            }
                            roomSchedule.append(tempTime).append("--").
                                    append(endTimeOfDay).append(" (").
                                    append(date).append(")").append("\n");
                        }
                    } else {
                        return roomSchedule.append("No reservations yet for this room");
                    }
                    return roomSchedule;
                } else {
                    roomSchedule.append("Sorry, the room name/number does not exist in the building!");
                    return roomSchedule;
                }
            } else {
                roomSchedule.append("Sorry, the building entered is not in the University!");
                return roomSchedule;
            }
        } catch (Exception e) {
            StringBuffer roomSchedule = new StringBuffer();
            return roomSchedule.append(e.getMessage());
        }
    }

    /**
     * Method to view the bookings made by a person.
     * @param email email of the person
     * @return returns the bookings of the person
     */
    public StringBuffer viewBookingsByPerson(String email) {
        try {
            isValueNull(email);
            email = email.toLowerCase();
            StringBuffer viewBookingsByPerson = new StringBuffer();
            isValidEmail(email);
            boolean emailExists = emailExistsOrNot(email);

            if (emailExists) {
                boolean isBooking = false;
                Person person = getPersonFromUniversity(email); // get person from the university
                viewBookingsByPerson.append("BOOKINGS BY PERSON\n");
                viewBookingsByPerson.append("------------------\n");
                for (Building building : this.university.getBuildings()) {
                    for (Room room : building.getRooms()) {
                        for (Booking booking : room.getBookings()) {
                            if (booking.getPerson().equals(person)) {
                                viewBookingsByPerson.append(building.getName()).append("--").
                                        append(room.getName()).append("--(").append(booking.getDate()).
                                        append(")--(Time: ").append(booking.getBookingStartTime()).
                                        append(")--(Duration: ").append(booking.getDuration()).append(")\n");
                                isBooking = true;
                            }
                        }
                    }
                }
                if (!isBooking) {
                    return new StringBuffer("No reservations yet");
                }
            } else {
                viewBookingsByPerson.append("The person does not exist!");
            }
            return viewBookingsByPerson;
        } catch (Exception e) {
            StringBuffer viewBookingsByPerson = new StringBuffer();
            viewBookingsByPerson.append(e.getMessage());
            return viewBookingsByPerson;
        }
    }

    /**
     * Method to save the data to a file.
     * @param dirPath directory path
     * @param nameOfFile name of the file
     * @return saves the data onto a file
     */
    public String saveToFile(String dirPath, String nameOfFile) {
        try {
            isValueNull(dirPath);
            isValueNull(nameOfFile);
            if ((new File((dirPath)).exists())) {
                String filePath = dirPath + nameOfFile + ".txt";
                File file = new File(filePath);
                FileOutputStream fos = new FileOutputStream(file);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(this.university);
                oos.close();
                fos.close();
                return "File saved successfully";
            } else {
                throw new IllegalArgumentException("File path doesn't exist");
            }
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * Method to load data from a file and it  overwrites the existing data.
     * @param filePath path to the file
     * @return loads and overwrites the existing data
     */
    public String loadFromFile(String filePath) {
        try {
            isValueNull(filePath);
            if ((new File((filePath)).exists())) {
                File file = new File(filePath);
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                this.university = (University) ois.readObject();
                fis.close();
                ois.close();
                return "File Loaded successfully. Data is overridden!";
            } else {
                throw new IllegalArgumentException("File path doesn't exist");
            }
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * Method to validate the email.
     * @param email email to be validated
     */
    public void isValidEmail(final String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX_PATTERN);
        if (!pattern.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid email");
        }
    }

    /**
     * Method to validate duration.
     * @param duration duration to be validated
     */
    private void isValidDuration(int duration) {
        if (!(duration >= 5 && duration % 5 == 0)) {
            throw new IllegalArgumentException("Should be > 5 min and multiple of 5 mins..");
        }
    }

    /**
     * Method to check whether an email with the person exists or not.
     * @param email
     * @return
     */
    public boolean emailExistsOrNot(String email) {
        for (Person person : this.university.getPeople()) {
            if (person.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method to get the person from the university.
     * @param email email of the person to be searched for
     * @return person
     */
    public Person getPersonFromUniversity(String email) {
        for (Person person : this.university.getPeople()) {
            if (person.getEmail().equals(email)) {
                return person;
            }
        }
        return null;
    }

    /**
     * Method to check whether a building exists in the university or not.
     * @param buildingName building name
     * @return boolean
     */
    public boolean buildingExistsOrNot(String buildingName) {
        for (Building building : this.university.getBuildings()) {
            if (building.getName().equals(buildingName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method to get the building from the university.
     * @param buildingName building name
     * @return the building
     */
    public Building getBuildingFromUniversity(String buildingName) {
        for (Building building : this.university.getBuildings()) {
            if (building.getName().equals(buildingName)) {
                return building;
            }
        }
        return null;
    }

    /**
     * Method to check whether the room exists or not.
     * @param roomName room name
     * @param building building
     * @return boolean
     */
    public boolean roomExistsOrNot(String roomName, Building building) {
        for (Room room : building.getRooms()) {
            if (room.getName().equals(roomName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method to get the room from the building.
     * @param roomName room name
     * @param building building
     * @return room from the building
     */
    public Room getRoomFromBuilding(String roomName, Building building) {
        for (Room room : building.getRooms()) {
            if (room.getName().equals(roomName)) {
                return room;
            }
        }
        return null;
    }

    /**
     * Method to convert date from string to LocalDate.
     * @param date date r=to be converted
     * @return converted date
     */
    public LocalDate getConvertedDate(String date) {
        try {
            return LocalDate.parse(date);
        } catch (Exception e) {
            throw new IllegalArgumentException("Date Format: (YYYY-MM-DD)");
        }
    }

    /**
     * Method to convert time from string to LocalTime.
     * @param time time to be converted
     * @return converted time
     */
    public LocalTime getConvertedTime(String time) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            return LocalTime.parse(time, formatter);
        } catch (Exception e) {
            throw new IllegalArgumentException("Time Format: (HH:mm)");
        }
    }

    /**
     * Method to covert the duration from string to int.
     * @param duration duration to be converted
     * @return converted duration
     */
    public int getConvertedDuration(String duration) {
        try {
            return Integer.parseInt(duration);
        } catch (
                Exception e) {
            throw new IllegalArgumentException("Duration: should be in minutes (int)");
        }
    }

    /**
     * Method to check if an input is null or not.
     * @param input input to be checked
     */
    public void isValueNull(String input) {
        if (input.isEmpty()) {
            throw new IllegalArgumentException("Null value given");
        }
    }
}
