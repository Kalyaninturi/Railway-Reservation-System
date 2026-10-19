
package railwayreservation;

public class User implements Authenticatable {

    private final String username;
    private final String password;
    private final String role;

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public String getPasswordForStorage() {
        return password;
    }

    @Override
    public boolean login(String username, String password) {
        return this.username.equals(username)
                && this.password.equals(password);
    }
}
