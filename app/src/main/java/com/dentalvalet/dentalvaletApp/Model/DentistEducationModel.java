package com.dentalvalet.dentalvaletApp.Model;

/**
 * Created by Awais Mahmood on 04-Dec-15.
 */
public class DentistEducationModel {

    String id;
    String title;
    String startDate;
    String endDate;
    String university;

    public DentistEducationModel(String id, String title, String startDate, String endDate, String university) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.university = university;
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }
}
