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
    private transient User user;
    private  String formattedAdress;



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

    public Location(int id, double longitude, double latitude, User user, String formattedAdress) {
        this.id        = id;
        this.longitude = longitude;
        this.latitude  = latitude;
        this.user      = user;
        this.formattedAdress    = formattedAdress;
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
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", user=" + user +
                ", formattedAdress='" + formattedAdress + '\'' +
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

    public String getFormattedAdress() {
        return formattedAdress;
    }

    public void setFormattedAdress(String formattedAdress) {
        this.formattedAdress = formattedAdress;
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
