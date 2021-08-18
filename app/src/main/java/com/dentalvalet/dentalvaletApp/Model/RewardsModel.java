package com.dentalvalet.dentalvaletApp.Model;

/**
 * Created by Awais Mahmood on 14-Dec-15.
 */
public class RewardsModel {
    String id;
    String dentistId;
    String title;
    String description;
    String image;
    String scoreRequired;


    public RewardsModel(String id, String dentistId, String title, String description, String image, String scoreRequired) {
        this.id = id;
        this.dentistId = dentistId;
        this.title = title;
        this.description = description;
        this.image = image;
        this.scoreRequired = scoreRequired;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getScoreRequired() {
        return scoreRequired;
    }

    public void setScoreRequired(String scoreRequired) {
        this.scoreRequired = scoreRequired;
    }
}
