package view.guiview;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Swing view in MVC of Room Booking.
 * It implements action listener inorder to listen for button clicks.
 */
public class SwingGUI implements ActionListener {

    private Controller controller; // controller
    JFrame frame; // main frame

    JPanel headingPanel;
    JPanel optionsPanel;

    //Buttons
    JButton addPersonBtn;
    JButton removePersonBtn;
    JButton addBuildingBtn;
    JButton removeBuildingBtn;
    JButton addRoomBtn;
    JButton removeRoomBtn;
    JButton addBookingBtn;
    JButton removeBookingBtn;
    JButton roomsAvailableAtTimeBtn;
    JButton roomsAvailableTimeSlotBtn;
    JButton roomSchedulesBtn;
    JButton showBookingsByPersonBtn;
    JButton saveDataBtn;
    JButton loadDataBtn;

    public void start() {
        //Frame properties
        frame = new JFrame();
        frame.setTitle("Room Booking");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);

        //Panels
        headingPanel = new JPanel();
        optionsPanel = new JPanel();

        //Create main page buttons
        addPersonBtn = new JButton("Add Person");
        removePersonBtn = new JButton("Remove Person");
        addBuildingBtn = new JButton("Add Building");
        removeBuildingBtn = new JButton("Remove Building");
        addRoomBtn = new JButton("Add Room");
        removeRoomBtn = new JButton("Remove Room");
        addBookingBtn = new JButton("Add Booking");
        removeBookingBtn = new JButton("Remove Booking");
        roomsAvailableAtTimeBtn = new JButton("Rooms Available at Given Time");
        roomsAvailableTimeSlotBtn = new JButton("Rooms Available at Time Slot");
        roomSchedulesBtn = new JButton("Show Room schedules");
        showBookingsByPersonBtn = new JButton("Show Bookings by Person");
        saveDataBtn = new JButton("Save Data");
        loadDataBtn = new JButton("Load Data");

        //add Buttons and heading to the panel
        optionsPanel.add(addPersonBtn);
        optionsPanel.add(removePersonBtn);
        optionsPanel.add(addBuildingBtn);
        optionsPanel.add(removeBuildingBtn);
        optionsPanel.add(addRoomBtn);
        optionsPanel.add(removeRoomBtn);
        optionsPanel.add(addBookingBtn);
        optionsPanel.add(removeBookingBtn);
        optionsPanel.add(roomsAvailableAtTimeBtn);
        optionsPanel.add(roomsAvailableTimeSlotBtn);
        optionsPanel.add(roomSchedulesBtn);
        optionsPanel.add(showBookingsByPersonBtn);
        optionsPanel.add(saveDataBtn);
        optionsPanel.add(loadDataBtn);

        //give action listener to all the buttons
        addPersonBtn.addActionListener(this);
        removePersonBtn.addActionListener(this);
        addBuildingBtn.addActionListener(this);
        removeBuildingBtn.addActionListener(this);
        addRoomBtn.addActionListener(this);
        removeRoomBtn.addActionListener(this);
        addBookingBtn.addActionListener(this);
        removeBookingBtn.addActionListener(this);
        roomsAvailableAtTimeBtn.addActionListener(this);
        roomsAvailableTimeSlotBtn.addActionListener(this);
        roomSchedulesBtn.addActionListener(this);
        showBookingsByPersonBtn.addActionListener(this);
        saveDataBtn.addActionListener(this);
        loadDataBtn.addActionListener(this);


        JLabel headingText = new JLabel("Room Booking");
        headingText.setFont(new Font("Serif", Font.PLAIN, 34));
        headingPanel.add(headingText);

        frame.add(headingPanel, BorderLayout.NORTH); // move the heading to the top most  of the page
        frame.add(optionsPanel, BorderLayout.CENTER); // move buttons grid to the center of the page

