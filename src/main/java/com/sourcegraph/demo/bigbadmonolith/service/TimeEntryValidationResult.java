package com.sourcegraph.demo.bigbadmonolith.service;

import com.sourcegraph.demo.bigbadmonolith.entity.BillableHour;

public class TimeEntryValidationResult {
    private final boolean valid;
    private final String message;
    private final String warning;
    private final BillableHour billableHour;

    private TimeEntryValidationResult(boolean valid, String message, String warning, BillableHour billableHour) {
        this.valid = valid;
        this.message = message;
        this.warning = warning;
        this.billableHour = billableHour;
    }

    public static TimeEntryValidationResult invalid(String message) {
        return new TimeEntryValidationResult(false, message, null, null);
    }

    public static TimeEntryValidationResult valid(BillableHour billableHour, String warning) {
        return new TimeEntryValidationResult(true, "", warning, billableHour);
    }

    public boolean isValid() {
        return valid;
    }

    public String getMessage() {
        return message;
    }

    public String getWarning() {
        return warning;
    }

    public BillableHour getBillableHour() {
        return billableHour;
    }
}
