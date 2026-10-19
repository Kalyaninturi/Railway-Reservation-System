
package railwayreservation;

public class Main {

    public static void main(String[] args) {

        InputHelper input = new InputHelper();
        RailwayService railwayService = new RailwayService();
        UserService userService = new UserService();
        Admin admin = new Admin("admin", "admin123");
        ReportService reports =
                new ReportService(railwayService, new FileHandler());

        boolean running = true;

        while (running) {

            System.out.println("\n===== RAILWAY RESERVATION SYSTEM =====");
            System.out.println("1. User Registration");
            System.out.println("2. User Login");
            System.out.println("3. Add Records");
            System.out.println("4. Update Records");
            System.out.println("5. View Reports");
            System.out.println("6. Admin Operations");
            System.out.println("7. Search Records");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");

            switch (input.readInt()) {

                case 1:
                    System.out.print("Enter Username: ");
                    String ru = input.readText();

                    System.out.print("Enter Password: ");
                    String rp = input.readText();

                    userService.registerUser(ru, rp);
                    break;

                case 2:
                    System.out.print("Enter Username: ");
                    String lu = input.readText();

                    System.out.print("Enter Password: ");
                    String lp = input.readText();

                    User logged = userService.loginUser(lu, lp);

                    if (logged != null) {

                        boolean um = true;

                        while (um) {

                            System.out.println("\n===== USER MENU =====");
                            System.out.println("1. View Trains");
                            System.out.println("2. Check Available Seats");
                            System.out.println("3. Book Ticket");
                            System.out.println("4. Cancel Ticket");
                            System.out.println("5. View Bookings");
                            System.out.println("6. Logout");
                            System.out.print("Enter your choice: ");

                            switch (input.readInt()) {

                                case 1:
                                    railwayService.viewTrains();
                                    break;

                                case 2:
                                    System.out.print("Enter Train Number: ");
                                    railwayService.checkSeats(input.readInt());
                                    break;

                                case 3:
                                    System.out.print("Enter Passenger Name: ");
                                    String pn = input.readText();

                                    System.out.print("Enter Contact Number: ");
                                    String cn = input.readText();

                                    System.out.print("Enter Train Number: ");
                                    int tn = input.readInt();

                                    System.out.print("Enter Number of Seats: ");
                                    int seats = input.readInt();

                                    railwayService.bookTicket(pn, cn, tn, seats);
                                    break;

                                case 4:
                                    System.out.print("Enter Booking ID: ");
                                    railwayService.cancelTicket(input.readInt());
                                    break;

                                case 5:
                                    railwayService.viewBookings();
                                    break;

                                case 6:
                                    um = false;
                                    System.out.println("Logged out successfully.");
                                    break;

                                default:
                                    System.out.println("Invalid choice.");
                            }
                        }
                    }
                    break;

                case 3:
                    System.out.print("Enter Record ID: ");
                    int id = input.readInt();

                    if (id <= 0) {
                        System.out.println(
                                "Invalid ID! Record ID must be greater than zero.");
                        break;
                    }

                    if (railwayService.searchRecord(id) != null) {
                        System.out.println(
                                "Duplicate record ID. Enter a different ID.");
                        break;
                    }

                    System.out.print("Enter Name: ");
                    String name = input.readText();

                    System.out.print("Enter Contact Number: ");
                    String contact = input.readText();

                    System.out.print("Enter Category: ");
                    String cat = input.readText();

                    System.out.print("Enter Status: ");
                    String status = input.readText();

                    railwayService.addRecord(
                            new Record(id, name, contact, cat, status));
                    break;

                case 4:
                    System.out.print("Enter Record ID to update: ");
                    int uid = input.readInt();

                    if (uid <= 0) {
                        System.out.println(
                                "Record ID must be greater than zero.");
                        break;
                    }

                    System.out.print("Enter New Name: ");
                    String nn = input.readText();

                    System.out.print("Enter New Contact Number: ");
                    String nc = input.readText();

                    System.out.print("Enter New Category: ");
                    String ncat = input.readText();

                    System.out.print("Enter New Status: ");
                    String ns = input.readText();

                    railwayService.updateRecord(uid, nn, nc, ncat, ns);
                    break;

                case 5:
                    reports.generateReport();

                    System.out.println("\n===== ALL RECORDS =====");
                    railwayService.viewRecords();

                    System.out.println("\n===== ALL BOOKINGS =====");
                    railwayService.viewBookings();
                    break;

                case 6:
                    System.out.print("Enter Admin Username: ");
                    String au = input.readText();

                    System.out.print("Enter Admin Password: ");
                    String ap = input.readText();

                    if (admin.login(au, ap)) {

                        boolean am = true;

                        while (am) {

                            admin.displayAdminMenu();

                            switch (input.readInt()) {

                                case 1:
                                    railwayService.viewTrains();
                                    break;

                                case 2:
                                    railwayService.viewBookings();
                                    break;

                                case 3:
                                    railwayService.viewRecords();
                                    break;

                                case 4:
                                    System.out.print("Enter Record ID to delete: ");
                                    int did = input.readInt();

                                    if (did <= 0) {
                                        System.out.println(
                                                "Record ID must be greater than zero.");
                                    } else {
                                        railwayService.deleteRecord(did);
                                    }
                                    break;

                                case 5:
                                    reports.generateReport();
                                    break;

                                case 6:
                                    am = false;
                                    System.out.println(
                                            "Admin logged out successfully.");
                                    break;

                                default:
                                    System.out.println("Invalid choice.");
                            }
                        }

                    } else {
                        System.out.println("Invalid admin credentials.");
                    }
                    break;

                case 7:
                    System.out.print("Enter Record ID: ");
                    int sid = input.readInt();

                    if (sid <= 0) {
                        System.out.println(
                                "Record ID must be greater than zero.");
                        break;
                    }

                    Record found = railwayService.searchRecord(sid);

                    if (found != null) {
                        found.displayRecord();
                    } else {
                        System.out.println("Record not found.");
                    }
                    break;

                case 8:
                    running = false;
                    System.out.println(
                            "Thank you for using Railway Reservation System.");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
