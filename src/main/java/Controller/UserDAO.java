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
    private JDBCHelper dbConnection;
    private Statement state = null;
    private ResultSet result = null;
    /**
     * Constructor class
     * @param url      String
     * @param username String
     * @param password String
     *
     * */
    public UserDAO(String url, String username, String password) {
        this.dbConnection = new JDBCHelper(url, username, password);
    }

    /**
     * Add the specified user to the users table
     * @param user User
     *
     * */
    public void addUser (User user){
        try {
            this.state = this.dbConnection.connect().createStatement();
            this.state.executeUpdate("insert into users (id, name) values ("+user.getId()+", '"+user.getName()+"')");
        }catch (Exception e){
            System.out.println("addUser"+e);
        }finally {
            dbConnection.disconnect(this.state);
        }
    }

    /**
     * Get the user from table users using specified id
     * @param id String
     * @return Returns a User object
     * */
    public User getUser (String id){
        User user = new User();
        try {
            this.state  = this.dbConnection.connect().createStatement();
            this.result = this.state.executeQuery("select * from users where id=" + id);
            System.out.println("select * from users where id=" + id);
            this.result.next();
            user.setId(Integer.parseInt(this.result.getString("id")));
            user.setName(this.result.getString("name"));
        }catch (Exception e){
            System.out.println("getUser"+e);
        }finally {
            dbConnection.disconnect(this.state, this.result);
            return user;
        }
    }

    /**
     * Delete a user from table users using specified id
     * @param id String
     *
     * */
    public void deleteUser (String id){
        try{
            this.state = this.dbConnection.connect().createStatement();
            this.state.execute("delete from users where id="+id);
            System.out.println("delete from users where id="+id);
        }catch (Exception e){
            System.out.println("deleteUser"+e);
        }finally {
            dbConnection.disconnect(this.state);
        }
    }

    /**
     * Edit the user having the same id as in parameter with the other parameters
     * @param id   String
     * @param name String
     * */
    public void editUser (String id, String name){
        try {
            this.state.execute("update users set name = '"+name+"' where id="+id);
        }catch (Exception e){
            System.out.println("updateUser"+e);
        }finally {
            dbConnection.disconnect(this.state);
        }
    }
}
