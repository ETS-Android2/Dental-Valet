package com.dentalvalet.dentalvaletApp.Model;

/**
 * Created by Awais Mahmood on 09-Dec-15.
 */
public class DentistStaffModel {

    String id;
    String dentistId;
    String name;
    String position;
    String description;
    String gender;
    String image;

    public DentistStaffModel(String id, String dentistId, String name, String position, String description, String gender, String image) {
        this.id = id;
        this.dentistId = dentistId;
        this.name = name;
        this.position = position;
        this.description = description;
        this.gender = gender;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDentistId() {
        return dentistId;
    }

    public void setDentistId(String dentistId) {
        this.dentistId = dentistId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
