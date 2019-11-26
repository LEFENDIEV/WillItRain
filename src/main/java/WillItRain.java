/**
 * @author        : LEFENDIEV
 * Creation date : 1/11/2019
 *
 * */

import Controller.LocationDAO;
import Controller.UserDAO;
import classes.Location;
import classes.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONObject;
import spark.utils.IOUtils;
import tools.SparkJWTHelper;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.InputStream;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.net.URL;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.*;

import static spark.Spark.*;


public class WillItRain {

    public static void main(String[] args) {
        try {
            System.out.println("Spark has ignited !");
            port(4567);
            UserDAO userDAO         = new UserDAO("jdbc:mysql://mysql:3306/sparkdb", "root", "pass");
            LocationDAO locationDAO = new LocationDAO("jdbc:mysql://mysql:3306/sparkdb", "root", "pass");
            SparkJWTHelper sparkJWTHelper = new SparkJWTHelper();
            //This hashmap allow to have a quick view of all endpoints, and allow quick changes to them
            Map<String, String> endpointList = new HashMap<String, String>() {{
                put("returnUserById", "/api/user/:id");
                put("registration", "/api/register");
                put("loging", "/api/loging");
                put("deleteUserById", "/api/user/:id");
                put("modifyUserById", "/api/user/:id");
                put("getWeatherByLocation", "/api/user/:id/willitrain/:location");
                put("getWeatherByCity", "/api/user/:id/willitrain/:city");
                put("getFrontPageMap", "/api/frontpage");
                put("setLocation", "/api/frontpage/location");
            }};
            //start of endpoints code
            get("/", (req, res) -> {
                return "Default endpoint";
            });
            //start of endpoints code
            options("/*", (req, res) -> {
                return "";
            });
            before((request, response) -> {
                //       /!\ the value * should be allowed in dev process only
                response.header("Access-Control-Allow-Origin ", "*");
                response.header("Access-Control-Allow-Methods ", "*");
                response.header("Access-Control-Allow-Headers", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin,");
                response.header("Access-Control-Allow-Credentials", "true");
            });
            //Return the user with the specified id
            get(endpointList.get("returnUserById"), (request, response) -> {
                String dbReturn = "";
                try {
                    if(request.cookie("user-token")!=null){
                        String token = request.cookie("user-token");
                        String salt = request.cookie("user-token-salt");
                        User user = userDAO.getUserByLogin(request.cookie("getLoginPage"));
                        if(sparkJWTHelper.isWillItRainTokenValid(token, salt,  user)){
                            dbReturn = userDAO.getUser(Integer.parseInt(request.params(":id"))).toString();
                            response.status(200);
                        }else{
                            response.status(401);
                        }
                    }else{
                        response.status(301);
                    }
                }catch (Exception e){
                    System.out.println("getUser"+e);
                }finally {
                    return "return get = " + dbReturn;
                }
            });
            //Allow the user to create a token for his session and redirect to front page
            post(endpointList.get("loging"), ((request, response) -> {
                String token;
                User user = new User();
                String finalMessage = "loging";
                try{
                    if(request.cookie("user-token")!=null){
                        token = request.cookie("user-token");
                        String salt = request.cookie("user-token-salt");
                        user = userDAO.getUserByLogin(request.cookie("login"));
                        if(sparkJWTHelper.isWillItRainTokenValid(token, salt,  user)){
                            response.status(401);
                        }else{
                            response.status(401);
                        }
                    }else{
                        //the data is fetched from the request.body and a dummy user is created to get the data easily
                        ArrayList<String> tokenInfo = new ArrayList<>();
                        User requestUser = new Gson().fromJson(request.body(), User.class);
                        String hashedPassword;
                        user = userDAO.getUserByLogin(requestUser.getLogin());
                        //creation of the test password with the dummy user password and the user salt
                        KeySpec spec = new PBEKeySpec(requestUser.getPassword().toCharArray(),Base64.getDecoder().decode(user.getSalt()), 65536, 128);
                        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
                        byte[] encodedPassword = factory.generateSecret(spec).getEncoded();
                        hashedPassword = Base64.getEncoder().encodeToString(encodedPassword);
                        if(user.getPassword().equals(hashedPassword)){
                            tokenInfo = sparkJWTHelper.generateJWTWillItRain(user);
                            response.cookie("getLoginPage", user.getLogin(),3600 );
                            response.cookie("user-token", tokenInfo.get(0),3600,  false, true);
                            response.cookie("user-token-salt", tokenInfo.get(1),3600,  false, true);
                            response.redirect(endpointList.get("getFrontPageMap"));

                        }else{
                            finalMessage = "not getLoginPageg";
                            response.status(401);
                        }
                    }
                }catch (Exception e){
                    System.out.println("getLoginPageg"+e);
                }
                return finalMessage;
            }));
            //Create an user with the data of POST
            post(endpointList.get("registration"), (request , response) -> {
                String token = "";
                String finalMessage = "registration";
                try {
                    response.type("application/json");
                    User user = new Gson().fromJson(request.body(), User.class);
                    if(user.getPassword().length()>5){
                        if (userDAO.checkLoginAvailable(user.getLogin())) {
                            //creation of the hashed password + salt
                            ArrayList<String> tokenInfo = new ArrayList<>();
                            String hashedPassword;
                            String stringSalt;
                            SecureRandom random = new SecureRandom();
                            byte[] salt = new byte[128];
                            random.nextBytes(salt);
                            KeySpec spec = new PBEKeySpec(user.getPassword().toCharArray(), salt, 65536, 128);
                            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
                            byte[] encodedPassword = factory.generateSecret(spec).getEncoded();
                            hashedPassword = Base64.getEncoder().encodeToString(encodedPassword);
                            //user final setup
                            stringSalt = Base64.getEncoder().encodeToString(salt);
                            user.setPassword(hashedPassword);
                            user.setSalt(stringSalt);
                            userDAO.addUser(user);
                            //token creation
                            tokenInfo= sparkJWTHelper.generateJWTWillItRain(user);
                            response.cookie("user-token", tokenInfo.get(0),3600,  false, true);
                            response.cookie("user-token-salt", tokenInfo.get(1),3600,  false, true);
                            response.cookie("login", user.getLogin(), 3600);
                            response.redirect(endpointList.get("getFrontPageMap"));
                        }else{
                            finalMessage = "login taken";
                        }
                    }else{
                        finalMessage = "password too short";
                    }
                }catch (Exception e){
                    System.out.println("postRequestCreate"+e);
                }finally {
                    return finalMessage;
                }
            });
            //Delete the user with the specified id
            delete(endpointList.get("deleteUserById"), (request , response) -> {
                try {
                    if(request.cookie("user-token")!=null){
                        String token = request.cookie("user-token");
                        String salt = request.cookie("user-token-salt");
                        User user = userDAO.getUserByLogin(request.cookie("login"));
                        if(sparkJWTHelper.isWillItRainTokenValid(token, salt,  user)){
                            userDAO.deleteUser(Integer.parseInt(request.params(":id")));
                            response.status(200);
                        }else{
                            response.status(401);
                        }
                    }else{
                        response.status(401);
                    }
                }catch (Exception e){
                    System.out.println("deleteUser"+e);
                }finally {
                    return "Delete user";
                }
            });
            //Modify the user with the specified id using POST data
            put(endpointList.get("modifyUserById"), (request , response) -> {
                try {
                    if(request.cookie("user-token")!=null){
                        String token = request.cookie("user-token");
                        String salt = request.cookie("user-token-salt");
                        User user = userDAO.getUserByLogin(request.cookie("login"));
                        if(sparkJWTHelper.isWillItRainTokenValid(token, salt,  user)){
                            Gson gson             = new Gson();
                            Type typeListAttr     = new TypeToken<List<String>>(){}.getType();
                            List<String> listAttr = gson.fromJson(request.body(), typeListAttr);
                            userDAO.editUser(Integer.parseInt(request.params(":id")), listAttr.get(0), listAttr.get(1), listAttr.get(2));
                            response.status(200);
                        }else{
                            response.status(401);
                        }
                    }else{
                        response.status(401);
                    }
                }catch (Exception e){
                    System.out.println("updateUser"+e);
                }finally {
                    return "Update user";
                }
            });
            //Return if it is raining in the specified location
            get(endpointList.get("getWeatherByLocation"), (request, response) -> {
                URL url        = new URL("https://api.you-weather.com/get/data/en?c="+request.params(":location"));
                InputStream is = url.openStream();
                String result  = "";
                try {
                    if(request.cookie("user-token")!=null){
                        String token = request.cookie("user-token");
                        String salt = request.cookie("user-token-salt");
                        User user = userDAO.getUserByLogin(request.cookie("login"));
                        if(sparkJWTHelper.isWillItRainTokenValid(token, salt,  user)){
                            StringWriter writer = new StringWriter();
                            IOUtils.copy(is, writer);
                            String jsonContent  = writer.toString();
                            JSONObject obj      = new JSONObject(jsonContent);
                            JSONObject location = obj.getJSONObject("data").getJSONArray("nearest_area").getJSONObject(0);

                            result += "You are in "+location.getJSONArray("areaName").getJSONObject(0).getString("value");
                            result += " located in "+location.getJSONArray("country").getJSONObject(0).getString("value");
                            result += " and today it is "+obj.getJSONObject("data").getJSONArray("current_condition").getJSONObject(0).getJSONArray("weatherDesc").getJSONObject(0).getString("value")+" !";
                            response.status(200);
                        }else{
                            response.status(401);
                        }
                    }else{
                        response.status(401);
                    }
                } catch (Exception e) {
                    System.out.println("willItRainLocation" + e);
                }
                return result;
            });
            //Return if it is raining in the specified city using a redirection of the previous endpoint
            get(endpointList.get("getWeatherByCity"), (request, response) -> {
                URL url        = new URL("https://api.you-weather.com/get/suggest/en?q="+request.params(":city"));
                InputStream is = url.openStream();
                try {
                    if(request.cookie("user-token")!=null){
                        StringWriter writer = new StringWriter();
                        String token = request.cookie("user-token");
                        String salt = request.cookie("user-token-salt");
                        User user = userDAO.getUserByLogin(request.cookie("login:w" +
                                ""));
                        if(sparkJWTHelper.isWillItRainTokenValid(token, salt,  user)){
                            IOUtils.copy(is, writer);
                            String jsonContent  = writer.toString();
                            JSONObject obj      = new JSONObject(jsonContent);
                            String location     = obj.getJSONArray("data").getString(0);

                            redirect.get("/user/:id/willitrain/"+location, "/user/:id/willitrain/:city");
                        }else{
                            response.status(401);
                        }
                    }else{
                        response.status(401);
                    }
                } catch (Exception e) {
                    System.out.println("willItRainCity" + e);
                }
                return "redirection";
            });
            //Redirect to the front page where the map is displayed
            get(endpointList.get("getFrontPageMap"), ((request, response) ->{
                try {
                    if(request.cookie("user-token")!=null){
                        String token = request.cookie("user-token");
                        String salt = request.cookie("user-token-salt");
                        User user = userDAO.getUserByLogin(request.cookie("login"));
                        if(sparkJWTHelper.isWillItRainTokenValid(token, salt,  user)){
                            ArrayList locationList = userDAO.getLocationOfUser(user.getId());
                            Gson gson = new Gson();
                            Type type = new TypeToken<ArrayList<Location>>() {}.getType();
                            String json = gson.toJson(locationList, type);
                            response.status(200);
                        }else{
                            response.status(401);
                        }
                    }else{
                        response.status(401);
                    }
                }catch(Exception e){
                    System.out.println("getFrontPageMap" + e);
                }
                finally {
                    return "frontpage";
                }
            } ));
        }catch(Exception e){
            System.out.println("getFrontPageMap"+e);
        }
    }
}
