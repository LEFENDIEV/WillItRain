/**
 * @author        : LEFENDIEV
 * Creation date : 1/11/2019
 *
 * */
package Controller;

import tools.JDBCHelper;
import classes.User;
import java.sql.*;

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
        String preQuery        = "insert into users (id, name) values (? , ?)";
        PreparedStatement stmt = null;
        try {
            stmt = this.db.connect().prepareStatement(preQuery);
            stmt.setInt(1, user.getId());
            stmt.setString(2, user.getName());
            stmt.executeUpdate();
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
        User user              = new User();
        String preQuery        = "select * from users where id= ?";
        PreparedStatement stmt = null;
        ResultSet res          = null;
        try {
            stmt = this.db.connect().prepareStatement(preQuery);
            stmt.setInt(1, id);
            res = stmt.executeQuery();
            res.next();
            user.setId(Integer.parseInt(res.getString("id")));
            user.setName(res.getString("name"));
        }catch (Exception e){
            System.out.println("getUser"+e);
        }finally {
            db.disconnect(stmt, res);
            return user;
        }
    }

    /**
     *
     * */
    public void deleteUser (int id){
        String preQuery        = "delete from users where id = ?";
        PreparedStatement stmt = null;
        try{
            stmt = this.db.connect().prepareStatement(preQuery);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }catch (Exception e){
            System.out.println("deleteUser"+e);
        }finally {
            db.disconnect(stmt);
        }
    }

    /**
     * Edit the user having the same id as in parameter with the other parameters
     * @param id   int
     * @param name String
     * */
    public void editUser (int id, String name){
        String preQuery        = "update users set name = ? where id=?";
        PreparedStatement stmt = null;
        try {
            stmt = this.db.connect().prepareStatement(preQuery);
            stmt.setInt(1, id);
            stmt.setString(2, name);
            stmt.executeUpdate();
        }catch (Exception e){
            System.out.println("updateUser"+e);
        }finally {
            db.disconnect(stmt);
        }
    }
}
