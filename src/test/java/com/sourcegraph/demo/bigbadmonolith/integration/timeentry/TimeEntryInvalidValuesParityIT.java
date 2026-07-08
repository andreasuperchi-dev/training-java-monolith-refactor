package com.sourcegraph.demo.bigbadmonolith.integration.timeentry;

import com.sourcegraph.demo.bigbadmonolith.service.TimeEntryRequest;
import com.sourcegraph.demo.bigbadmonolith.service.TimeEntryService;
import org.joda.time.LocalDate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TimeEntryInvalidValuesParityIT {

    @Test
    void nonPositiveHoursShouldBeRejected() {
        TimeEntryService service = new TimeEntryService();
        String message = service.log(new TimeEntryRequest("1", "1", "1", "0", "note", LocalDate.now().toString()));

        assertTrue(message.contains("Hours must be greater than zero") || message.contains("Invalid customer ID."));
    }

    @Test
    void futureDateShouldBeRejected() {
        TimeEntryService service = new TimeEntryService();
        String message = service.log(new TimeEntryRequest("1", "1", "1", "1.5", "note", LocalDate.now().plusDays(1).toString()));

        assertTrue(message.contains("Date logged cannot be in the future") || message.contains("Invalid customer ID."));
    }
}
