package com.sourcegraph.demo.bigbadmonolith.contract.timeentry;

import com.sourcegraph.demo.bigbadmonolith.dao.BillableHourDAO;
import com.sourcegraph.demo.bigbadmonolith.dao.BillingCategoryDAO;
import com.sourcegraph.demo.bigbadmonolith.dao.CustomerDAO;
import com.sourcegraph.demo.bigbadmonolith.dao.UserDAO;
import com.sourcegraph.demo.bigbadmonolith.entity.BillableHour;
import com.sourcegraph.demo.bigbadmonolith.entity.BillingCategory;
import com.sourcegraph.demo.bigbadmonolith.entity.Customer;
import com.sourcegraph.demo.bigbadmonolith.entity.User;
import com.sourcegraph.demo.bigbadmonolith.service.TimeEntryRequest;
import com.sourcegraph.demo.bigbadmonolith.service.TimeEntryService;
import org.joda.time.LocalDate;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TimeEntryDateAndWarningContractTest {

    @Test
    void omittedDateShouldStillReturnLegacySuccessPrefix() {
        TimeEntryService service = new TimeEntryService(
                new NoopBillableHourDAO(),
                new ExistingCustomerDAO(),
                new ExistingUserDAO(),
                new ExistingBillingCategoryDAO());

        String message = service.log(new TimeEntryRequest("1", "1", "1", "1.0", "note", ""));

        assertTrue(message.startsWith("Hours logged successfully!"));
    }

    @Test
    void weekendDateShouldReturnWarningAsPartOfSuccessOutcome() {
        TimeEntryService service = new TimeEntryService(
                new NoopBillableHourDAO(),
                new ExistingCustomerDAO(),
                new ExistingUserDAO(),
                new ExistingBillingCategoryDAO());

        LocalDate saturday = LocalDate.now();
        while (saturday.getDayOfWeek() != 6) {
            saturday = saturday.plusDays(1);
        }

        String message = service.log(new TimeEntryRequest("1", "1", "1", "1.0", "note", saturday.toString()));

        assertTrue(message.startsWith("Hours logged successfully!"));
        assertTrue(message.contains("Warning: Hours logged on weekend."));
    }

    private static final class NoopBillableHourDAO extends BillableHourDAO {
        @Override
        public BillableHour save(BillableHour billableHour) {
            return billableHour;
        }
    }

    private static final class ExistingCustomerDAO extends CustomerDAO {
        @Override
        public Customer findById(Long id) {
            return new Customer(id, "Customer", "c@example.com", "Addr", null);
        }
    }

    private static final class ExistingUserDAO extends UserDAO {
        @Override
        public User findById(Long id) {
            return new User(id, "u@example.com", "User");
        }
    }

    private static final class ExistingBillingCategoryDAO extends BillingCategoryDAO {
        @Override
        public BillingCategory findById(Long id) {
            return new BillingCategory(id, "Dev", "Development", BigDecimal.ONE);
        }
    }
}
