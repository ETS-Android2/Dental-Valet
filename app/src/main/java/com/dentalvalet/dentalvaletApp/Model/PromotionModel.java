package com.dentalvalet.dentalvaletApp.Model;

/**
 * Created by Awais Mahmood on 03-Dec-15.
 */
public class PromotionModel {

    String id;
    String dentistId;
    String title;
    String description;
    String cost;
    String validityDate;
    String image;
    String rating;

    public PromotionModel(String id, String dentistId, String title, String description, String cost, String validityDate, String image, String rating) {
        this.id = id;
        this.dentistId = dentistId;
        this.title = title;
        this.description = description;
        this.cost = cost;
        this.validityDate = validityDate;
        this.image = image;
        this.rating = rating;
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

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getValidityDate() {
        return validityDate;
    }

    public void setValidityDate(String validityDate) {
        this.validityDate = validityDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
