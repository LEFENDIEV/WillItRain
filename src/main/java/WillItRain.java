/**
 * @author        : LEFENDIEV
 * Creation date : 1/11/2019
 *
 * */

import Controller.UserDAO;

import classes.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONObject;
import spark.utils.IOUtils;


import java.io.InputStream;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.List;

import static spark.Spark.*;


public class WillItRain {

    public static void main(String[] args) {
        try {
            System.out.println("Spark has ignited !");
            port(4567);
            UserDAO dbConnection = new UserDAO("jdbc:mysql://mysql:3306/sparkdb", "root", "pass");
            get("/", (req, res) -> {
                return "Default endpoint";
            });

            //Return the user with the specified id
            get("/user/:id", (req, res) -> {
                String dbReturn = "";
                try {
                    dbReturn = dbConnection.getUser(req.params(":id")).toString();
                }catch (Exception e){
                    System.out.println("getUser"+e);
                }finally {
                    return "return get = " + dbReturn;
                }
            });
            //Create an user with the data of POST
            post("/create", (request , response) -> {
                try {
                    response.type("application/json");
                    User user = new Gson().fromJson(request.body(), User.class);
                    dbConnection.addUser(user);
                }catch (Exception e){
                    System.out.println("postRequestCreate"+e);
                }finally {
                    return "create user";
                }
            });
            //Delete the user with the specified id
            delete("/user/:id", (request , response) -> {
                try {
                    dbConnection.deleteUser(request.params(":id"));
                }catch (Exception e){
                    System.out.println("deleteUser"+e);
                }finally {
                    return "Delete user";
                }
            });
            //Modify the user with the specified id using POST data
            put("/user/:id", (request , response) -> {
                try {
                    Gson gson             = new Gson();
                    Type typeListAttr     = new TypeToken<List<String>>(){}.getType();
                    List<String> listAttr = gson.fromJson(request.body(), typeListAttr);
                    dbConnection.editUser(request.params(":id"), listAttr.get(0));
                }catch (Exception e){
                    System.out.println("updateUser"+e);
                }finally {
                    return "Update user";
                }
            });
            //Return if it is raining in the specified location
            get("/user/:id/willitrain/:location", (request, response) -> {
                URL url        = new URL("https://api.you-weather.com/get/data/en?c="+request.params(":location"));
                InputStream is = url.openStream();
                String result  = "";
                try {
                    StringWriter writer = new StringWriter();
                    IOUtils.copy(is, writer);
                    String jsonContent  = writer.toString();
                    JSONObject obj      = new JSONObject(jsonContent);
                    JSONObject location = obj.getJSONObject("data").getJSONArray("nearest_area").getJSONObject(0);

                    result += "You are in "+location.getJSONArray("areaName").getJSONObject(0).getString("value");
                    result += " located in "+location.getJSONArray("country").getJSONObject(0).getString("value");
                    result += " and today it is "+obj.getJSONObject("data").getJSONArray("current_condition").getJSONObject(0).getJSONArray("weatherDesc").getJSONObject(0).getString("value")+" !";
                } catch (Exception e) {
                    System.out.println("setLocation " + e);
                }
                return result;
            });
            //Return if it is raining in the specified city using a redirection of the previous endpoint
            get("/user/:id/willitrain/:city", (request, response) -> {
                URL url        = new URL("https://api.you-weather.com/get/suggest/en?q="+request.params(":city"));
                InputStream is = url.openStream();
                try {
                    StringWriter writer = new StringWriter();
                    IOUtils.copy(is, writer);
                    String jsonContent  = writer.toString();
                    JSONObject obj      = new JSONObject(jsonContent);
                    String location     = obj.getJSONArray("data").getString(0);
                    redirect.get("/user/:id/willitrain/"+location, "/user/:id/willitrain/:city");
                } catch (Exception e) {
                    System.out.println("setLocation " + e);
                }
                return "redirection";
            });

        }catch(Exception e){
            System.out.println("hello"+e);
        }
    }
}
