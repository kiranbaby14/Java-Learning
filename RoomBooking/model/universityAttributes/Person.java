package model.universityAttributes;

import java.io.Serializable;

/**
 * Person Model
 * It has all the details about the person in the university.
 */
public class Person implements Serializable {
    private final String name;
    private final String email;

    public Person(final String name, final String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public String toString() {
        return "Name: " + this.name + ", Email:  " + this.email + "\n";
    }
}
