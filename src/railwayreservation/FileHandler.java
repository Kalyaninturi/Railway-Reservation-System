
package railwayreservation;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    private final Path directory = Paths.get("data");

    // Create the data folder
    public FileHandler() {
        try {
            Files.createDirectories(directory);
        } catch (IOException e) {
            System.out.println(
                    "Could not create data folder: " + e.getMessage());
        }
    }

    // Save data to a file
    public void saveData(String name, List<String> lines)
            throws IOException {

        Files.createDirectories(directory);

        Files.write(
                directory.resolve(name),
                lines,
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING
        );
    }

    // Load data from a file
    public List<String> loadData(String name) throws IOException {

        Path path = directory.resolve(name);

        if (!Files.exists(path)) {
            return new ArrayList<>();
        }

        return Files.readAllLines(path, StandardCharsets.UTF_8);
    }

    // Append data without deleting existing content
    public void appendData(String name, String line)
            throws IOException {

        Files.createDirectories(directory);

        Files.write(
                directory.resolve(name),
                List.of(line),
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
        );
    }

    // Save operation history
    public void saveHistory(String line) throws IOException {
        appendData("history.txt", line);
    }
}
