package com.dentalvalet.dentalvaletApp.Model;

/**
 * Created by Awais Mahmood on 25-Nov-15.
 */
public class Specialities {
    long id;
    String name;

    public Specialities(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
