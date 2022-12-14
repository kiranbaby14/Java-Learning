package view.guiview;

import controller.Controller;

import java.util.Scanner;

/**
 * CLI GUI of view in MVC
 */
public class CliGUI {

    private Controller controller; // controller
    private boolean continueOption;
    private final Scanner input = new Scanner(System.in);

    /**
     * method gets called whenever program is run.
     */
    public void start() {
        String option;
        choices();

        while ((option = userChoice()) != null) {
            switch (option) { // choose from the given options
                case "1" -> {
                    addOrRemoveChoices();
                    addOrRemove();
                }
                case "2" -> roomsAvailableAtGivenTime();
                case "3" -> roomsAvailableAtGivenTimePeriod();
                case "4" -> roomSchedule();
                case "5" -> viewBookingsByPerson();
                case "6" -> saveToFile();
                case "7" -> loadFromFile();
                case "8" -> System.exit(1);
            }
            start();
        }


    }

    /**
     * Method to print the options to CLI.
     */
    public void choices() {
        System.out.println("""

                CHOOSE AN OPTION (1-8)
                ----------------------
                1. Add and remove people, rooms, buildings and bookings
                2. Choose a time, and see all rooms that are available at that time
                3. Choose a timeslot (start and end times) and see all rooms that are available for that whole period
                4. View the schedule for a given room, including bookings and free periods
                5. View all bookings made by a given person
                6. Save to a specified filename
                7. Load from a specified file
                8. QUIT
                """);
    }

    /**
     * Method to call add or remove options.
     */
    private void addOrRemoveChoices() {
        System.out.println("""

                CHOOSE AN OPTION TO ADD?REMOVE(1-9)
                ----------------------
                1. Add Person
                2. Remove Person
                3. Add Building
                4. Remove Building
                5. Add Room
                6. Remove Room
                7. Add Booking
                8. Remove Booking
                9. Go Back
                """);
    }

