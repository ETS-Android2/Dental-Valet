package com.dentalvalet.dentalvaletApp.Model;

/**
 * Created by Awais Mahmood on 03-Dec-15.
 */
public class TestimonialModel {
    String id;
    String patientId;
    String dentistId;
    String title;
    String description;
    String createdAt;
    String status;
    String approved;
    String vedioFile;


    public TestimonialModel(String id, String patientId, String dentistId, String title, String description, String createdAt, String status, String approved, String vedioFile) {
        this.id = id;
        this.patientId = patientId;
        this.dentistId = dentistId;
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
        this.status = status;
        this.approved = approved;
        this.vedioFile = vedioFile;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getApproved() {
        return approved;
    }

    public void setApproved(String approved) {
        this.approved = approved;
    }

    public String getVedioFile() {
        return vedioFile;
    }

    public void setVedioFile(String vedioFile) {
        this.vedioFile = vedioFile;
    }
}
