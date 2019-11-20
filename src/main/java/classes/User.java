/**
 * @author        : LEFENDIEV
 * Creation date : 1/11/2019
 *
 * */

package classes;


public class User  {
    private int id;
    private String name;
    private String password;

    /**
     * Constructor
     * @param id int
     * @param name String
     * */
    public User(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password =  password;
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
                ", name='" + name + '\'' +
                '}';
    }
    /**
     * Getters and Setters for a User
     * */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
