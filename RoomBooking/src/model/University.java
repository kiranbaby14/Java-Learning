package model;

import model.universityAttributes.Building;
import model.universityAttributes.Person;

import java.util.ArrayList;

public class University {

    private ArrayList<Building> buildings = new ArrayList<>();
    private ArrayList<Person> people = new ArrayList<>();

    public University(ArrayList<Building> buildings, ArrayList<Person> people) {
        this.buildings = buildings;
        this.people = people;
    }

    // get the arraylist of buildings
    public ArrayList<Building> getBuildings() {
        return this.buildings;
    }

    // add a building to the buildings list
    public void addBuilding(Building building) {
        this.buildings.add(building);
    }

    //remove a building from the buildings list
    public void removeBuilding(Building building){
        this.buildings.remove(building);
    }

    public ArrayList<Person> getPeople() {
        return this.people;
    }

    public void addPerson(Person person) {
        this.people.add(person);
    }

    public void removePerson(Person person) {
        this.people.remove(person);
    }

    public String toString() {
        StringBuffer result = new StringBuffer();
        result.append("Buildings...\n");
        for (Building building : buildings) {
            result.append(" " + building);
        }
        result.append("\nPeople...\n");
        for (Person person: people) {
            result.append(" " + person);
        }
        return result.toString();
    }
}
