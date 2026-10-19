
package railwayreservation;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReportService {

    private final RailwayService service;
    private final FileHandler fileHandler;

    public ReportService(RailwayService service, FileHandler handler) {
        this.service = service;
        this.fileHandler = handler;
    }

    public void generateReport() {

        System.out.println("\n===== SYSTEM REPORT =====");
        System.out.println("Total Trains: " + service.getTotalTrains());
        System.out.println("Total Bookings: " + service.getTotalBookings());
        System.out.println("Total Records: " + service.getTotalRecords());

        try {
            String timestamp = LocalDateTime.now().format(
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            fileHandler.saveHistory(
                    timestamp + " | Generated system report");

        } catch (IOException e) {
            System.out.println(
                    "Could not save operation history: " + e.getMessage());
        }
    }
}
