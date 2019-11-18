/**
 * @author        : LEFENDIEV
 * Creation date : 1/11/2019
 *
 * */

package classes;
import classes.User;



public class Location {
    private int id;
    private double longitude;
    private double latitude;
    private User user;



    /**
     * Constructor
     * @param id int
     * @param longitude double
     * @param latitude double
     * @param user User
     * */
    public Location(int id, double longitude, double latitude, User user) {
        this.id        = id;
        this.longitude = longitude;
        this.latitude  = latitude;
        this.user      = user;
    }

    /**
     * Constructor - no param
     * */
    public Location() {
    }

    /**
     * Translate a Location to a double
     * */
    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", id_user='" + user.getId() + '\'' +
                '}';
    }

    /**
     * Getters and Setters for a Location
     * */

    public int getId() {
        return id;
    }

    public void setId(int id) {
         this.id = id;
    }
    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
