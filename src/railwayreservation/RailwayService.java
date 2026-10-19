
package railwayreservation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RailwayService {

    private final List<Train> trains = new ArrayList<>();
    private final List<Booking> bookings = new ArrayList<>();
    private final List<Record> records = new ArrayList<>();

    private final FileHandler fileHandler = new FileHandler();

    private int nextBookingId = 1001;

    public RailwayService() {

        // Existing trains
        trains.add(new Train(
                101, "Guntur Express",
                "Guntur", "Hyderabad", 50));

        trains.add(new Train(
                102, "Sabari Express",
                "Guntur", "Thiruvananthapuram", 40));

        // New trains
        trains.add(new Train(
                103, "Krishna Express",
                "Guntur", "Vijayawada", 60));

        trains.add(new Train(
                104, "Deccan Express",
                "Vijayawada", "Secunderabad", 45));

        loadRecords();
        loadBookings();
    }

    private boolean validContact(String contact) {
        return contact != null && contact.matches("[0-9]{10}");
    }

    // Display all trains
    public void viewTrains() {

        System.out.println("\n===== AVAILABLE TRAINS =====");

        for (Train t : trains) {
            t.displayTrainDetails();
            System.out.println("-------------------------");
        }
    }

    // Find a train by train number
    public Train findTrain(int number) {

        for (Train t : trains) {
            if (t.getTrainNumber() == number) {
                return t;
            }
        }

        return null;
    }

    // Check available seats
    public void checkSeats(int number) {

        Train t = findTrain(number);

        if (t == null) {
            System.out.println("Train not found.");
            return;
        }

        System.out.println("Train: " + t.getTrainName());
        System.out.println("Available Seats: " + t.getAvailableSeats());
    }

    // Book a ticket
    public Booking bookTicket(
            String name, String contact, int trainNumber, int seats) {

        Train train = findTrain(trainNumber);

        if (name == null || name.trim().isEmpty()) {
            System.out.println("Passenger name is required.");
            return null;
        }

        if (!validContact(contact)) {
            System.out.println("Enter a valid 10-digit contact number.");
            return null;
        }

        if (train == null) {
            System.out.println("Train not found.");
            return null;
        }

        if (seats <= 0 || seats > train.getAvailableSeats()) {
            System.out.println(
                    "Invalid seat count or insufficient seats.");
            return null;
        }

        Booking b = new Booking(
                nextBookingId, name.trim(), contact, trainNumber, seats);

        train.setAvailableSeats(train.getAvailableSeats() - seats);
        bookings.add(b);

        if (!saveBookings()) {
            bookings.remove(b);
            train.setAvailableSeats(
                    train.getAvailableSeats() + seats);

            System.out.println("Booking could not be saved.");
            return null;
        }

        nextBookingId++;

        System.out.println("Ticket booked successfully!");
        System.out.println("Booking ID: " + b.getBookingId());

        return b;
    }

    // View all bookings
    public void viewBookings() {

        if (bookings.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        for (Booking b : bookings) {
            b.displayBookingDetails();
            System.out.println("-------------------------");
        }
    }

    private Booking findBooking(int id) {

        for (Booking b : bookings) {
            if (b.getBookingId() == id) {
                return b;
            }
        }

        return null;
    }

    // Cancel a ticket
    public boolean cancelTicket(int id) {

        Booking b = findBooking(id);

        if (b == null || !b.getStatus().equals("CONFIRMED")) {
            System.out.println("Active booking not found.");
            return false;
        }

        Train t = findTrain(b.getTrainNumber());

        if (t == null) {
            System.out.println("Train not found.");
            return false;
        }

        b.cancelBooking();

        if (!saveBookings()) {

            bookings.set(
                    bookings.indexOf(b),
                    new Booking(
                            b.getBookingId(),
                            b.getPassengerName(),
                            b.getContactNumber(),
                            b.getTrainNumber(),
                            b.getSeatsBooked(),
                            "CONFIRMED"
                    )
            );

            System.out.println("Cancellation could not be saved.");
            return false;
        }

        t.setAvailableSeats(
                t.getAvailableSeats() + b.getSeatsBooked());

        System.out.println("Ticket cancelled successfully.");

        return true;
    }

    // Add a record
    public boolean addRecord(Record r) {

        if (r == null) {
            System.out.println("Invalid record.");
            return false;
        }

        if (r.getId() <= 0) {
            System.out.println(
                    "Invalid ID! Record ID must be greater than zero.");
            return false;
        }

        if (r.getName() == null || r.getName().trim().isEmpty()) {
            System.out.println("Name is required.");
            return false;
        }

        if (!validContact(r.getContactNumber())) {
            System.out.println("Enter a valid 10-digit contact number.");
            return false;
        }

        if (searchRecord(r.getId()) != null) {
            System.out.println("Duplicate record ID.");
            return false;
        }

        records.add(r);

        if (!saveRecords()) {
            records.remove(r);
            System.out.println("Record could not be saved.");
            return false;
        }

        System.out.println("Record added successfully.");

        return true;
    }

    // Search a record by ID
    public Record searchRecord(int id) {

        for (Record r : records) {
            if (r.getId() == id) {
                return r;
            }
        }

        return null;
    }

    // Update an existing record
    public boolean updateRecord(
            int id, String name, String contact,
            String category, String status) {

        Record r = searchRecord(id);

        if (r == null) {
            System.out.println("Record not found.");
            return false;
        }

        if (name == null || name.trim().isEmpty()) {
            System.out.println("Name is required.");
            return false;
        }

        if (!validContact(contact)) {
            System.out.println("Enter a valid 10-digit contact number.");
            return false;
        }

        String oldName = r.getName();
        String oldContact = r.getContactNumber();
        String oldCategory = r.getCategory();
        String oldStatus = r.getStatus();

        r.setName(name.trim());
        r.setContactNumber(contact);
        r.setCategory(category);
        r.setStatus(status);

        if (!saveRecords()) {
            r.setName(oldName);
            r.setContactNumber(oldContact);
            r.setCategory(oldCategory);
            r.setStatus(oldStatus);

            System.out.println("Record update could not be saved.");
            return false;
        }

        System.out.println("Record updated successfully.");

        return true;
    }

    // Delete a record
    public boolean deleteRecord(int id) {

        Record r = searchRecord(id);

        if (r == null) {
            System.out.println("Record not found.");
            return false;
        }

        records.remove(r);

        if (!saveRecords()) {
            records.add(r);
            System.out.println("Record deletion could not be saved.");
            return false;
        }

        System.out.println("Record deleted successfully.");

        return true;
    }

    // View all records
    public void viewRecords() {

        if (records.isEmpty()) {
            System.out.println("No records found.");
            return;
        }

        for (Record r : records) {
            r.displayRecord();
            System.out.println("-------------------------");
        }
    }

    // Save records to a file
    private boolean saveRecords() {

        List<String> lines = new ArrayList<>();

        for (Record r : records) {
            lines.add(
                    r.getId() + "|"
                    + r.getName() + "|"
                    + r.getContactNumber() + "|"
                    + r.getCategory() + "|"
                    + r.getStatus()
            );
        }

        try {
            fileHandler.saveData("records.txt", lines);
            return true;

        } catch (IOException e) {
            System.out.println(
                    "Error saving records: " + e.getMessage());
            return false;
        }
    }

    // Load records from a file
    private void loadRecords() {

        try {
            for (String line : fileHandler.loadData("records.txt")) {

                String[] p = line.split("\\|", -1);

                if (p.length == 5) {
                    try {
                        int id = Integer.parseInt(p[0]);

                        if (id > 0 && searchRecord(id) == null) {
                            records.add(
                                    new Record(
                                            id, p[1], p[2], p[3], p[4]));
                        }

                    } catch (NumberFormatException e) {
                        System.out.println(
                                "Skipping record with invalid ID.");
                    }
                }
            }

        } catch (IOException e) {
            System.out.println(
                    "Error loading records: " + e.getMessage());
        }
    }

    // Save bookings to a file
    private boolean saveBookings() {

        List<String> lines = new ArrayList<>();

        for (Booking b : bookings) {
            lines.add(
                    b.getBookingId() + "|"
                    + b.getPassengerName() + "|"
                    + b.getContactNumber() + "|"
                    + b.getTrainNumber() + "|"
                    + b.getSeatsBooked() + "|"
                    + b.getStatus()
            );
        }

        try {
            fileHandler.saveData("bookings.txt", lines);
            return true;

        } catch (IOException e) {
            System.out.println(
                    "Error saving bookings: " + e.getMessage());
            return false;
        }
    }

    // Load bookings from a file
    private void loadBookings() {

        try {
            for (String line : fileHandler.loadData("bookings.txt")) {

                String[] p = line.split("\\|", -1);

                if (p.length != 6) {
                    continue;
                }

                try {
                    int id = Integer.parseInt(p[0]);
                    int trainNumber = Integer.parseInt(p[3]);
                    int seats = Integer.parseInt(p[4]);

                    String status = p[5];
                    Train t = findTrain(trainNumber);

                    if (id <= 0 || t == null
                            || !(status.equals("CONFIRMED")
                            || status.equals("CANCELLED"))
                            || seats <= 0
                            || seats > t.getTotalSeats()
                            || findBooking(id) != null) {
                        continue;
                    }

                    if (status.equals("CONFIRMED")
                            && seats > t.getAvailableSeats()) {
                        System.out.println(
                                "Skipping booking: insufficient capacity.");
                        continue;
                    }

                    bookings.add(
                            new Booking(
                                    id, p[1], p[2],
                                    trainNumber, seats, status));

                    if (status.equals("CONFIRMED")) {
                        t.setAvailableSeats(
                                t.getAvailableSeats() - seats);
                    }

                    if (id >= nextBookingId) {
                        nextBookingId = id + 1;
                    }

                } catch (NumberFormatException e) {
                    System.out.println(
                            "Skipping booking with invalid numbers.");
                }
            }

        } catch (IOException e) {
            System.out.println(
                    "Error loading bookings: " + e.getMessage());
        }
    }

    // Total trains
    public int getTotalTrains() {
        return trains.size();
    }

    // Total bookings
    public int getTotalBookings() {
        return bookings.size();
    }

    // Total records
    public int getTotalRecords() {
        return records.size();
    }
}
