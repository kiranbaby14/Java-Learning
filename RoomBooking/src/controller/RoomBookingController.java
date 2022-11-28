package controller;

import model.University;
import view.guiview.CliGUI;
import view.guiview.SwingGUI;

public class RoomBookingController {

    private University university;
    private CliGUI cliGUI;
    private SwingGUI swingGUI;

    public RoomBookingController(University university, CliGUI cliGUI, SwingGUI swingGUI) {
        this.university = university;
        this.cliGUI = cliGUI;
        this.swingGUI = swingGUI;
    }
}
