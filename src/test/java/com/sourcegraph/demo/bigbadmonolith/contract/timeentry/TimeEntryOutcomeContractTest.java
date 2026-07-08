package com.sourcegraph.demo.bigbadmonolith.contract.timeentry;

import com.sourcegraph.demo.bigbadmonolith.service.TimeEntryRequest;
import com.sourcegraph.demo.bigbadmonolith.service.TimeEntryService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TimeEntryOutcomeContractTest {

    @Test
    void invalidRequestShouldReturnErrorOrValidationOutcome() {
        TimeEntryService service = new TimeEntryService();
        String message = service.log(new TimeEntryRequest("", "1", "1", "1", "note", ""));

        assertTrue(message.startsWith("Validation errors:") || message.startsWith("Error logging hours:"));
    }
}
