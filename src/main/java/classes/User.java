/**
 * @author        : LEFENDIEV
 * Creation date : 1/11/2019
 *
 * */

package classes;


public class User  {
    private int id;
    private String login;
    private String password;
    private String salt;

    /**
     * Constructor
     * @param id int
     * @param login String
     * */
    public User(int id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    /**
     * Constructor - no param
     * */
    public User() {

    }
    /**
     * Constructor only in use of LocationDAO::getLocation()
     * */
    public User(int id) {
        this.id = id;
    }

    /**
     * Translate a User to a String
     * */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                '}';
    }

    /**
     * Getters and Setters for a User
     * */

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
