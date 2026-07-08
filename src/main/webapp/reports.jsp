<%@ page import="java.util.*" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="org.joda.time.LocalDate" %>
<%@ page import="com.sourcegraph.demo.bigbadmonolith.dao.*" %>
<%@ page import="com.sourcegraph.demo.bigbadmonolith.entity.*" %>
<%@ page import="com.sourcegraph.demo.bigbadmonolith.service.ReportingService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    CustomerDAO customerDAO = new CustomerDAO();
    ReportingService reportingService = new ReportingService();
    
    String reportType = request.getParameter("reportType");
    String customerId = request.getParameter("customerId");
    String month = request.getParameter("month");
    String year = request.getParameter("year");

    DecimalFormat df = new DecimalFormat("#,##0.00");
%>
<html>
<head>
    <title>Reports - Big Bad Monolith</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; background-color: #f5f5f5; }
        .header { background-color: #333; color: white; padding: 20px; text-align: center; }
        .nav { background-color: #666; padding: 10px; }
        .nav a { color: white; text-decoration: none; margin-right: 20px; }
        .nav a:hover { text-decoration: underline; }
        .content { background: white; padding: 20px; margin: 20px 0; border: 1px solid #ddd; border-radius: 5px; }
        .form-group { margin-bottom: 15px; display: inline-block; margin-right: 20px; }
        .form-group label { display: block; margin-bottom: 5px; font-weight: bold; }
        .form-group select, .form-group input { padding: 8px; border: 1px solid #ccc; border-radius: 3px; }
        .btn { background: #007acc; color: white; padding: 10px 15px; border: none; border-radius: 3px; cursor: pointer; }
        .btn:hover { background: #005a9e; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid #ddd; padding: 12px; text-align: left; }
        th { background-color: #f2f2f2; }
        .report-section { margin: 30px 0; padding: 20px; border: 1px solid #ddd; border-radius: 5px; }
        .summary-box { background: #e7f3ff; padding: 15px; margin: 15px 0; border-radius: 5px; }
        .text-right { text-align: right; }
        .text-center { text-align: center; }
    </style>
</head>
<body>
    <div class="header">
        <h1>Billing Reports</h1>
    </div>
    
    <div class="nav">
        <a href="index.jsp">Dashboard</a>
        <a href="customers.jsp">Customers</a>
        <a href="users.jsp">Users</a>
        <a href="categories.jsp">Billing Categories</a>
        <a href="hours.jsp">Log Hours</a>
        <a href="reports.jsp">Reports</a>
    </div>
    
    <div class="content">
        <h2>Generate Reports</h2>
        <form method="get" action="reports.jsp">
            <div class="form-group">
                <label for="reportType">Report Type:</label>
                <select id="reportType" name="reportType">
                    <option value="">Select Report Type</option>
                    <option value="customer" <%= "customer".equals(reportType) ? "selected" : "" %>>Customer Bill</option>
                    <option value="monthly" <%= "monthly".equals(reportType) ? "selected" : "" %>>Monthly Summary</option>
                    <option value="revenue" <%= "revenue".equals(reportType) ? "selected" : "" %>>Revenue Summary</option>
                </select>
            </div>
            
            <% if ("customer".equals(reportType)) { %>
            <div class="form-group">
                <label for="customerId">Customer:</label>
                <select id="customerId" name="customerId">
                    <option value="">Select Customer</option>
                    <%
                        try {
                            List<Customer> customers = customerDAO.findAll();
                            for (Customer customer : customers) {
                                String selected = customer.getId().toString().equals(customerId) ? "selected" : "";
                                out.println("<option value='" + customer.getId() + "' " + selected + ">" + 
                                          customer.getName() + "</option>");
                            }
                        } catch (Exception e) {
                            out.println("<option value=''>Error loading customers</option>");
                        }
                    %>
                </select>
            </div>
            <% } %>
            
            <% if ("monthly".equals(reportType)) { %>
            <div class="form-group">
                <label for="year">Year:</label>
                <input type="number" id="year" name="year" value="<%= year != null ? year : "2024" %>" min="2020" max="2030">
            </div>
            <div class="form-group">
                <label for="month">Month:</label>
                <select id="month" name="month">
                    <% 
                        String[] months = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
                        String[] monthNames = {"January", "February", "March", "April", "May", "June", 
                                             "July", "August", "September", "October", "November", "December"};
                        for (int i = 0; i < months.length; i++) {
                            String selected = months[i].equals(month) ? "selected" : "";
                            out.println("<option value='" + months[i] + "' " + selected + ">" + monthNames[i] + "</option>");
                        }
                    %>
                </select>
            </div>
            <% } %>
            
            <button type="submit" class="btn">Generate Report</button>
        </form>
        
        <%
            if ("customer".equals(reportType) && customerId != null && !customerId.trim().isEmpty()) {
        %>
        <div class="report-section">
            <h2>Customer Bill Report</h2>
            <%
                try {
                    ReportingService.CustomerBillReport report = reportingService.generateCustomerBillReport(Long.parseLong(customerId));
            %>
            
            <div class="summary-box">
                <h3>Bill To:</h3>
                <p><strong><%= report.getCustomerName() %></strong><br>
                Email: <%= report.getCustomerEmail() %></p>
            </div>
            
            <table>
                <thead>
                    <tr>
                        <th>Date</th>
                        <th>User</th>
                        <th>Category</th>
                        <th>Hours</th>
                        <th>Rate</th>
                        <th>Amount</th>
                        <th>Description</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        for (ReportingService.CustomerBillLine line : report.getLines()) {
                    %>
                        <tr>
                            <td><%= line.getDateLogged() %></td>
                            <td><%= line.getUserName() %></td>
                            <td><%= line.getCategoryName() %></td>
                            <td class="text-right"><%= df.format(line.getHours()) %></td>
                            <td class="text-right">$<%= df.format(line.getHourlyRate()) %></td>
                            <td class="text-right">$<%= df.format(line.getLineTotal()) %></td>
                            <td><%= line.getNote() != null ? line.getNote() : "" %></td>
                        </tr>
                    <%
                        }
                    %>
                    <tr style="background-color: #f8f9fa; font-weight: bold;">
                        <td colspan="3">TOTAL</td>
                        <td class="text-right"><%= df.format(report.getTotalHours()) %></td>
                        <td></td>
                        <td class="text-right">$<%= df.format(report.getTotalAmount()) %></td>
                        <td></td>
                    </tr>
                </tbody>
            </table>
            
            <%
                } catch (Exception e) {
                    out.println("<p>Error generating customer report: " + e.getMessage() + "</p>");
                }
            %>
        </div>
        <%
            } else if ("monthly".equals(reportType) && year != null && month != null) {
        %>
        <div class="report-section">
            <h2>Monthly Summary - <%= month %>/<%= year %></h2>
            <%
                try {
                    ReportingService.MonthlySummaryReport report = reportingService.generateMonthlySummaryReport(Integer.parseInt(year), month);
            %>
            
            <table>
                <thead>
                    <tr>
                        <th>Customer</th>
                        <th>Total Hours</th>
                        <th>Total Revenue</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        for (ReportingService.MonthlySummaryRow row : report.getRows()) {
                    %>
                        <tr>
                            <td><%= row.getCustomerName() %></td>
                            <td class="text-right"><%= df.format(row.getTotalHours()) %></td>
                            <td class="text-right">$<%= df.format(row.getTotalAmount()) %></td>
                        </tr>
                    <%
                        }
                    %>
                    <tr style="background-color: #f8f9fa; font-weight: bold;">
                        <td>MONTHLY TOTAL</td>
                        <td class="text-right"><%= df.format(report.getMonthlyHours()) %></td>
                        <td class="text-right">$<%= df.format(report.getMonthlyTotal()) %></td>
                    </tr>
                </tbody>
            </table>
            
            <%
                } catch (Exception e) {
                    out.println("<p>Error generating monthly report: " + e.getMessage() + "</p>");
                }
            %>
        </div>
        <%
            } else if ("revenue".equals(reportType)) {
        %>
        <div class="report-section">
            <h2>Revenue Summary</h2>
            <%
                try {
                    ReportingService.RevenueSummaryReport report = reportingService.generateRevenueSummaryReport();
            %>
            
            <h3>By Customer</h3>
            <table>
                <thead>
                    <tr>
                        <th>Customer</th>
                        <th>Total Hours</th>
                        <th>Total Revenue</th>
                        <th>Average Rate</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        for (ReportingService.RevenueByCustomerRow row : report.getByCustomer()) {
                    %>
                        <tr>
                            <td><%= row.getCustomerName() %></td>
                            <td class="text-right"><%= df.format(row.getTotalHours()) %></td>
                            <td class="text-right">$<%= df.format(row.getTotalRevenue()) %></td>
                            <td class="text-right">$<%= df.format(row.getAvgRate()) %></td>
                        </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
            
            <h3>By Category</h3>
            <table>
                <thead>
                    <tr>
                        <th>Category</th>
                        <th>Hourly Rate</th>
                        <th>Total Hours</th>
                        <th>Total Revenue</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        for (ReportingService.RevenueByCategoryRow row : report.getByCategory()) {
                    %>
                        <tr>
                            <td><%= row.getCategoryName() %></td>
                            <td class="text-right">$<%= df.format(row.getHourlyRate()) %></td>
                            <td class="text-right"><%= df.format(row.getTotalHours()) %></td>
                            <td class="text-right">$<%= df.format(row.getTotalRevenue()) %></td>
                        </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
            
            <%
                } catch (Exception e) {
                    out.println("<div class='error'>Error generating revenue summary: " + e.getMessage() + "</div>");
                }
            %>
        </div>
        <%
            }
        %>
        

    </div>
</body>
</html>
