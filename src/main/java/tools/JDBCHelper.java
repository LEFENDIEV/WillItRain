/**
 * @author        : LEFENDIEV
 * Creation date : 1/11/2019
 *
 * */

package tools;

import java.sql.*;
/**
 * Tools for the DAOs using JDBC
 *
 * */
public class JDBCHelper {
    private Connection connection = null;
    private String url;
    private String username;
    private String password;

    /**
     * Constructor which set the connection attribute and will allow a connection
     * @param url String
     * @param username String
     * @param password String
     * */
    public JDBCHelper(String url, String username, String password){
        try{
            this.url = url;
            this.username = username;
            this.password = password;
        }catch (Exception e){
            System.out.println("userDAO"+e);
        }
    }

    /**
     * Allow the connection to mysql using JDBC
     * @return Connection
     * */
    public Connection connect(){
        try {
            if(connection.isClosed()){
                this.connection = DriverManager.getConnection(this.url, this.username, this.password);
            }
        }catch (Exception e){
            System.out.println("Connection "+e);
        }finally {
            return this.connection;
        }
    }

    /**
     * Disconnects the connection of the current JDBCHelper and following parameters
     * @param statement Statement
     * @param result ResultSet
     * */
    public void disconnect(Statement statement, ResultSet result){
        try{
            if(this.connection != null && statement != null && result != null){
                this.connection.close();
                statement.close();
                result.close();
            }
        }catch (Exception e){
            System.out.println("Disconnection "+e);
        }
    }

    /**
     * Disconnects the connection of the current JDBCHelper and following parameters
     * @param statement Statement
     * */
    public void disconnect(Statement statement){
        try{
            if(this.connection != null && statement != null ){
                this.connection.close();
                statement.close();
            }
        }catch (Exception e){
            System.out.println("Disconnection "+e);
        }
    }

    /**
     * Disconnects the connection of the current JDBCHelper and following parameters
     * */
    public void disconnect(){
        try{
            if(this.connection != null ){
                this.connection.close();
            }
        }catch (Exception e){
            System.out.println("Disconnection "+e);
        }
    }

}
