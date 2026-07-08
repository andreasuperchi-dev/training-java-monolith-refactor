package com.sourcegraph.demo.bigbadmonolith.contract.timeentry;

import com.sourcegraph.demo.bigbadmonolith.service.TimeEntryRequest;
import com.sourcegraph.demo.bigbadmonolith.service.TimeEntryValidationResult;
import com.sourcegraph.demo.bigbadmonolith.service.TimeEntryService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TimeEntryInputContractTest {

    @Test
    void contractShouldRequireCoreFields() {
        TimeEntryService service = new TimeEntryService();
        TimeEntryValidationResult result = service.validate(new TimeEntryRequest("", "", "", "", "optional", ""));

        assertFalse(result.isValid());
        assertTrue(result.getMessage().startsWith("Validation errors:"));
    }
}