    /**
     * Add or remove implementations.
     */
    public void addOrRemove() {
        String addOrRemoveInput = userChoice();

        switch (addOrRemoveInput) { // choose from the given options
            case "1":
                System.out.println("\nADD PERSON");
                System.out.println("----------");

                System.out.print("Enter Person Name: ");
                String addPersonName = input.next();

                System.out.print("Enter Person Email: ");
                String addPersonEmail = input.next();

                String addPersonOutput = controller.addPerson(addPersonName,
                        addPersonEmail); //pass the values to the controller
                print(addPersonOutput);

                continueOption = continueOption();

                if (continueOption) { //continue choice
                    addOrRemoveChoices();
                    addOrRemove();
                }
                break;

            case "2":
                System.out.println("\nREMOVE PERSON");
                System.out.println("-------------");

                System.out.print("Enter Person Email: ");
                String removePerson = input.next();

                String removePersonOutput =
                        controller.removePerson(removePerson); //pass the values to the controller
                print(removePersonOutput);

                continueOption = continueOption();

                if (continueOption) { //continue option
                    addOrRemoveChoices();
                    addOrRemove();
                }
                break;

            case "3":
                System.out.println("\nADD BUILDING");
                System.out.println("------------");

                System.out.print("Enter Building Name: ");
                String addBuildingName = input.next();

                System.out.print("Enter Building Address: ");
                String addBuildingAddress = input.next();

                String addBuildingOutput =
                        controller.addBuilding(addBuildingName,
                                addBuildingAddress); //pass the values to the controller
                print(addBuildingOutput);

                continueOption = continueOption();

                if (continueOption) {
                    addOrRemoveChoices();
                    addOrRemove();
                }
                break;

            case "4":
                System.out.println("\nREMOVE BUILDING");
                System.out.println("---------------");

                System.out.print("Enter Building Name: ");
                String removeBuilding = input.next();

                String removeBuildingOutput = controller.removeBuilding(removeBuilding);
                print(removeBuildingOutput);

                continueOption = continueOption();

                if (continueOption) {
                    addOrRemoveChoices();
                    addOrRemove();
                }
                break;

            case "5":
                System.out.println("\nADD ROOM");
                System.out.println("--------");

                System.out.print("Enter Building Name: ");
                String addRoomToBuildingName = input.next();

                System.out.print("Enter Room Name: ");
                String addRoomName = input.next();

                String addRoomOutput = controller.addRoom(addRoomName, addRoomToBuildingName);
                print(addRoomOutput);

                continueOption = continueOption();

                if (continueOption) {
                    addOrRemoveChoices();
                    addOrRemove();
                }
                break;

            case "6":
                System.out.println("\nREMOVE ROOM");
                System.out.println("-----------");

                System.out.print("Enter Building Name: ");
                String removeRoomFromBuilding = input.next();

                System.out.print("Enter Room Name: ");
                String removeRoom = input.next();

                String removeRoomOutput = controller.removeRoom(removeRoom, removeRoomFromBuilding);
                print(removeRoomOutput);

                continueOption = continueOption();

                if (continueOption) {
                    addOrRemoveChoices();
                    addOrRemove();
                }
                break;

            case "7":
                System.out.println("\nADD BOOKING");
                System.out.println("-----------");

                System.out.print("Enter Person Email: ");
                String addBookingPersonEmail = input.next();

                System.out.print("Enter Building Name: ");
                String addBookingBuildingName = input.next();

                System.out.print("Enter Room Name/Number: ");
                String addBookingRoomName = input.next();

                System.out.print("Enter Booking  Date (YYYY-MM-DD): ");
                String addBookingDate = input.next();

                System.out.print("Enter Booking Time (HH:mm): ");
                String addBookingTime = input.next();

                System.out.print("Enter Booking Duration: ");
                String addBookingDuration = input.next();

                String addBookingOutput = controller.addBooking(
                        addBookingPersonEmail, addBookingBuildingName,
                        addBookingRoomName, addBookingDate,
                        addBookingTime, addBookingDuration); //pass the values to the controller
                print(addBookingOutput);

                continueOption = continueOption();

                if (continueOption) {
                    addOrRemoveChoices();
                    addOrRemove();
                }
                break;

            case "8":
                System.out.println("\nREMOVE BOOKING");
                System.out.println("--------------");

                System.out.print("Enter Person Email: ");
                String removeBookingPersonEmail = input.next();

                System.out.print("Enter Building Name: ");
                String removeBookingBuildingName = input.next();

                System.out.print("Enter Room Name/Number: ");
                String removeBookingRoomName = input.next();

                System.out.print("Enter Booking  Date (YYYY-MM-DD): ");
                String removeBookingDate = input.next();

                System.out.print("Enter Booking Time (HH:mm): ");
                String removeBookingTime = input.next();

                String removeBookingOutput = controller.removeBooking(removeBookingPersonEmail, removeBookingBuildingName,
                        removeBookingRoomName, removeBookingDate, removeBookingTime);
                print(removeBookingOutput);


                continueOption = continueOption();

                if (continueOption) {
                    addOrRemoveChoices();
                    addOrRemove();
                }
                break;

            case "9": //Go back option
                break;

            default:
                addOrRemove();
        }
    }

    /**
     * Method to implement rooms available at a particular time in CLI.
     */
    public void roomsAvailableAtGivenTime() {
        System.out.println("\nROOMS AVAILABLE AT A GIVEN TIME");
        System.out.println("-------------------------------");
        System.out.print("Enter Booking  Date (YYYY-MM-DD): ");
        String date = input.next();
        System.out.print("Enter the time (HH:mm): ");
        String time = input.next();

        StringBuffer roomsAvailable = controller.roomsAvailableAtGivenTime(time, date);
        printBuffer(roomsAvailable);

        continueOption = continueOption();

        if (continueOption) {
            roomsAvailableAtGivenTime();
        }
    }

