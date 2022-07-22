package com.example.whatsappme;

public class HistoryModel {
    String mobileNumber, time;

    public HistoryModel(String mobileNumber, String time) {
        this.mobileNumber = mobileNumber;
        this.time = time;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
