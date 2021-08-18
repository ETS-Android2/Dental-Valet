package com.dentalvalet.dentalvaletApp.Model;

/**
 * Created by Awais Mahmood on 01-Dec-15.
 */
public class WishlistModel {

    String serviceId;
    String dentistId;
    String title;
    String detistTime;
    String hygienistTime;
    String cost;
    String image;

    public WishlistModel(String serviceId, String dentistId, String title, String detistTime, String hygienistTime, String cost, String image) {
        this.serviceId = serviceId;
        this.dentistId = dentistId;
        this.title = title;
        this.detistTime = detistTime;
        this.hygienistTime = hygienistTime;
        this.cost = cost;
        this.image = image;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
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

    public String getDetistTime() {
        return detistTime;
    }

    public void setDetistTime(String detistTime) {
        this.detistTime = detistTime;
    }

    public String getHygienistTime() {
        return hygienistTime;
    }

    public void setHygienistTime(String hygienistTime) {
        this.hygienistTime = hygienistTime;
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
}
