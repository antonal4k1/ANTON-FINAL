package busticketingsystem;

import java.sql.*;
import java.util.Scanner;

public class Reports {
    Scanner input = new Scanner(System.in);
    config conf = new config();

    public void reportMenu() {
        boolean exit = true;
        do {
            System.out.println("+----------------------------------------------------------------------------------------------------+");
            System.out.printf("|%-25s%-50s%-25s|\n", "", "Report Menu", "");
            System.out.printf("|%-5s%-95s|\n", "", "1. View All General Reports");
            System.out.printf("|%-5s%-95s|\n", "", "2. View Individual Report");
            System.out.printf("|%-5s%-95s|\n", "", "3. Exit");
            System.out.println("+----------------------------------------------------------------------------------------------------+");
            
            System.out.printf("%-5sEnter Choice: ", "");
            int choice;

            while (true) {
                try {
                    choice = input.nextInt();
                    if (choice > 0 && choice < 4) {
                        break;
                    } else {
                        System.out.printf("|%-5sEnter Choice Again: ", "");
                    }
                } catch (Exception e) {
                    input.next();
                    System.out.printf("|%-5sEnter Choice Again: ", "");
                }
            }

            switch (choice) {
                case 1:
                    viewAllGeneralReports();
                    break;
                case 2:
                    viewIndividualReport();
                    break;
                default:
                    exit = false;
                    break;
            }
        } while (exit);
    }

    private void viewAllGeneralReports() {
        System.out.println("Displaying all Bus Schedules:");
        viewSchedules();

        System.out.println("\nDisplaying all Tickets:");
        viewTickets();
    }

    private void viewSchedules() {
        String query = "SELECT * FROM tbl_schedule";
        String[] headers = {"Schedule ID", "Bus Number", "Departure", "Arrival", "Departure Time", "Fare"};
        String[] columns = {"schedule_id", "bus_number", "departure", "arrival", "departure_time", "fare"};
        
        try {
            viewRecords(query, headers, columns);
        } catch (Exception e) {
            System.out.println("Error generating schedule report: " + e.getMessage());
        }
    }

    private void viewTickets() {
        String query = "SELECT * FROM tbl_ticketing";
        String[] headers = {"Ticket ID", "Passenger Name", "Contact", "Email", "Destination", "Travel Date"};
        String[] columns = {"ticket_id", "passenger_name", "contact", "email", "destination", "travel_date"};
        
        try {
            viewRecords(query, headers, columns);
        } catch (Exception e) {
            System.out.println("Error generating ticket report: " + e.getMessage());
        }
    }

    private void viewIndividualReport() {
        System.out.println("Choose report type:");
        System.out.println("1. Schedule");
        System.out.println("2. Ticket");
        System.out.printf("Enter choice: ");
        int choice = input.nextInt();

        if (choice == 1) {
            System.out.printf("Enter Schedule ID: ");
            int scheduleId = input.nextInt();
            String query = "SELECT * FROM tbl_schedule WHERE schedule_id = ?";
            String[] headers = {"Schedule ID", "Bus Number", "Departure", "Arrival", "Departure Time", "Fare"};
            String[] columns = {"schedule_id", "bus_number", "departure", "arrival", "departure_time", "fare"};
            try {
                viewIndividualRecord(query, scheduleId, headers, columns);
            } catch (Exception e) {
                System.out.println("Error generating individual bus schedule report: " + e.getMessage());
            }
        } else if (choice == 2) {
            System.out.printf("Enter Ticket ID: ");
            int ticketId = input.nextInt();
            String query = "SELECT * FROM tbl_ticketing WHERE ticket_id = ?";
            String[] headers = {"Ticket ID", "Passenger Name", "Contact", "Email", "Destination", "Travel Date"};
            String[] columns = {"ticket_id", "passenger_name", "contact", "email", "destination", "travel_date"};
            try {
                viewIndividualRecord(query, ticketId, headers, columns);
            } catch (Exception e) {
                System.out.println("Error generating individual ticket report: " + e.getMessage());
            }
        } else {
            System.out.println("Invalid choice.");
        }
    }

    private void viewRecords(String query, String[] headers, String[] columns) throws SQLException {
        try (Connection conn = conf.connectDB();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            System.out.println("+----------------------------------------------------------------------------------------------------+");
            System.out.printf("| %-12s | %-15s | %-15s | %-30s | %-10s | %-12s |\n",
                              headers[0], headers[1], headers[2], headers[3], headers[4], headers[5]);
            System.out.println("+----------------------------------------------------------------------------------------------------+");

            while (rs.next()) {
                System.out.printf("| %-12d | %-15s | %-15s | %-30s | %-10s | %-12s |\n",
                        rs.getInt(columns[0]),
                        rs.getString(columns[1]),
                        rs.getString(columns[2]),
                        rs.getString(columns[3]),
                        rs.getString(columns[4]),
                        rs.getString(columns[5]));
            }

            System.out.println("+----------------------------------------------------------------------------------------------------+");
        }
    }

    private void viewIndividualRecord(String query, int id, String[] headers, String[] columns) throws SQLException {
        try (Connection conn = conf.connectDB();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
        
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("+----------------------------------------------------------------------------------------------------+");
                System.out.printf("| %-12s | %-15s | %-15s | %-30s | %-10s | %-12s |\n",
                        headers[0], headers[1], headers[2], headers[3], headers[4], headers[5]);
                System.out.println("+----------------------------------------------------------------------------------------------------+");

                System.out.printf("| %-12d | %-15s | %-15s | %-30s | %-10s | %-12s |\n",
                        rs.getInt(columns[0]),
                        rs.getString(columns[1]),
                        rs.getString(columns[2]),
                        rs.getString(columns[3]),
                        rs.getString(columns[4]),
                        rs.getString(columns[5]));

                System.out.println("+----------------------------------------------------------------------------------------------------+");
            } else {
                System.out.println("No record found with the provided ID.");
            }
        } catch (SQLException e) {
            System.out.println("Error fetching individual record: " + e.getMessage());
        }
    }
}
