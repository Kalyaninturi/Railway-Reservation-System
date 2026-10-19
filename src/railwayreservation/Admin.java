package railwayreservation;

public class Admin implements Authenticatable {

    private final String username;
    private final String password;

    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public boolean login(String username, String password) {
        return this.username.equals(username)
                && this.password.equals(password);
    }

    public void displayAdminMenu() {
        System.out.println("\n===== ADMIN MENU =====");
        System.out.println("1. View Trains");
        System.out.println("2. View All Bookings");
        System.out.println("3. View All Records");
        System.out.println("4. Delete a Record");
        System.out.println("5. View Reports");
        System.out.println("6. Logout");
        System.out.print("Enter your choice: ");
    }
}