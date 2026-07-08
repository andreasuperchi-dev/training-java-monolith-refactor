package com.sourcegraph.demo.bigbadmonolith.service;

import com.sourcegraph.demo.bigbadmonolith.dao.LibertyConnectionManager;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ReportingService {

    public CustomerBillReport generateCustomerBillReport(long customerId) throws SQLException {
        CustomerBillReport report = new CustomerBillReport();

        try (Connection conn = LibertyConnectionManager.getConnection()) {
            try (PreparedStatement customerStmt = conn.prepareStatement("SELECT name, email FROM customers WHERE id = ?")) {
                customerStmt.setLong(1, customerId);
                try (ResultSet rs = customerStmt.executeQuery()) {
                    if (rs.next()) {
                        report.customerName = rs.getString("name");
                        report.customerEmail = rs.getString("email");
                    }
                }
            }

            String sql = "SELECT bh.date_logged, u.name as user_name, bc.name as category_name, " +
                    "bh.hours, bc.hourly_rate, bh.hours * bc.hourly_rate as line_total, bh.note " +
                    "FROM billable_hours bh " +
                    "JOIN users u ON bh.user_id = u.id " +
                    "JOIN billing_categories bc ON bh.category_id = bc.id " +
                    "WHERE bh.customer_id = ? " +
                    "ORDER BY bh.date_logged DESC";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setLong(1, customerId);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        CustomerBillLine line = new CustomerBillLine();
                        line.dateLogged = rs.getDate("date_logged");
                        line.userName = rs.getString("user_name");
                        line.categoryName = rs.getString("category_name");
                        line.hours = defaultDecimal(rs.getBigDecimal("hours"));
                        line.hourlyRate = defaultDecimal(rs.getBigDecimal("hourly_rate"));
                        line.lineTotal = defaultDecimal(rs.getBigDecimal("line_total"));
                        line.note = rs.getString("note");

                        report.lines.add(line);
                        report.totalHours = report.totalHours.add(line.hours);
                        report.totalAmount = report.totalAmount.add(line.lineTotal);
                    }
                }
            }
        }

        return report;
    }

    public MonthlySummaryReport generateMonthlySummaryReport(int year, String month) throws SQLException {
        MonthlySummaryReport report = new MonthlySummaryReport();

        String startDate = year + "-" + month + "-01";
        String endDate = year + "-" + month + "-31";

        String sql = "SELECT c.name as customer_name, " +
                "SUM(bh.hours) as total_hours, " +
                "SUM(bh.hours * bc.hourly_rate) as total_amount " +
                "FROM billable_hours bh " +
                "JOIN customers c ON bh.customer_id = c.id " +
                "JOIN billing_categories bc ON bh.category_id = bc.id " +
                "WHERE bh.date_logged >= ? AND bh.date_logged <= ? " +
                "GROUP BY c.name " +
                "ORDER BY total_amount DESC";

        try (Connection conn = LibertyConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(startDate));
            stmt.setDate(2, Date.valueOf(endDate));

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    MonthlySummaryRow row = new MonthlySummaryRow();
                    row.customerName = rs.getString("customer_name");
                    row.totalHours = defaultDecimal(rs.getBigDecimal("total_hours"));
                    row.totalAmount = defaultDecimal(rs.getBigDecimal("total_amount"));

                    report.rows.add(row);
                    report.monthlyHours = report.monthlyHours.add(row.totalHours);
                    report.monthlyTotal = report.monthlyTotal.add(row.totalAmount);
                }
            }
        }

        return report;
    }

    public RevenueSummaryReport generateRevenueSummaryReport() throws SQLException {
        RevenueSummaryReport report = new RevenueSummaryReport();

        try (Connection conn = LibertyConnectionManager.getConnection();
             Statement stmt = conn.createStatement()) {

            try (ResultSet rs = stmt.executeQuery(
                    "SELECT c.name, " +
                            "SUM(bh.hours) as total_hours, " +
                            "SUM(bh.hours * bc.hourly_rate) as total_revenue, " +
                            "AVG(bc.hourly_rate) as avg_rate " +
                            "FROM customers c " +
                            "LEFT JOIN billable_hours bh ON c.id = bh.customer_id " +
                            "LEFT JOIN billing_categories bc ON bh.category_id = bc.id " +
                            "GROUP BY c.name " +
                            "ORDER BY total_revenue DESC"
            )) {
                while (rs.next()) {
                    RevenueByCustomerRow row = new RevenueByCustomerRow();
                    row.customerName = rs.getString("name");
                    row.totalHours = defaultDecimal(rs.getBigDecimal("total_hours"));
                    row.totalRevenue = defaultDecimal(rs.getBigDecimal("total_revenue"));
                    row.avgRate = defaultDecimal(rs.getBigDecimal("avg_rate"));
                    report.byCustomer.add(row);
                }
            }

            try (ResultSet rs = stmt.executeQuery(
                    "SELECT bc.name, bc.hourly_rate, " +
                            "COALESCE(SUM(bh.hours), 0) as total_hours, " +
                            "COALESCE(SUM(bh.hours * bc.hourly_rate), 0) as total_revenue " +
                            "FROM billing_categories bc " +
                            "LEFT JOIN billable_hours bh ON bc.id = bh.category_id " +
                            "GROUP BY bc.name, bc.hourly_rate " +
                            "ORDER BY total_revenue DESC"
            )) {
                while (rs.next()) {
                    RevenueByCategoryRow row = new RevenueByCategoryRow();
                    row.categoryName = rs.getString("name");
                    row.hourlyRate = defaultDecimal(rs.getBigDecimal("hourly_rate"));
                    row.totalHours = defaultDecimal(rs.getBigDecimal("total_hours"));
                    row.totalRevenue = defaultDecimal(rs.getBigDecimal("total_revenue"));
                    report.byCategory.add(row);
                }
            }
        }

        return report;
    }

    private BigDecimal defaultDecimal(BigDecimal value) {
        return value == null ? BigDecimal.ZERO : value;
    }

    public static class CustomerBillReport {
        private String customerName = "";
        private String customerEmail = "";
        private final List<CustomerBillLine> lines = new ArrayList<>();
        private BigDecimal totalAmount = BigDecimal.ZERO;
        private BigDecimal totalHours = BigDecimal.ZERO;

        public String getCustomerName() {
            return customerName;
        }

        public String getCustomerEmail() {
            return customerEmail;
        }

        public List<CustomerBillLine> getLines() {
            return lines;
        }

        public BigDecimal getTotalAmount() {
            return totalAmount;
        }

        public BigDecimal getTotalHours() {
            return totalHours;
        }
    }

    public static class CustomerBillLine {
        private Date dateLogged;
        private String userName;
        private String categoryName;
        private BigDecimal hours;
        private BigDecimal hourlyRate;
        private BigDecimal lineTotal;
        private String note;

        public Date getDateLogged() {
            return dateLogged;
        }

        public String getUserName() {
            return userName;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public BigDecimal getHours() {
            return hours;
        }

        public BigDecimal getHourlyRate() {
            return hourlyRate;
        }

        public BigDecimal getLineTotal() {
            return lineTotal;
        }

        public String getNote() {
            return note;
        }
    }

    public static class MonthlySummaryReport {
        private final List<MonthlySummaryRow> rows = new ArrayList<>();
        private BigDecimal monthlyTotal = BigDecimal.ZERO;
        private BigDecimal monthlyHours = BigDecimal.ZERO;

        public List<MonthlySummaryRow> getRows() {
            return rows;
        }

        public BigDecimal getMonthlyTotal() {
            return monthlyTotal;
        }

        public BigDecimal getMonthlyHours() {
            return monthlyHours;
        }
    }

    public static class MonthlySummaryRow {
        private String customerName;
        private BigDecimal totalHours;
        private BigDecimal totalAmount;

        public String getCustomerName() {
            return customerName;
        }

        public BigDecimal getTotalHours() {
            return totalHours;
        }

        public BigDecimal getTotalAmount() {
            return totalAmount;
        }
    }

    public static class RevenueSummaryReport {
        private final List<RevenueByCustomerRow> byCustomer = new ArrayList<>();
        private final List<RevenueByCategoryRow> byCategory = new ArrayList<>();

        public List<RevenueByCustomerRow> getByCustomer() {
            return byCustomer;
        }

        public List<RevenueByCategoryRow> getByCategory() {
            return byCategory;
        }
    }

    public static class RevenueByCustomerRow {
        private String customerName;
        private BigDecimal totalHours;
        private BigDecimal totalRevenue;
        private BigDecimal avgRate;

        public String getCustomerName() {
            return customerName;
        }

        public BigDecimal getTotalHours() {
            return totalHours;
        }

        public BigDecimal getTotalRevenue() {
            return totalRevenue;
        }

        public BigDecimal getAvgRate() {
            return avgRate;
        }
    }

    public static class RevenueByCategoryRow {
        private String categoryName;
        private BigDecimal hourlyRate;
        private BigDecimal totalHours;
        private BigDecimal totalRevenue;

        public String getCategoryName() {
            return categoryName;
        }

        public BigDecimal getHourlyRate() {
            return hourlyRate;
        }

        public BigDecimal getTotalHours() {
            return totalHours;
        }

        public BigDecimal getTotalRevenue() {
            return totalRevenue;
        }
    }
}
