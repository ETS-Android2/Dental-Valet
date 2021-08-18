package com.dentalvalet.dentalvaletApp.Model;

/**
 * Created by Awais Mahmood on 01-Dec-15.
 */
public class ServicesModel {

    String id;
    String dentistId;
    String title;
    String description;
    String requiredHours;
    String cost;
    String image;
    String mandatory;
    String hygineistTime;
    String document;
    String vedio;

    public ServicesModel(String id, String dentistId, String title, String description, String requiredHours, String cost, String image, String mandatory, String hygineistTime, String document, String vedio) {
        this.id = id;
        this.dentistId = dentistId;
        this.title = title;
        this.description = description;
        this.requiredHours = requiredHours;
        this.cost = cost;
        this.image = image;
        this.mandatory = mandatory;
        this.hygineistTime = hygineistTime;
        this.document = document;
        this.vedio = vedio;
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

    public String getRequiredHours() {
        return requiredHours;
    }

    public void setRequiredHours(String requiredHours) {
        this.requiredHours = requiredHours;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMandatory() {
        return mandatory;
    }

    public void setMandatory(String mandatory) {
        this.mandatory = mandatory;
    }

    public String getHygineistTime() {
        return hygineistTime;
    }

    public void setHygineistTime(String hygineistTime) {
        this.hygineistTime = hygineistTime;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getVedio() {
        return vedio;
    }

    public void setVedio(String vedio) {
        this.vedio = vedio;
    }
}
