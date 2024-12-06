package busticketingsystem;
import java.util.InputMismatchException;
import java.util.Scanner;

public class BusSchedule {

    public void scheduleTransaction() {
        Scanner sc = new Scanner(System.in);
        String response;

        do {
            System.out.println("");
            System.out.println("[******BUS SCHEDULE MANAGEMENT******]");
            System.out.println("");
            System.out.println("1. --ADD SCHEDULE--");
            System.out.println("2. --VIEW SCHEDULES--");
            System.out.println("3. --UPDATE SCHEDULE--");
            System.out.println("4. --DELETE SCHEDULE--");
            System.out.println("5. --EXIT SCHEDULE MANAGEMENT--");

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

            BusSchedule bs = new BusSchedule();

            switch (action) {
                case 1:
                    bs.addSchedule();
                    break;
                case 2:
                    bs.viewSchedules();
                    break;
                case 3:
                    bs.viewSchedules();
                    bs.updateSchedule();
                    bs.viewSchedules();
                    break;
                case 4:
                    bs.viewSchedules();
                    bs.deleteSchedule();
                    bs.viewSchedules();
                    break;
                case 5:
                    System.out.println("Exiting Bus Schedule Management...");
                    return;
                default:
                    System.out.println("Invalid choice! Please select a valid option (1-5).");
            }

            System.out.print("Do you want to continue? (yes/no): ");
            response = sc.next();

        } while (response.equalsIgnoreCase("yes"));

        System.out.println("Thank You, See you soon!");
    }

    public void addSchedule() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        System.out.print("Bus Number: ");
        String busNumber = sc.nextLine().trim();
        while (busNumber.isEmpty()) {
            System.out.print("Bus Number cannot be empty. Please enter a valid Bus Number: ");
            busNumber = sc.nextLine().trim();
        }

        System.out.print("Departure Location: ");
        String departure = sc.nextLine().trim();
        while (departure.isEmpty()) {
            System.out.print("Departure Location cannot be empty. Please enter a valid location: ");
            departure = sc.nextLine().trim();
        }

        System.out.print("Arrival Location: ");
        String arrival = sc.nextLine().trim();
        while (arrival.isEmpty()) {
            System.out.print("Arrival Location cannot be empty. Please enter a valid location: ");
            arrival = sc.nextLine().trim();
        }

        System.out.print("Departure Time (HH:MM): ");
        String departureTime = sc.nextLine().trim();
        while (!departureTime.matches("\\d{2}:\\d{2}")) {
            System.out.print("Invalid time format! Please enter in HH:MM format: ");
            departureTime = sc.nextLine().trim();
        }

        System.out.print("Fare: ");
        double fare = -1;
        while (true) {
            if (sc.hasNextDouble()) {
                fare = sc.nextDouble();
                if (fare > 0) break;
            }
            System.out.print("Invalid input! Please enter a positive fare amount: ");
            sc.next();
        }

        String sql = "INSERT INTO tbl_schedule (bus_number, departure, arrival, departure_time, fare) VALUES (?, ?, ?, ?, ?)";
        conf.addRecord(sql, busNumber, departure, arrival, departureTime, fare);
    }

    public void viewSchedules() {
        config conf = new config();
        String query = "SELECT * FROM tbl_schedule";
        String[] headers = {"Schedule ID", "Bus Number", "Departure", "Arrival", "Departure Time", "Fare"};
        String[] columns = {"schedule_id", "bus_number", "departure", "arrival", "departure_time", "fare"};

        conf.viewRecords(query, headers, columns);
    }

    private void updateSchedule() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        int id;

        while (true) {
            System.out.print("Enter the Schedule ID to update: ");
            while (!sc.hasNextInt()) {
                System.out.print("Invalid input! Please enter a valid Schedule ID: ");
                sc.next();
            }
            id = sc.nextInt();
            if (conf.getSingleValue("SELECT schedule_id FROM tbl_schedule WHERE schedule_id = ?", id) != 0) {
                break; 
            }
            System.out.println("Selected ID doesn't exist! Try again.");
        }

        sc.nextLine(); 

        System.out.print("New Bus Number: ");
        String newBusNumber = sc.nextLine().trim();
        while (newBusNumber.isEmpty()) {
            System.out.print("Bus Number cannot be empty. Please enter a valid Bus Number: ");
            newBusNumber = sc.nextLine().trim();
        }

        System.out.print("New Departure Location: ");
        String newDeparture = sc.nextLine().trim();
        while (newDeparture.isEmpty()) {
            System.out.print("Departure Location cannot be empty. Please enter a valid location: ");
            newDeparture = sc.nextLine().trim();
        }

        System.out.print("New Arrival Location: ");
        String newArrival = sc.nextLine().trim();
        while (newArrival.isEmpty()) {
            System.out.print("Arrival Location cannot be empty. Please enter a valid location: ");
            newArrival = sc.nextLine().trim();
        }

        System.out.print("New Departure Time (HH:MM): ");
        String newDepartureTime = sc.nextLine().trim();
        while (!newDepartureTime.matches("\\d{2}:\\d{2}")) {
            System.out.print("Invalid time format! Please enter in HH:MM format: ");
            newDepartureTime = sc.nextLine().trim();
        }

        System.out.print("New Fare: ");
        double newFare = -1;
        while (true) {
            if (sc.hasNextDouble()) {
                newFare = sc.nextDouble();
                if (newFare > 0) break;
            }
            System.out.print("Invalid input! Please enter a positive fare amount: ");
            sc.next();
        }

        String qry = "UPDATE tbl_schedule SET bus_number = ?, departure = ?, arrival = ?, departure_time = ?, fare = ? WHERE schedule_id = ?";
        conf.updateRecord(qry, newBusNumber, newDeparture, newArrival, newDepartureTime, newFare, id);
    }

    private void deleteSchedule() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        int id;

        while (true) {
            System.out.print("Enter the Schedule ID to delete: ");
            while (!sc.hasNextInt()) {
                System.out.print("Invalid input! Please enter a valid Schedule ID: ");
                sc.next();
            }
            id = sc.nextInt();
            if (conf.getSingleValue("SELECT schedule_id FROM tbl_schedule WHERE schedule_id = ?", id) != 0) {
                break; 
            }
            System.out.println("Selected ID doesn't exist! Try again.");
        }

        String qry = "DELETE FROM tbl_schedule WHERE schedule_id = ?";
        conf.deleteRecord(qry, id);
    }

    boolean isValidTicket(int customerOrTicketId) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
}
