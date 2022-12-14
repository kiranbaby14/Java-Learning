package main;

import controller.Controller; //Controller
import model.University; // Universoty Model
import view.guiview.CliGUI; // CLI View
import view.guiview.SwingGUI; // Swing View

/**
 * Main function of MVC implemented Room Booking system.
 *
 * @author: Student ID: 220015821
 */
public class MVCMain {
    /**
     * main method which instantiates views, models, and controller.
     * @param args CLI arguments
     */
    public static void main(String[] args) {
        //Model
        University university = new University();
        //views
        SwingGUI swingGUI = new SwingGUI();
        CliGUI cliGUI = new CliGUI();

        //controller
        new Controller(university, cliGUI, swingGUI);

        swingGUI.start(); // call start method inside SwingGUI
        cliGUI.start(); // call start method inside CliGUI
    }
}