        optionsPanel.setLayout(new GridLayout(12, 1, 7, 7));
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        frame.setVisible(true);

    }

    /**
     * Overridden method that listens to button clicks.
     *
     * @param button the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent button) {
        if (button.getSource() == addPersonBtn) {
            addPerson();
        }
        if (button.getSource() == removePersonBtn) {
            removePerson();
        }
        if (button.getSource() == addBuildingBtn) {
            addBuilding();
        }
        if (button.getSource() == removeBuildingBtn) {
            removeBuilding();
        }
        if (button.getSource() == addRoomBtn) {
            addRoom();
        }
        if (button.getSource() == removeRoomBtn) {
            removeRoom();
        }
        if (button.getSource() == addBookingBtn) {
            addBooking();
        }
        if (button.getSource() == removeBookingBtn) {
            removeBooking();
        }
        if (button.getSource() == roomsAvailableAtTimeBtn) {
            roomsAvailableAtTime();
        }
        if (button.getSource() == roomsAvailableTimeSlotBtn) {
            roomsAvailableTimeSlot();
        }
        if (button.getSource() == roomSchedulesBtn) {
            roomSchedules();
        }
        if (button.getSource() == showBookingsByPersonBtn) {
            showBookingsByPerson();
        }
        if (button.getSource() == saveDataBtn) {
            saveData();
        }
        if (button.getSource() == loadDataBtn) {
            loadData();
        }
    }

    /**
     * Add person details enter pop up.
     */
    public void addPerson() {
        JPanel panel = new JPanel(new GridLayout(4, 1));
        JTextField text1 = new JTextField(15);
        JTextField text2 = new JTextField(15);
        panel.add(new JLabel("Person name:"));
        panel.add(text1);
        panel.add(new JLabel("Email:"));
        panel.add(text2);
        int optionValue = JOptionPane.showConfirmDialog(null, panel,
                "Add person", JOptionPane.INFORMATION_MESSAGE);

        if (optionValue == 0) {
            String name = text1.getText();
            String email = text2.getText();
            JOptionPane.showMessageDialog(null,
                    this.controller.addPerson(name, email),
                    "Add Person", JOptionPane.INFORMATION_MESSAGE);

        }
    }

    /**
     * Remove person details enter pop up.
     */
    private void removePerson() {
        JPanel panel = new JPanel(new GridLayout(4, 1));
        JTextField text = new JTextField(15);
        panel.add(new JLabel("Person email:"));
        panel.add(text);
        int optionValue = JOptionPane.showConfirmDialog(null,
                panel, "Remove person", JOptionPane.INFORMATION_MESSAGE);

        if (optionValue == 0) {
            String email = text.getText();
            JOptionPane.showMessageDialog(null,
                    this.controller.removePerson(email),
                    "Remove Person", JOptionPane.INFORMATION_MESSAGE);

        }
    }

    /**
     * Add building enter details pop up.
     */
    private void addBuilding() {
        JPanel panel = new JPanel(new GridLayout(4, 1));
        JTextField text1 = new JTextField(15);
        JTextField text2 = new JTextField(15);
        panel.add(new JLabel("Building name:"));
        panel.add(text1);
        panel.add(new JLabel("Building address:"));
        panel.add(text2);
        int optionValue = JOptionPane.showConfirmDialog(null,
                panel, "Add Building", JOptionPane.INFORMATION_MESSAGE);

        if (optionValue == 0) {
            String name = text1.getText();
            String address = text2.getText();
            JOptionPane.showMessageDialog(null,
                    this.controller.addBuilding(name, address),
                    "Add Building", JOptionPane.INFORMATION_MESSAGE);

        }
    }

    /**
     * Remove building enter details pop up.
     */
    private void removeBuilding() {
        JPanel panel = new JPanel(new GridLayout(4, 1));
        JTextField text = new JTextField(15);
        panel.add(new JLabel("Building name:"));
        panel.add(text);
        int optionValue = JOptionPane.showConfirmDialog(null,
                panel, "Remove Building", JOptionPane.INFORMATION_MESSAGE);

        if (optionValue == 0) {
            String name = text.getText();
            JOptionPane.showMessageDialog(null,
                    this.controller.removeBuilding(name),
                    "Remove Building", JOptionPane.INFORMATION_MESSAGE);

        }
    }

    /**
     * Add room enter details pop up.
     */
    public void addRoom() {
        JPanel panel = new JPanel(new GridLayout(4, 1));
        JTextField text1 = new JTextField(15);
        JTextField text2 = new JTextField(15);
        panel.add(new JLabel("Room name/number:"));
        panel.add(text1);
        panel.add(new JLabel("Building name:"));
        panel.add(text2);
        int optionValue = JOptionPane.showConfirmDialog(null,
                panel, "Add Room", JOptionPane.INFORMATION_MESSAGE);

        if (optionValue == 0) {
            String roomName = text1.getText();
            String buildingName = text2.getText();
            JOptionPane.showMessageDialog(null,
                    this.controller.addRoom(roomName, buildingName),
                    "Add Room", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Remove buttons enter details pop up.
     */
    public void removeRoom() {
        JPanel panel = new JPanel(new GridLayout(3, 1));
        JTextField text1 = new JTextField(15);
        JTextField text2 = new JTextField(15);
        panel.add(new JLabel("Room name/number:"));
        panel.add(text1);
        panel.add(new JLabel("Building name:"));
        panel.add(text2);
        int optionValue = JOptionPane.showConfirmDialog(null,
                panel, "Remove Room", JOptionPane.INFORMATION_MESSAGE);

        if (optionValue == 0) {
            String roomName = text1.getText();
            String buildingName = text2.getText();
            JOptionPane.showMessageDialog(null,
                    this.controller.removeRoom(roomName, buildingName),
                    "Remove Room", JOptionPane.INFORMATION_MESSAGE);

        }
    }

    /**
     * Add booking enter details pop up.
     */
    public void addBooking() {
        JPanel panel = new JPanel(new GridLayout(7, 1));
        JTextField text1 = new JTextField(15);
        JTextField text2 = new JTextField(15);
        JTextField text3 = new JTextField(15);
        JTextField text4 = new JTextField(15);
        JTextField text5 = new JTextField(15);
        JTextField text6 = new JTextField(15);
        panel.add(new JLabel("Person email:"));
        panel.add(text1);
        panel.add(new JLabel("Building name:"));
        panel.add(text2);
        panel.add(new JLabel("Room name/number:"));
        panel.add(text3);
        panel.add(new JLabel("Booking date (YYYY-MM-DD):"));
        panel.add(text4);
        panel.add(new JLabel("Booking time (HH:mm):"));
        panel.add(text5);
        panel.add(new JLabel("Booking duration (mins):"));
        panel.add(text6);
        int optionValue = JOptionPane.showConfirmDialog(null,
                panel, "Add Booking", JOptionPane.INFORMATION_MESSAGE);

        if (optionValue == 0) {
            String email = text1.getText();
            String buildingName = text2.getText();
            String roomName = text3.getText();
            String date = text4.getText();
            String bookingStartTime = text5.getText();
            String duration = text6.getText();
            JOptionPane.showMessageDialog(null,
                    this.controller.addBooking(email, buildingName,
                            roomName, date, bookingStartTime, duration),
                    "Add Booking", JOptionPane.INFORMATION_MESSAGE);

        }
    }

    /**
     * Remove building enter details pop up.
     */
    public void removeBooking() {
        JPanel panel = new JPanel(new GridLayout(6, 1));
        JTextField text1 = new JTextField(15);
        JTextField text2 = new JTextField(15);
        JTextField text3 = new JTextField(15);
        JTextField text4 = new JTextField(15);
        JTextField text5 = new JTextField(15);
        panel.add(new JLabel("Person email:"));
        panel.add(text1);
        panel.add(new JLabel("Building name:"));
        panel.add(text2);
        panel.add(new JLabel("Room name/number:"));
        panel.add(text3);
        panel.add(new JLabel("Booking date (YYYY-MM-DD):"));
        panel.add(text4);
        panel.add(new JLabel("Booking time (HH:mm):"));
        panel.add(text5);

        int optionValue = JOptionPane.showConfirmDialog(null,
                panel, "Remove Booking", JOptionPane.INFORMATION_MESSAGE);

        if (optionValue == 0) {
            String email = text1.getText();
            String buildingName = text2.getText();
            String roomName = text3.getText();
            String date = text4.getText();
            String bookingStartTime = text5.getText();
            JOptionPane.showMessageDialog(null,
                    this.controller.removeBooking(email, buildingName,
                            roomName, date, bookingStartTime),
                    "Remove Booking", JOptionPane.INFORMATION_MESSAGE);

        }
    }

    /**
     * Rooms available at time enter details pop up.
     */
    public void roomsAvailableAtTime() {
        JPanel panel = new JPanel(new GridLayout(4, 1));
        JTextField text1 = new JTextField(15);
        JTextField text2 = new JTextField(15);
        panel.add(new JLabel("Time (HH:mm):"));
        panel.add(text1);
        panel.add(new JLabel("Date (YYYY-MM-DD):"));
        panel.add(text2);
        int optionValue = JOptionPane.showConfirmDialog(null,
                panel, "Rooms available at given time", JOptionPane.INFORMATION_MESSAGE);

        if (optionValue == 0) {
            String time = text1.getText();
            String date = text2.getText();
            JOptionPane.showMessageDialog(null,
                    this.controller.roomsAvailableAtGivenTime(time, date),
                    "Rooms available at given time", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Rooms available during a time slot enter details pop up.
     */
    public void roomsAvailableTimeSlot() {
        JPanel panel = new JPanel(new GridLayout(4, 1));
        JTextField text1 = new JTextField(15);
        JTextField text2 = new JTextField(15);
        JTextField text3 = new JTextField(15);
        panel.add(new JLabel("Start time (HH:mm):"));
        panel.add(text1);
        panel.add(new JLabel("End time (HH:mm):"));
        panel.add(text2);
        panel.add(new JLabel("Date (YYYY-MM-DD):"));
        panel.add(text3);
        int optionValue = JOptionPane.showConfirmDialog(null,
                panel, "Rooms available at time slot", JOptionPane.INFORMATION_MESSAGE);

        if (optionValue == 0) {
            String startTime = text1.getText();
            String endTime = text2.getText();
            String date = text3.getText();
            JOptionPane.showMessageDialog(null,
                    this.controller.roomsAvailableAtGivenTimePeriod(startTime, endTime, date),
                    "Rooms available at time slot", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Room schedules enter details pop up.
     */
    public void roomSchedules() {
        JPanel panel = new JPanel(new GridLayout(4, 1));
        JTextField text1 = new JTextField(15);
        JTextField text2 = new JTextField(15);
        panel.add(new JLabel("Room name/number:"));
        panel.add(text1);
        panel.add(new JLabel("Building name:"));
        panel.add(text2);
        int optionValue = JOptionPane.showConfirmDialog(null,
                panel, "Room schedules", JOptionPane.INFORMATION_MESSAGE);

        if (optionValue == 0) {
            String roomName = text1.getText();
            String buildingName = text2.getText();
            JOptionPane.showMessageDialog(null,
                    this.controller.roomSchedule(roomName, buildingName),
                    "Room schedules", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Show Bookings by a person enter details pop up.
     */
    public void showBookingsByPerson() {
        JPanel panel = new JPanel(new GridLayout(4, 1));
        JTextField text = new JTextField(15);
        panel.add(new JLabel("Person email:"));
        panel.add(text);
        int optionValue = JOptionPane.showConfirmDialog(null,
                panel, "Room schedules of person", JOptionPane.INFORMATION_MESSAGE);

        if (optionValue == 0) {
            String email = text.getText();
            JOptionPane.showMessageDialog(null,
                    this.controller.viewBookingsByPerson(email),
                    "Room schedules of person", JOptionPane.INFORMATION_MESSAGE);

        }
    }

    /**
     * Save data enter details pop up.
     */
    public void saveData() {
        JPanel panel = new JPanel(new GridLayout(4, 1));
        JTextField text1 = new JTextField(15);
        JTextField text2 = new JTextField(15);
        panel.add(new JLabel("Directory path (with slash): "));
        panel.add(text1);
        panel.add(new JLabel("File name (file type not required): "));
        panel.add(text2);
        int optionValue = JOptionPane.showConfirmDialog(null,
                panel, "Save data", JOptionPane.INFORMATION_MESSAGE);

        if (optionValue == 0) {
            String dirPath = text1.getText();
            String fileName = text2.getText();
            JOptionPane.showMessageDialog(null,
                    this.controller.saveToFile(dirPath, fileName),
                    "Save data", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Load data enter details pop up.
     */
    public void loadData() {
        JPanel panel = new JPanel(new GridLayout(4, 1));
        JTextField text = new JTextField(15);
        panel.add(new JLabel("File path: "));
        panel.add(text);

        int optionValue = JOptionPane.showConfirmDialog(null,
                panel, "Load data", JOptionPane.INFORMATION_MESSAGE);

        if (optionValue == 0) {
            String filePath = text.getText();
            JOptionPane.showMessageDialog(null,
                    this.controller.loadFromFile(filePath),
                    "Load data", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Method to give a connection to and from controller.
     *
     * @param controller controller of MVC
     */
    public void setController(Controller controller) {
        this.controller = controller;
    }
}
