/**
 * @author        : LEFENDIEV
 * Creation date : 18/11/2019
 *
 * */

package Controller;

import tools.JDBCHelper;
import classes.Location;
import classes.User;
import java.sql.*;

/**
 * DAO for the Locations using jdbc and mysql
 * */
public class  LocationDAO {
    private JDBCHelper db;
    /**
     * Constructor class
     * @param url      String
     * @param username String
     * @param password String
     *
     * */
    public LocationDAO(String url, String username, String password) {
        this.db = new JDBCHelper(url, username, password);
    }

    /**
     * Add the specified locations to the location table
     * @param location Location
     *
     * */
    public void addLocation (Location location){
        String preQuery        = "insert into location (id, longitude, latitude, id_user) values (?, ?, ?, ?)";
        PreparedStatement stmt = null;
        try {
            stmt = this.db.connect().prepareStatement(preQuery);
            stmt.setInt(1, location.getId());
            stmt.setDouble(2, location.getLongitude());
            stmt.setDouble(3, location.getLatitude());
            stmt.setInt(4, location.getUser().getId());
            stmt.executeUpdate();
        }catch (Exception e){
            System.out.println("addLocation"+e);
        }finally {
            db.disconnect(stmt);
        }
    }

    /**
     * Get the location from table location using specified id
     * @param id int
     * @return Returns a Location object
     * */
    public Location getLocation (int id){
        Location location              = new Location();
        String preQuery        = "select * from location where id= ?";
        PreparedStatement stmt = null;
        ResultSet res          = null;
        try {
            stmt = this.db.connect().prepareStatement(preQuery);
            stmt.setInt(1, id);
            res = stmt.executeQuery();
            res.next();
            location.setId(res.getInt("id"));
            location.setLongitude(res.getDouble("longitude"));
            location.setLatitude(res.getDouble("latitude"));
            location.setUser(new User(res.getInt("id_user")));
        }catch (Exception e){
            System.out.println("getLocation"+e);
        }finally {
            db.disconnect(stmt, res);
            System.out.println(location);
            return location;
        }
    }

    /**
     * Delete a user from table users using specified id
     * @param id int
     * */
    public void deleteLocation (int id){
        String preQuery        = "delete from location where id = ?";
        PreparedStatement stmt = null;
        try{
            stmt = this.db.connect().prepareStatement(preQuery);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }catch (Exception e){
            System.out.println("deleteLocation"+e);
        }finally {
            db.disconnect(stmt);
        }
    }

    /**
     * Edit the location in the
     * @param location Location
     * */
    public void editLocation (Location location){
        String preQuery        = "update location set longitude = ?, latitude = ? where id=? and id_user=?";
        PreparedStatement stmt = null;
        try {
            stmt = this.db.connect().prepareStatement(preQuery);
            stmt.setDouble(1, location.getLongitude());
            stmt.setDouble(2, location.getLatitude());
            stmt.setInt(3, location.getId());
            stmt.setInt(4, location.getUser().getId());
            stmt.executeUpdate();
        }catch (Exception e){
            System.out.println("updateLocation"+e);
        }finally {
            db.disconnect(stmt);
        }
    }

    /**
     * Return the amount of locations of a User using his id
     * */
    public int getNbLocation (int id){
        String preQuery        = "select count(*) from location where id_user=?";
        PreparedStatement stmt = null;
        ResultSet res          = null;
        int nbLocation=0;
        try {
            stmt = this.db.connect().prepareStatement(preQuery);
            stmt.setInt(1,id);
            res = stmt.executeQuery();
            res.next();
            nbLocation = res.getInt(1);
        }catch (Exception e){
            System.out.println("getNbLocation"+e);
        }finally {
            db.disconnect(stmt);
            return nbLocation;
        }
    }

}
