
package railwayreservation;

public class Booking {

    private final int bookingId;
    private final int trainNumber;
    private final int seatsBooked;

    private final String passengerName;
    private final String contactNumber;

    private String status;

    // Constructor for a new booking
    public Booking(int id, String name, String contact,
                   int train, int seats) {

        this(id, name, contact, train, seats, "CONFIRMED");
    }

    // Constructor with booking status
    public Booking(int id, String name, String contact,
                   int train, int seats, String status) {

        bookingId = id;
        passengerName = name;
        contactNumber = contact;
        trainNumber = train;
        seatsBooked = seats;
        this.status = status;
    }

    // Getters
    public int getBookingId() {
        return bookingId;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public int getTrainNumber() {
        return trainNumber;
    }

    public int getSeatsBooked() {
        return seatsBooked;
    }

    public String getStatus() {
        return status;
    }

    // Cancel booking
    public void cancelBooking() {
        status = "CANCELLED";
    }

    // Display booking details
    public void displayBookingDetails() {
        System.out.println("Booking ID: " + bookingId);
        System.out.println("Passenger: " + passengerName);
        System.out.println("Contact: " + contactNumber);
        System.out.println("Train Number: " + trainNumber);
        System.out.println("Seats Booked: " + seatsBooked);
        System.out.println("Status: " + status);
    }
}
