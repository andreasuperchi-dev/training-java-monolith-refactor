package com.sourcegraph.demo.bigbadmonolith.integration.timeentry;

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
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TimeEntryPersistenceParityIT {

    @Test
    void validEntryShouldPersistAndReturnLegacyEquivalentSuccessMessage() {
        CapturingBillableHourDAO billableHourDAO = new CapturingBillableHourDAO();
        TimeEntryService service = new TimeEntryService(
                billableHourDAO,
                new ExistingCustomerDAO(),
                new ExistingUserDAO(),
                new ExistingBillingCategoryDAO());

        String message = service.log(new TimeEntryRequest("1", "1", "1", "2.0", "note", ""));

        assertEquals("Hours logged successfully!", message);
        assertEquals(1L, billableHourDAO.savedCount);
    }

    private static final class CapturingBillableHourDAO extends BillableHourDAO {
        private long savedCount;

        @Override
        public BillableHour save(BillableHour billableHour) {
            savedCount++;
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
