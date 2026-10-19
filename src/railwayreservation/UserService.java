
package railwayreservation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserService {

    private final List<User> users = new ArrayList<>();
    private final FileHandler fileHandler = new FileHandler();

    public UserService() {

        loadUsers();

        if (users.isEmpty()) {
            users.add(new User("user1", "user123", "USER"));
            saveUsers();
        }
    }

    public boolean registerUser(String username, String password) {

        if (username == null || username.trim().isEmpty()
                || password == null || password.trim().isEmpty()) {

            System.out.println("Username and password are required.");
            return false;
        }

        for (User user : users) {

            if (user.getUsername().equalsIgnoreCase(username.trim())) {
                System.out.println("Username already exists.");
                return false;
            }
        }

        users.add(new User(username.trim(), password, "USER"));

        if (saveUsers()) {
            System.out.println("Registration successful!");
            return true;
        }

        users.remove(users.size() - 1);

        System.out.println(
                "Registration failed. User could not be saved.");

        return false;
    }

    public User loginUser(String username, String password) {

        for (User user : users) {

            if (user.login(username, password)) {
                System.out.println("Login successful!");
                return user;
            }
        }

        System.out.println("Invalid username or password.");
        return null;
    }

    private boolean saveUsers() {

        List<String> lines = new ArrayList<>();

        for (User user : users) {
            lines.add(
                    user.getUsername() + "|"
                    + user.getPasswordForStorage() + "|"
                    + user.getRole()
            );
        }

        try {
            fileHandler.saveData("users.txt", lines);
            return true;

        } catch (IOException e) {
            System.out.println(
                    "Error saving users: " + e.getMessage());
            return false;
        }
    }

    private void loadUsers() {

        try {
            for (String line : fileHandler.loadData("users.txt")) {

                String[] p = line.split("\\|", -1);

                if (p.length == 3 && !p[0].isBlank()) {
                    users.add(new User(p[0], p[1], p[2]));
                }
            }

        } catch (IOException e) {
            System.out.println(
                    "Error loading users: " + e.getMessage());
        }
    }
}
