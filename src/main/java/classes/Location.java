/**
 * @author        : LEFENDIEV
 * Creation date : 1/11/2019
 *
 * */

package location;

public class Location {
    private int id;
    private String longitude;
    private String latitude;
    private User user;



    /**
     * Constructor
     * @param id int
     * @param longitude String
     * @param latitude String
     * */
    public Location(int id, String longitude, String latitude) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    /**
     * Constructor - no param
     * */
    public Location() {
    }

    /**
     * Translate a Location to a String
     * */
    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitudee + '\'' +
                '}';
    }
    /**
     * Getters and Setters for a Location
     * */
    public int getId() {
        return id;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
    public String getLongitude() {
        return latitude;
    }

    public void setLongitude(String latitude) {
        this.latitude = latitude;
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
