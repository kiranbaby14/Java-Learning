package model.universityAttributes;

public class Person {
    private String name;
    private String email;

    public Person(String name, String email) {
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
        StringBuffer result = new StringBuffer();
        result.append("Name: " + this.name + ", Email:  " + this.email + "\n");
        return result.toString();
    }
}
