/**
 * @author        : LEFENDIEV
 * Creation date : 1/11/2019
 *
 * */

package Controller;

import classes.Location;
import tools.JDBCHelper;
import classes.User;
import java.sql.*;
import java.util.ArrayList;

/**
 * DAO for the Users using jdbc and mysql
 * */
public class  UserDAO {
    private JDBCHelper db;
    /**
     * Constructor class
     * @param url      String
     * @param username String
     * @param password String
     *
     * */
    public UserDAO(String url, String username, String password) {
        this.db = new JDBCHelper(url, username, password);
    }

    /**
     * Add the specified user to the users table
     * @param user User
     *
     * */
    public void addUser (User user){
        String preQuery        = "insert into users (login, password, salt) values (?, ?, ?)";
        PreparedStatement stmt = null;
        try {
            stmt = this.db.connect().prepareStatement(preQuery);
            stmt.setString(1, user.getLogin());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getSalt());
            System.out.println(user.toString());
            stmt.executeUpdate();
        }catch (SQLException e){
            System.out.println("SQL Exception = "+e);
        }catch (Exception e){
            System.out.println("addUser"+e);
        }finally {
            db.disconnect(stmt);
        }
    }

    /**
     * Get the user from table users using specified id
     * @param id int
     * @return Returns a User object
     * */
    public User getUser (int id){
        User user              = null;
        String preQuery        = "select * from users where id= ?";
        PreparedStatement stmt = null;
        ResultSet res          = null;
        try {
            stmt = this.db.connect().prepareStatement(preQuery);
            stmt.setInt(1, id);
            res = stmt.executeQuery();
            res.next();
           if(res!=null){
               user.setId(Integer.parseInt(res.getString("id")));
               user.setLogin(res.getString("login"));
               user.setPassword(res.getString("password"));
               user.setSalt(res.getString("salt"));
           }
        }catch (SQLException e){
            System.out.println("SQL Exception = "+e);
        }catch (Exception e){
            System.out.println("getUser"+e);
        }finally {
            db.disconnect(stmt, res);
            return user;
        }
    }

    /**
     * Delete a user from table users using specified id
     * @param id int
     * */
    public void deleteUser (int id){
        String preQuery        = "delete from users where id = ?";
        PreparedStatement stmt = null;
        try{
            stmt = this.db.connect().prepareStatement(preQuery);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }catch (SQLException e){
            System.out.println("SQL Exception = "+e);
        }catch (Exception e){
            System.out.println("deleteUser"+e);
        }finally {
            db.disconnect(stmt);
        }
    }

    /**
     * Edit the user having the same id as in parameter with the other parameters
     * @param id   int
     * @param login String
     * */
    public void editUser (int id, String login, String password, String salt){
        String preQuery        = "update users set login = ? and password= ? and salt = ? where id=?";
        PreparedStatement stmt = null;
        try {
            stmt = this.db.connect().prepareStatement(preQuery);
            stmt.setString(1, login);
            stmt.setString(2, password);
            stmt.setString(3, salt);
            stmt.setInt(4, id);
            stmt.executeUpdate();
        }catch (SQLException e){
            System.out.println("SQL Exception = "+e);
        }catch (Exception e){
            System.out.println("updateUser"+e);
        }finally {
            db.disconnect(stmt);
        }
    }

    /**
     * Look in the database if the login has been taken by a user
     * @param login String
     * */
    public boolean checkLoginAvailable(String login){
        boolean isAvailable = false;
        String preQuery        = "Select count(*) from users where login=?";
        ResultSet res          = null;
        PreparedStatement stmt = null;
        int nbLogin;
        try{
            stmt = this.db.connect().prepareStatement(preQuery);
            stmt.setString(1,login);
            res = stmt.executeQuery();
            res.next();
            nbLogin = res.getInt(1);
            if(nbLogin==0){
                isAvailable = true;
            }
        }catch (SQLException e){
            System.out.println("SQL Exception = "+e);
        }catch (Exception e){
            System.out.println("checkLoginAvailable");
        }
        return isAvailable;
    }

    /**
     * Because a login is unique to a user, we can get all his data with it
     * @param login String
     * */
    public User getUserByLogin(String login){
        User user = null;
        String preQuery        = "Select * from users where login=?";
        ResultSet res          = null;
        PreparedStatement stmt = null;
        int nbLogin;
        try{
            stmt = this.db.connect().prepareStatement(preQuery);
            stmt.setString(1,login);
            res = stmt.executeQuery();
            if(res != null) {
            res.next();
                user = new User();
                user.setId(res.getInt(1));
                user.setLogin(res.getString(2));
                user.setPassword(res.getString(3));
                user.setSalt(res.getString(4));
            }
        }catch (SQLException e){
            System.out.println("SQL Exception = "+e);
        }catch (Exception e){
            System.out.println("getUserByLogin"+e);
        }
        return user;
    }

    /**
     * Get the number of users from the Database
     * */
    public int getNbOfUser(){
        String preQuery        = "Select count(*) from users";
        ResultSet res          = null;
        PreparedStatement stmt = null;
        int nbUser = 0;
        try{
            stmt = this.db.connect().prepareStatement(preQuery);
            res = stmt.executeQuery();
            res.next();

            nbUser = res.getInt(1);
        }catch (SQLException e){
            System.out.println("SQL Exception = "+e);
        }catch (Exception e){
            System.out.println("getNbUser"+e);
        }
        return nbUser;
    }

    /**
     * Get all the locations from a user with his id
     * @param id int
     * */
    public ArrayList<Location> getLocationOfUser(int id){
        String preQuery        = "Select * from location where id_user=?";
        ResultSet res          = null;
        PreparedStatement stmt = null;
        int nbUser = 0;
        ArrayList<Location> locationListOfUser = new ArrayList<>();
        Location currentLocation = new Location();
        try{

            stmt = this.db.connect().prepareStatement(preQuery);
            stmt.setInt(1, id);
            res = stmt.executeQuery();
            do{
                res.next();
                currentLocation.setId(res.getInt(1));
                currentLocation.setLongitude(res.getFloat(2));
                currentLocation.setLatitude(res.getFloat(3));
                currentLocation.setFormattedAdress(res.getString(4));
                currentLocation.setUser(new User(res.getInt(5)));
                System.out.println(currentLocation.toString());
                locationListOfUser.add(currentLocation);
            }while(res.isLast());
        }catch (SQLException e){
            System.out.println("SQL Exception = "+e);
        }catch (Exception e){
            System.out.println("getLocationOfUser"+e);
        }
        return locationListOfUser;
    }
}
