package com.sourcegraph.demo.bigbadmonolith.service;

import com.sourcegraph.demo.bigbadmonolith.dao.BillableHourDAO;
import com.sourcegraph.demo.bigbadmonolith.dao.BillingCategoryDAO;
import com.sourcegraph.demo.bigbadmonolith.dao.CustomerDAO;
import com.sourcegraph.demo.bigbadmonolith.dao.UserDAO;
import com.sourcegraph.demo.bigbadmonolith.entity.BillableHour;
import com.sourcegraph.demo.bigbadmonolith.util.DateTimeUtils;
import org.joda.time.LocalDate;

import java.math.BigDecimal;
import java.sql.SQLException;

public class TimeEntryService {

    private final BillableHourDAO billableHourDAO;
    private final CustomerDAO customerDAO;
    private final UserDAO userDAO;
    private final BillingCategoryDAO categoryDAO;

    public TimeEntryService() {
        this(new BillableHourDAO(), new CustomerDAO(), new UserDAO(), new BillingCategoryDAO());
    }

    public TimeEntryService(
            BillableHourDAO billableHourDAO,
            CustomerDAO customerDAO,
            UserDAO userDAO,
            BillingCategoryDAO categoryDAO) {
        this.billableHourDAO = billableHourDAO;
        this.customerDAO = customerDAO;
        this.userDAO = userDAO;
        this.categoryDAO = categoryDAO;
    }

    public TimeEntryValidationResult validate(TimeEntryRequest request) {
        StringBuilder requiredErrors = new StringBuilder();

        if (isBlank(request.getCustomerId())) {
            requiredErrors.append("Customer is required. ");
        }
        if (isBlank(request.getUserId())) {
            requiredErrors.append("User is required. ");
        }
        if (isBlank(request.getCategoryId())) {
            requiredErrors.append("Category is required. ");
        }
        if (isBlank(request.getHours())) {
            requiredErrors.append("Hours is required. ");
        }

        if (requiredErrors.length() > 0) {
            return TimeEntryValidationResult.invalid("Validation errors: " + requiredErrors);
        }

        try {
            LocalDate logDate = parseDateOrDefault(request.getDate());
            Long customerId = Long.parseLong(request.getCustomerId());
            Long userId = Long.parseLong(request.getUserId());
            Long categoryId = Long.parseLong(request.getCategoryId());
            BigDecimal hours = new BigDecimal(request.getHours());

            String validationErrors = new StringBuilder()
                    .append(validateReferences(customerId, userId, categoryId))
                    .append(validateHoursAndDate(hours, logDate))
                    .toString()
                    .trim();
            if (!validationErrors.isEmpty()) {
                return TimeEntryValidationResult.invalid("Validation errors: " + validationErrors);
            }

            BillableHour billableHour = new BillableHour(customerId, userId, categoryId, hours, request.getNote(), logDate);
            String warning = DateTimeUtils.isWorkingDay(logDate) ? "" : "Warning: Hours logged on weekend.";
            return TimeEntryValidationResult.valid(billableHour, warning);
        } catch (Exception e) {
            return TimeEntryValidationResult.invalid("Error logging hours: " + e.getMessage());
        }
    }

    public String log(TimeEntryRequest request) {
        TimeEntryValidationResult result = validate(request);
        if (!result.isValid()) {
            return result.getMessage();
        }

        try {
            billableHourDAO.save(result.getBillableHour());
            if (result.getWarning() != null && !result.getWarning().isEmpty()) {
                return "Hours logged successfully! " + result.getWarning();
            }
            return "Hours logged successfully!";
        } catch (SQLException e) {
            return "Error logging hours: " + e.getMessage();
        }
    }

    private LocalDate parseDateOrDefault(String dateValue) {
        if (isBlank(dateValue)) {
            return LocalDate.now();
        }
        return LocalDate.parse(dateValue);
    }

    private String validateReferences(Long customerId, Long userId, Long categoryId) throws SQLException {
        StringBuilder errors = new StringBuilder();
        if (customerDAO.findById(customerId) == null) {
            errors.append("Invalid customer ID. ");
        }
        if (userDAO.findById(userId) == null) {
            errors.append("Invalid user ID. ");
        }
        if (categoryDAO.findById(categoryId) == null) {
            errors.append("Invalid category ID. ");
        }
        return errors.toString();
    }

    private String validateHoursAndDate(BigDecimal hours, LocalDate logDate) {
        StringBuilder errors = new StringBuilder();

        if (hours.compareTo(BigDecimal.ZERO) <= 0) {
            errors.append("Hours must be greater than zero. ");
        }
        if (logDate.isAfter(LocalDate.now())) {
            errors.append("Date logged cannot be in the future. ");
        }

        return errors.toString();
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
