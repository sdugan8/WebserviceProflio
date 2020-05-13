
package userverify;

/**
 *
 * @author Godich
 */
public class strip {
    public String username;
    public String role;

    public strip() {
    }
    public strip(String username, String role) {
        this.username = username;
        this.role = role;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
