package busticketingsystem;

import java.util.Scanner;

public class BusTicketingSystem {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean continueSystem = true;

        while (continueSystem) {
            System.out.println("");
            System.out.println("------------------------------------------");
            System.out.println("| WELCOME TO BUS TICKETING SYSTEM     |");
            System.out.println("------------------------------------------");
            System.out.println("");
            System.out.println("1. CUSTOMER PAPER TICKET");
            System.out.println("2. BUS SCHEDULE");
            System.out.println("3. REPORTS");
            System.out.println("4. EXIT");

            int action = -1;

            while (true) {
                System.out.print("Enter Action (1-4): ");
                try {
                    action = sc.nextInt();
                    sc.nextLine(); 
                    if (action >= 1 && action <= 4) {
                        break;
                    } else {
                        System.out.println("Invalid choice! Please select a number between 1 and 4.");
                    }
                } catch (Exception e) {
                    System.out.println("Invalid input! Please enter a number between 1 and 4.");
                    sc.nextLine(); 
                }
            }

            switch (action) {
                case 1:
                    CustomerPaperTicket customerTicket = new CustomerPaperTicket();
                    customerTicket.customerTransaction();
                    break;

                case 2:
                    BusSchedule schedule = new BusSchedule();
                    schedule.scheduleTransaction();
                    break;

                case 3:
                    Reports reports = new Reports();
                    reports.reportMenu();
                    break;

                case 4:
                    System.out.print("Are you sure you want to exit? (yes/no): ");
                    String response = sc.nextLine();
                    if (response.equalsIgnoreCase("yes")) {
                        continueSystem = false;
                    }
                    break;

                default:
                    System.out.println("Invalid choice! Please select a valid option (1-4).");
            }
        }

        System.out.println("Thank you for using the Bus Ticketing System! Goodbye!");
        sc.close();
    }
}
