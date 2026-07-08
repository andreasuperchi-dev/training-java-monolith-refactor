package com.sourcegraph.demo.bigbadmonolith.integration.timeentry;

import com.sourcegraph.demo.bigbadmonolith.service.TimeEntryRequest;
import com.sourcegraph.demo.bigbadmonolith.service.TimeEntryService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TimeEntryDefaultDateParityIT {

    @Test
    void missingDateShouldNotYieldDateParsingError() {
        TimeEntryService service = new TimeEntryService();
        String message = service.log(new TimeEntryRequest("1", "1", "1", "1.0", "note", ""));

        assertTrue(!message.contains("Invalid format") && !message.contains("malformed"));
    }
}
