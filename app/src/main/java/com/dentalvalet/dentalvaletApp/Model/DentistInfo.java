package com.dentalvalet.dentalvaletApp.Model;

import com.dentalvalet.dentalvaletApp.Activities.DentistStaff;

import java.util.ArrayList;

/**
 * Created by Awais Mahmood on 22-Nov-15.
 */
public class DentistInfo {
    long id;
    String name;
    String email;
    String mobile;
    String expertise;
    String officeAddress;
    String image;
    String zipcode;
    String state;
    String subscriptionStatus;


    String city;
    String country;
    String education;
    String practiseInfo;
    String staff;
    String practiceName;
    String uniqueInfo;
    String speciality;
    String insuranceAccepted;
    private ArrayList<PromotionModel> dentistPromotions;
    private ArrayList<TestimonialModel> dentistTestimonial;
    private ArrayList<DentistEducationModel> dentistEducation;
    private ArrayList<DentistExperienceModel> dentistExperience;
    private ArrayList<DentistStaffModel> dentistStaff;
    private ArrayList<RewardsModel> dentistRewards;
    private ArrayList<AppointentModel> availableAppointments;
    private ArrayList<ServicesModel> availableServices;



    public DentistInfo() {
    }

    public DentistInfo(long id, String name, String email, String mobile, String expertise, String officeAddress, String image, String zipcode, String state, String subscriptionStatus, String city, String country, String education, String practiseInfo, String staff, String practiceName, String uniqueInfo, String speciality, String insuranceAccepted) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.expertise = expertise;
        this.officeAddress = officeAddress;
        this.image = image;
        this.zipcode = zipcode;
        this.state = state;
        this.subscriptionStatus = subscriptionStatus;
        this.city = city;
        this.country = country;
        this.education = education;
        this.practiseInfo = practiseInfo;
        this.staff = staff;
        this.practiceName = practiceName;
        this.uniqueInfo = uniqueInfo;
        this.speciality = speciality;
        this.insuranceAccepted = insuranceAccepted;
    }

    public ArrayList<RewardsModel> getDentistRewards() {
        return dentistRewards;
    }

    public void setDentistRewards(ArrayList<RewardsModel> dentistRewards) {
        this.dentistRewards = dentistRewards;
    }

    public ArrayList<AppointentModel> getAvailableAppointments() {
        return availableAppointments;
    }

    public ArrayList<ServicesModel> getAvailableServices() {
        return availableServices;
    }

    public void setAvailableServices(ArrayList<ServicesModel> availableServices) {
        this.availableServices = availableServices;
    }

    public void setAvailableAppointments(ArrayList<AppointentModel> availableAppointments) {
        this.availableAppointments = availableAppointments;
    }

    public ArrayList<TestimonialModel> getDentistTestimonial() {

        return dentistTestimonial;
    }

    public ArrayList<DentistStaffModel> getDentistStaff() {
        return dentistStaff;
    }

    public void setDentistStaff(ArrayList<DentistStaffModel> dentistStaff) {
        this.dentistStaff = dentistStaff;
    }

    public ArrayList<DentistExperienceModel> getDentistExperience() {
        return dentistExperience;
    }

    public void setDentistExperience(ArrayList<DentistExperienceModel> dentistExperience) {
        this.dentistExperience = dentistExperience;
    }

    public void setDentistTestimonial(ArrayList<TestimonialModel> dentistTestimonial) {
        this.dentistTestimonial = dentistTestimonial;
    }

    public ArrayList<PromotionModel> getDentistPromotions() {
        return dentistPromotions;
    }

    public void setDentistPromotions(ArrayList<PromotionModel> dentistPromotions) {
        this.dentistPromotions = dentistPromotions;
    }

    public ArrayList<DentistEducationModel> getDentistEducation() {
        return dentistEducation;
    }

    public void setDentistEducation(ArrayList<DentistEducationModel> dentistEducation) {
        this.dentistEducation = dentistEducation;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getPractiseInfo() {
        return practiseInfo;
    }

    public void setPractiseInfo(String practiseInfo) {
        this.practiseInfo = practiseInfo;
    }

    public String getStaff() {
        return staff;
    }

    public void setStaff(String staff) {
        this.staff = staff;
    }

    public String getPracticeName() {
        return practiceName;
    }

    public void setPracticeName(String practiceName) {
        this.practiceName = practiceName;
    }

    public String getUniqueInfo() {
        return uniqueInfo;
    }

    public void setUniqueInfo(String uniqueInfo) {
        this.uniqueInfo = uniqueInfo;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getInsuranceAccepted() {
        return insuranceAccepted;
    }

    public void setInsuranceAccepted(String insuranceAccepted) {
        this.insuranceAccepted = insuranceAccepted;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

    public String getOfficeAddress() {
        return officeAddress;
    }

    public void setOfficeAddress(String officeAddress) {
        this.officeAddress = officeAddress;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSubscriptionStatus() {
        return subscriptionStatus;
    }

    public void setSubscriptionStatus(String subscriptionStatus) {
        this.subscriptionStatus = subscriptionStatus;
    }

    @Override
    public String toString() {
        return "ID "+id+" Email "+email+" image Name "+image + " zipcode "+ zipcode + " phone "+mobile;
    }
}
