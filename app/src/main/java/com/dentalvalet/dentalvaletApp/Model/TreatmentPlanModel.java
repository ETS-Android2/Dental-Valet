package com.dentalvalet.dentalvaletApp.Model;

/**
 * Created by Awais Mahmood on 02-Dec-15.
 */
public class TreatmentPlanModel {

    String planId;
    String planTitle;
    String description;
    String cost;
    String status;
    String serviceTitle;
    String serviceDescription;
    String requiredHours;
    String serviceCost;
    String mandatory;
    String serviceDate;
    String hygineistTime;
    String serviceId;

    public TreatmentPlanModel(String planId, String planTitle, String description, String cost, String status, String serviceTitle, String serviceDescription, String requiredHours, String serviceCost, String mandatory, String serviceDate, String hygineistTime, String serviceId) {
        this.planId = planId;
        this.planTitle = planTitle;
        this.description = description;
        this.cost = cost;
        this.status = status;
        this.serviceTitle = serviceTitle;
        this.serviceDescription = serviceDescription;
        this.requiredHours = requiredHours;
        this.serviceCost = serviceCost;
        this.mandatory = mandatory;
        this.serviceDate = serviceDate;
        this.hygineistTime = hygineistTime;
        this.serviceId = serviceId;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getPlanTitle() {
        return planTitle;
    }

    public void setPlanTitle(String planTitle) {
        this.planTitle = planTitle;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getServiceTitle() {
        return serviceTitle;
    }

    public void setServiceTitle(String serviceTitle) {
        this.serviceTitle = serviceTitle;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    public String getRequiredHours() {
        return requiredHours;
    }

    public void setRequiredHours(String requiredHours) {
        this.requiredHours = requiredHours;
    }

    public String getServiceCost() {
        return serviceCost;
    }

    public void setServiceCost(String serviceCost) {
        this.serviceCost = serviceCost;
    }

    public String getMandatory() {
        return mandatory;
    }

    public void setMandatory(String mandatory) {
        this.mandatory = mandatory;
    }

    public String getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(String serviceDate) {
        this.serviceDate = serviceDate;
    }

    public String getHygineistTime() {
        return hygineistTime;
    }

    public void setHygineistTime(String hygineistTime) {
        this.hygineistTime = hygineistTime;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }
}
