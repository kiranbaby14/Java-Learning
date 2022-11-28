package model;

import model.universityAttributes.Building;
import model.universityAttributes.Person;

import java.util.ArrayList;

public class University {

    private ArrayList<Building> buildings;
    private ArrayList<Person> people;

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
}
