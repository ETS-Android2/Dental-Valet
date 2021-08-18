package com.dentalvalet.dentalvaletApp.Model;

import java.util.ArrayList;

/**
 * Created by Awais Mahmood on 27-Nov-15.
 */
public class User {

    String id;
    DentistInfo hiredDentist;
    String name;
    String email;
    String phone;
    String address;
    String image;
    String password;
    String hiredDentistStatus;
    AppointentModel selectedAppointment;
    private ArrayList<WishlistModel> patientWishlist;
    private ArrayList<TreatmentPlanModel> patientTreatmentPlan;


    public User(String id, String name, String email, String phone, String address, String image, String password,String hiredDentistStatus) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.image = image;
        this.password = password;
         this.hiredDentistStatus= hiredDentistStatus;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<WishlistModel> getPatientWishlist() {
        return patientWishlist;
    }

    public ArrayList<TreatmentPlanModel> getPatientTreatmentPlan() {
        return patientTreatmentPlan;
    }

    public void setPatientTreatmentPlan(ArrayList<TreatmentPlanModel> patientTreatmentPlan) {
        this.patientTreatmentPlan = patientTreatmentPlan;
    }

    public String getHiredDentistStatus() {
        return hiredDentistStatus;
    }

    public void setHiredDentistStatus(String hiredDentistStatus) {
        this.hiredDentistStatus = hiredDentistStatus;
    }

    public DentistInfo getHiredDentist() {
        return hiredDentist;
    }

    public void setHiredDentist(DentistInfo hiredDentist) {
        this.hiredDentist = hiredDentist;
    }

    public void setPatientWishlist(ArrayList<WishlistModel> patientWishlist) {
        this.patientWishlist = patientWishlist;
    }

    public AppointentModel getSelectedAppointment() {
        return selectedAppointment;
    }

    public void setSelectedAppointment(AppointentModel selectedAppointment) {
        this.selectedAppointment = selectedAppointment;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DentistInfo getSelectedDentist() {
        return hiredDentist;
    }

    public void setSelectedDentist(DentistInfo hiredDentist) {
        this.hiredDentist = hiredDentist;
    }
}

