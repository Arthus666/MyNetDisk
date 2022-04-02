package zm.pojo;

/**
 * @author Arthus
 */
public class User {

    private int id;
    private String username;
    private String password;
    private int is_admin;
    private String email;

    public User(int id, String username, String password, int is_admin, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.is_admin = is_admin;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIs_admin() {
        return is_admin;
    }

    public void setIs_admin(int is_admin) {
        this.is_admin = is_admin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", is_admin=" + is_admin +
                ", email='" + email + '\'' +
                '}';
    }
}
