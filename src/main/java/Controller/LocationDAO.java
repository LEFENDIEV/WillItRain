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
        String preQuery        = "insert into location (id, longitude, latitude, formatted_adress, id_user) values (?, ?, ?, ?, ?)";
        PreparedStatement stmt = null;
        try {
            stmt = this.db.connect().prepareStatement(preQuery);
            stmt.setInt(1, location.getId());
            stmt.setDouble(2, location.getLongitude());
            stmt.setDouble(3, location.getLatitude());
            stmt.setString(4, location.getFormattedAdress());
            stmt.setInt(5, location.getUser().getId());
            stmt.executeUpdate();
        }catch (SQLException e){
            System.out.println("SQL Exception = "+e);
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
            location.setId(res.getInt(0));
            location.setLongitude(res.getDouble(1));
            location.setLatitude(res.getDouble(2));
            location.setFormattedAdress(res.getString(3));
            location.setUser(new User(res.getInt(4)));
        }catch (SQLException e){
            System.out.println("SQL Exception = "+e);
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
        }catch (SQLException e){
            System.out.println("SQL Exception = "+e);
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
        String preQuery        = "update location set longitude = ?, latitude = ?, formatted_adress = ? where id=? and id_user=?";
        PreparedStatement stmt = null;
        try {
            stmt = this.db.connect().prepareStatement(preQuery);
            stmt.setDouble(1, location.getLongitude());
            stmt.setDouble(2, location.getLatitude());
            stmt.setString(3, location.getFormattedAdress());
            stmt.setInt(4, location.getId());
            stmt.setInt(5, location.getUser().getId());
            stmt.executeUpdate();
        }catch (SQLException e){
            System.out.println("SQL Exception = "+e);
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
        }catch (SQLException e){
            System.out.println("SQL Exception = "+e);
        }catch (Exception e){
            System.out.println("getNbLocation"+e);
        }finally {
            db.disconnect(stmt);
            return nbLocation;
        }
    }

}
