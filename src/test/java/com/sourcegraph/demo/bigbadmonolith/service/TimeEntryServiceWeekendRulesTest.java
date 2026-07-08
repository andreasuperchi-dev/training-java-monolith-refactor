package com.sourcegraph.demo.bigbadmonolith.service;

import com.sourcegraph.demo.bigbadmonolith.dao.BillableHourDAO;
import com.sourcegraph.demo.bigbadmonolith.dao.BillingCategoryDAO;
import com.sourcegraph.demo.bigbadmonolith.dao.CustomerDAO;
import com.sourcegraph.demo.bigbadmonolith.dao.UserDAO;
import com.sourcegraph.demo.bigbadmonolith.entity.BillingCategory;
import com.sourcegraph.demo.bigbadmonolith.entity.Customer;
import com.sourcegraph.demo.bigbadmonolith.entity.User;
import org.joda.time.LocalDate;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TimeEntryServiceWeekendRulesTest {

    @Test
    void weekendEntryShouldRemainValidAndEmitWarning() {
        TimeEntryService service = new TimeEntryService(
                new BillableHourDAO(),
                new ExistingCustomerDAO(),
                new ExistingUserDAO(),
                new ExistingBillingCategoryDAO());

        LocalDate saturday = LocalDate.now();
        while (saturday.getDayOfWeek() != 6) {
            saturday = saturday.plusDays(1);
        }

        TimeEntryValidationResult result = service.validate(new TimeEntryRequest(
                "1", "1", "1", "1.5", "weekend", saturday.toString()));

        assertTrue(result.isValid());
        assertTrue(result.getWarning().contains("Warning: Hours logged on weekend."));
    }

    @Test
    void weekdayEntryShouldNotEmitWeekendWarning() {
        TimeEntryService service = new TimeEntryService(
                new BillableHourDAO(),
                new ExistingCustomerDAO(),
                new ExistingUserDAO(),
                new ExistingBillingCategoryDAO());

        LocalDate weekday = LocalDate.now();
        while (weekday.getDayOfWeek() == 6 || weekday.getDayOfWeek() == 7) {
            weekday = weekday.plusDays(1);
        }

        TimeEntryValidationResult result = service.validate(new TimeEntryRequest(
                "1", "1", "1", "1.5", "weekday", weekday.toString()));

        assertTrue(result.isValid());
        assertFalse(result.getWarning().contains("weekend"));
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