    /**
     * Method to implement rooms available in a given time slot in the CLI.
     */
    public void roomsAvailableAtGivenTimePeriod() {
        System.out.println("\nROOMS AVAILABLE AT A GIVEN TIME PERIOD");
        System.out.println("--------------------------------------");
        System.out.print("Enter Booking  Date (YYYY-MM-DD): ");
        String date = input.next();
        System.out.print("Enter the Start time (HH:mm): ");
        String startTime = input.next();
        System.out.print("Enter the End time (HH:mm): ");
        String endTime = input.next();

        StringBuffer roomsAvailable =
                controller.roomsAvailableAtGivenTimePeriod(startTime,
                        endTime, date); //pass the values to the controller
        printBuffer(roomsAvailable);

        continueOption = continueOption();

        if (continueOption) {
            roomsAvailableAtGivenTime();
        }
    }

    /**
     * Method to implement viewing room scheduling in CLI.
     */
    public void roomSchedule() {
        System.out.println("\nROOM SCHEDULE");
        System.out.println("-------------");
        System.out.print("Enter Building Name: ");
        String buildingName = input.next();
        System.out.print("Enter Room Name: ");
        String roomName = input.next();

        StringBuffer roomSchedule = controller.roomSchedule(roomName, buildingName);
        printBuffer(roomSchedule);

        continueOption = continueOption();

        if (continueOption) {
            roomSchedule();
        }
    }

    /**
     * Method to view the bookings by a person in the CLI.
     */
    public void viewBookingsByPerson() {
        System.out.println("\nVIEW BOOKINGS BY PERSON");
        System.out.println("-----------------------");
        System.out.print("Enter the email: : ");
        String email = input.next();

        StringBuffer viewBookingsByPerson = controller.viewBookingsByPerson(email);
        printBuffer(viewBookingsByPerson);

        continueOption = continueOption();

        if (continueOption) {
            viewBookingsByPerson();
        }
    }

    /**
     * Method to save the file from CLI.
     */
    public void saveToFile() {
        System.out.println("\nSAVE TO A FILE");
        System.out.println("--------------");
        System.out.print("Directory path (with slash): ");
        String dirPath = input.next();
        System.out.print("File name (file type not required): ");
        String nameOfFile = input.next();

        String saveToFile = controller.saveToFile(dirPath, nameOfFile);
        print(saveToFile);
        continueOption = continueOption();

        if (continueOption) {
            saveToFile();
        }
    }

    /**
     * Method to implement load data in the CLI.
     */
    public void loadFromFile() {
        System.out.println("\nLOAD FROM A FILE");
        System.out.println("----------------");
        System.out.print("File path: ");
        String filePath = input.next();

        String loadFromFile = controller.loadFromFile(filePath);

        print(loadFromFile);
        continueOption = continueOption();

        if (continueOption) {
            loadFromFile();
        }
    }

    /**
     * Method to make a connection between CLI view and controller.
     *
     * @param controller
     */
    public void setController(Controller controller) {
        this.controller = controller;
    }

    /**
     * Method to print the message.
     *
     * @param message
     */
    public void print(String message) {
        System.out.println("\n*** " + message + " ***\n ");
    }

    /**
     * Method to print the string buffer.
     *
     * @param message
     */
    public void printBuffer(StringBuffer message) {
        System.out.println("\n********\n" + message + "********\n ");
    }


    /**
     * Method to implement user choices in the CLI.
     *
     * @return user choice
     */
    public String userChoice() {

        System.out.print("Your choice: ");
        return input.next();
    }

    /**
     * Method to implement choose continue option from the user.
     *
     * @return continue or not
     */
    public boolean continueOption() {
        System.out.println("Continue ? (Y/N) ");
        String continueOption = userChoice();
        if ((continueOption).equalsIgnoreCase("Y")) {
            return true;
        } else if (continueOption.equalsIgnoreCase("N")) {
            return false;
        } else {
            return continueOption();
        }
    }
}
