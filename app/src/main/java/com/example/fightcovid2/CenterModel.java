package com.example.fightcovid2;

public class CenterModel {
    private String centerName;
    private String centerAddress;
    private String centerFromTime;
    private String centerToTime;
    private String feeType;
    private int ageLimit;
    private String vaccineName;
    private int vaccineAvailability;

    public CenterModel(String centerName, String centerAddress, String centerFromTime, String centerToTime, String feeType, int ageLimit, String vaccineName, int vaccineAvailability) {
        this.centerName = centerName;
        this.centerAddress = centerAddress;
        this.centerFromTime = centerFromTime;
        this.centerToTime = centerToTime;
        this.feeType = feeType;
        this.ageLimit = ageLimit;
        this.vaccineName = vaccineName;
        this.vaccineAvailability = vaccineAvailability;
    }

    public CenterModel() {

    }

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public String getCenterAddress() {
        return centerAddress;
    }

    public void setCenterAddress(String centerAddress) {
        this.centerAddress = centerAddress;
    }

    public String getCenterFromTime() {
        return centerFromTime;
    }

    public void setCenterFromTime(String centerFromTime) {
        this.centerFromTime = centerFromTime;
    }

    public String getCenterToTime() {
        return centerToTime;
    }

    public void setCenterToTime(String centerToTime) {
        this.centerToTime = centerToTime;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public int getAgeLimit() {
        return ageLimit;
    }

    public void setAgeLimit(int ageLimit) {
        this.ageLimit = ageLimit;
    }

    public String getVaccineName() {
        return vaccineName;
    }

    public void setVaccineName(String vaccineName) {
        this.vaccineName = vaccineName;
    }

    public int getVaccineAvailability() {
        return vaccineAvailability;
    }

    public void setVaccineAvailability(int vaccineAvailability) {
        this.vaccineAvailability = vaccineAvailability;
    }
}
