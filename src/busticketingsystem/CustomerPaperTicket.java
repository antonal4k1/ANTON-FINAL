package busticketingsystem;

import java.util.InputMismatchException;
import java.util.Scanner;

public class CustomerPaperTicket {

    public void customerTransaction() {
        Scanner sc = new Scanner(System.in);
        String response;

        do {
            System.out.println("");
            System.out.println("[******WELCOME TO BUS TICKETING SYSTEM******]");
            System.out.println("");
            System.out.println("1. --ADD BOOK TICKET--");
            System.out.println("2. --VIEW TICKETS--");
            System.out.println("3. --UPDATE TICKET--");
            System.out.println("4. --DELETE TICKET--");
            System.out.println("5. --EXIT SYSTEM--");

            int action = -1;

            while (true) {
                System.out.print("Enter Action: ");
                if (sc.hasNextInt()) {
                    action = sc.nextInt();
                    if (action >= 1 && action <= 5) {
                        break;
                    } else {
                        System.out.println("Invalid input! Please enter a number between 1 and 5.");
                    }
                } else {
                    System.out.println("Invalid input! Please enter a number between 1 and 5.");
                    sc.next(); 
                }
            }

            switch (action) {
                case 1:
                    bookTicket();
                    viewTickets();
                    break;
                case 2:
                    viewTickets();
                    break;
                case 3:
                    viewTickets();
                    updateTicket();
                    viewTickets();
                    break;
                case 4:
                    viewTickets();
                    deleteTicket();
                    viewTickets();
                    break;
                case 5:
                    System.out.println("Exiting Ticketing System...");
                    return;
                default:
                    System.out.println("Invalid choice! Please select a valid option (1-5).");
            }

            System.out.print("Do you want to continue? (yes/no): ");
            response = sc.next();

        } while (response.equalsIgnoreCase("yes"));

        System.out.println("Thank You, Have a safe journey!");
    }

    public void bookTicket() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        System.out.print("Passenger Name: ");
        String name = sc.nextLine().trim();

        while (name.isEmpty()) {
            System.out.print("Name cannot be empty. Please enter a valid Passenger Name: ");
            name = sc.nextLine().trim();
        }

        System.out.print("Contact Number: ");
        String contact = sc.nextLine().trim();

        while (!contact.matches("\\d{10}")) { 
            System.out.print("Invalid contact number! Please enter a 10-digit contact number: ");
            contact = sc.nextLine().trim();
        }

        System.out.print("Email: ");
        String email = sc.nextLine().trim();

        while (!email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
            System.out.print("Invalid email format! Please enter a valid email: ");
            email = sc.nextLine().trim();
        }

        System.out.print("Destination: ");
        String destination = sc.nextLine().trim();

        while (destination.isEmpty()) {
            System.out.print("Destination cannot be empty. Please enter a valid Destination: ");
            destination = sc.nextLine().trim();
        }

        System.out.print("Travel Date (YYYY-MM-DD): ");
        String travelDate = sc.nextLine().trim();

        while (!travelDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
            System.out.print("Invalid date format! Please enter the date in YYYY-MM-DD format: ");
            travelDate = sc.nextLine().trim();
        }

        String sql = "INSERT INTO tbl_ticketing (passenger_name, contact, email, destination, travel_date) VALUES (?, ?, ?, ?, ?)";
        conf.addRecord(sql, name, contact, email, destination, travelDate);
    }

    public void viewTickets() {
        config conf = new config();
        String query = "SELECT * FROM tbl_ticketing";
        String[] headers = {"Ticket_ID", "Passenger Name", "Contact", "Email", "Destination", "Travel Date"};
        String[] columns = {"ticket_id", "passenger_name", "contact", "email", "destination", "travel_date"};

        conf.viewRecords(query, headers, columns);
    }

    private void updateTicket() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        int id;

        while (true) {
            System.out.print("Enter the Ticket ID to update: ");
            while (!sc.hasNextInt()) {
                System.out.print("Invalid input! Please enter a valid Ticket ID: ");
                sc.next();
            }
            id = sc.nextInt();
            if (conf.getSingleValue("SELECT ticket_id FROM tbl_ticket WHERE ticket_id = ?", id) != 0) {
                break;
            }
            System.out.println("Selected ID doesn't exist! Try again.");
        }

        sc.nextLine(); 

        System.out.print("New Passenger Name: ");
        String newName = sc.nextLine().trim();

        while (newName.isEmpty()) {
            System.out.print("Name cannot be empty. Please enter a valid Passenger Name: ");
            newName = sc.nextLine().trim();
        }

        System.out.print("New Contact Number: ");
        String newContact = sc.nextLine().trim();

        while (!newContact.matches("\\d{10}")) {
            System.out.print("Invalid contact number! Please enter a 10-digit contact number: ");
            newContact = sc.nextLine().trim();
        }

        System.out.print("New Email: ");
        String newEmail = sc.nextLine().trim();

        while (!newEmail.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
            System.out.print("Invalid email format! Please enter a valid email: ");
            newEmail = sc.nextLine().trim();
        }

        System.out.print("New Destination: ");
        String newDestination = sc.nextLine().trim();

        while (newDestination.isEmpty()) {
            System.out.print("Destination cannot be empty. Please enter a valid Destination: ");
            newDestination = sc.nextLine().trim();
        }

        System.out.print("New Travel Date (YYYY-MM-DD): ");
        String newTravelDate = sc.nextLine().trim();

        while (!newTravelDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
            System.out.print("Invalid date format! Please enter the date in YYYY-MM-DD format: ");
            newTravelDate = sc.nextLine().trim();
        }

        String query = "UPDATE tbl_ticketing SET passenger_name = ?, contact = ?, email = ?, destination = ?, travel_date = ? WHERE ticket_id = ?";
        conf.updateRecord(query, newName, newContact, newEmail, newDestination, newTravelDate, id);
    }

    private void deleteTicket() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        int id;

        while (true) {
            System.out.print("Enter the Ticket ID to delete: ");
            while (!sc.hasNextInt()) {
                System.out.print("Invalid input! Please enter a valid Ticket ID: ");
                sc.next();
            }
            id = sc.nextInt();
            if (conf.getSingleValue("SELECT ticket_id FROM tbl_ticketing WHERE ticket_id = ?", id) != 0) {
                break;
            }
            System.out.println("Selected ID doesn't exist! Try again.");
        }

        String query = "DELETE FROM tbl_ticketing WHERE ticket_id = ?";
        conf.deleteRecord(query, id);
    }
}
