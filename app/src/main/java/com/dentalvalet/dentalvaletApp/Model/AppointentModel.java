package com.dentalvalet.dentalvaletApp.Model;

/**
 * Created by Awais Mahmood on 01-Dec-15.
 */
public class AppointentModel {
    String dentistId;
    String appointmentType;
    String appointmentId;
    String startDateTime;
    String endDateTime;
    String time;
    String date;
    String title;

    public AppointentModel() {
    }

    public AppointentModel(String title,
                           String dentistId, String appointmentType,
                           String appointmentId, String startDateTime,
                           String endDateTime, String time, String date) {
        this.dentistId = dentistId;
        this.appointmentType = appointmentType;
        this.appointmentId = appointmentId;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.time = time;
        this.date = date;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getDentistId() {
        return dentistId;
    }

    public void setDentistId(String dentistId) {
        this.dentistId = dentistId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
