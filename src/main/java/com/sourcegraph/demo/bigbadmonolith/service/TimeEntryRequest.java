package com.sourcegraph.demo.bigbadmonolith.service;

public class TimeEntryRequest {
    private final String customerId;
    private final String userId;
    private final String categoryId;
    private final String hours;
    private final String note;
    private final String date;

    public TimeEntryRequest(
            String customerId,
            String userId,
            String categoryId,
            String hours,
            String note,
            String date) {
        this.customerId = customerId;
        this.userId = userId;
        this.categoryId = categoryId;
        this.hours = hours;
        this.note = note;
        this.date = date;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getUserId() {
        return userId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getHours() {
        return hours;
    }

    public String getNote() {
        return note;
    }

    public String getDate() {
        return date;
    }
}
