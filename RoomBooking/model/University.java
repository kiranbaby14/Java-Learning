package model;

import model.universityAttributes.Building;
import model.universityAttributes.Person;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Main model of the MVC (University).
 * It implements serializable so that data can be saved and loaded.
 */
public class University implements Serializable {

    private final ArrayList<Building> buildings; // building in the university
    private final ArrayList<Person> people ; // people in the university

    /**
     * Constructor
     */
    public University() {
        this.buildings = new ArrayList<>();
        this.people = new ArrayList<>();
    }

    /**
     * get the arraylist of buildings.
     * @return building list
     */
    public ArrayList<Building> getBuildings() {
        return this.buildings;
    }

    /**
     * Add a building to the buildings list.
     * @param building building to be added
     */
    public void addBuilding(Building building) {
        this.buildings.add(building);
    }

    /**
     * remove a building from the buildings list.
     * @param building building to be removed
     */
    public void removeBuilding(Building building){
        this.buildings.remove(building);
    }

    /**
     * Method to get the people list
     * @return people list
     */
    public ArrayList<Person> getPeople() {
        return this.people;
    }

    /**
     * Method to add the person to the university.
     * @param person person to be added
     */
    public void addPerson(Person person) {
        this.people.add(person);
    }

    /**
     * Method to remove a person from university.
     * @param person person to be removed
     */
    public void removePerson(Person person) {
        this.people.remove(person);
    }

    /**
     * overriding string method.
     * @return string
     */
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Buildings...\n");
        for (Building building : buildings) {
            result.append(" ").append(building);
        }
        result.append("\nPeople...\n");
        for (Person person: people) {
            result.append(" ").append(person);
        }
        return result.toString();
    }
}
