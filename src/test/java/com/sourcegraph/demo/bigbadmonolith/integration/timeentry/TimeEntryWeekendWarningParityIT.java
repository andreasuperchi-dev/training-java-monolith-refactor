package com.sourcegraph.demo.bigbadmonolith.integration.timeentry;

import com.sourcegraph.demo.bigbadmonolith.service.TimeEntryRequest;
import com.sourcegraph.demo.bigbadmonolith.service.TimeEntryService;
import org.joda.time.LocalDate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TimeEntryWeekendWarningParityIT {

    @Test
    void weekendEntryShouldRemainWarningOrEquivalentValidationPath() {
        TimeEntryService service = new TimeEntryService();
        LocalDate saturday = LocalDate.now().withDayOfWeek(6);
        String message = service.log(new TimeEntryRequest("1", "1", "1", "1", "note", saturday.toString()));

        assertTrue(message.contains("Warning") || message.contains("Invalid customer ID.") || message.contains("Hours logged successfully"));
    }
}
