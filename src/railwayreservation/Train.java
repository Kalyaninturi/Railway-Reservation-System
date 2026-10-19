
package railwayreservation;

public class Train {

    private final int trainNumber;
    private final int totalSeats;

    private final String trainName;
    private final String source;
    private final String destination;

    private int availableSeats;

    public Train(int trainNumber, String trainName, String source,
                 String destination, int totalSeats) {

        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.source = source;
        this.destination = destination;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats;
    }

    public int getTrainNumber() {
        return trainNumber;
    }

    public String getTrainName() {
        return trainName;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int seats) {
        if (seats >= 0 && seats <= totalSeats) {
            availableSeats = seats;
        }
    }

    public void displayTrainDetails() {

        System.out.println("Train Number: " + trainNumber);
        System.out.println("Train Name: " + trainName);
        System.out.println("Route: " + source + " -> " + destination);
        System.out.println("Total Seats: " + totalSeats);
        System.out.println("Available Seats: " + availableSeats);
    }
}
