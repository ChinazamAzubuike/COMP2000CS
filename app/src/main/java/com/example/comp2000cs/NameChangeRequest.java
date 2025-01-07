package com.example.comp2000cs;

public class NameChangeRequest {
    private final int id;
    private final String email;
    private final String newFirstName;
    private final String newLastName;

    public NameChangeRequest(int id, String email, String newFirstName, String newLastName) {
        this.id = id;
        this.email = email;
        this.newFirstName = newFirstName;
        this.newLastName = newLastName;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getNewFirstName() {
        return newFirstName;
    }

    public String getNewLastName() {
        return newLastName;
    }
}
