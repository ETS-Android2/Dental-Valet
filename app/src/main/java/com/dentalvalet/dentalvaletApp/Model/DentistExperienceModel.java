package com.dentalvalet.dentalvaletApp.Model;

/**
 * Created by Awais Mahmood on 04-Dec-15.
 */
public class DentistExperienceModel {
    String id;
    String title;
    String duration;
    String isActive;

    public DentistExperienceModel(String id, String title, String duration, String isActive) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.isActive = isActive;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }
}
