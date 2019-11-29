/**
 * @author        : LEFENDIEV
 * Creation date : 21/11/2019
 *
 * */

package tools;


import classes.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.*;

public class SparkJWTHelper {

    /**
     * Constructor - no param
     * */
    public SparkJWTHelper() {
    }


    /**
     * Generate a token and its salt inside an Array, the token is also identified by the id of the user
     * @param user User
     * */
    public ArrayList<String> generateJWTWillItRain(User user){
        String token = "";
        ArrayList<String> tokenInfo = new ArrayList<>();
        try {
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[64];
            random.nextBytes(salt);
            Algorithm algorithmHS = Algorithm.HMAC256(salt);
            Map<String, Object> claimList = new HashMap<>();
            claimList.put("kid", salt);
            claimList.put("exp",  (System.currentTimeMillis()/1000)+3600);
            token = JWT.create()
                    .withHeader(claimList)
                    .withIssuer(user.getLogin())
                    .sign(algorithmHS);
            tokenInfo.add(token);
            tokenInfo.add(Base64.getEncoder().encodeToString(salt));
            System.out.println(JWT.decode(token));
        }catch (Exception e){
            System.out.println("generateJWTWillItRain"+e);
        }
        finally {
            return tokenInfo;
        }
    }

    /**
     * Verify the token using its unique salt and the id of the user
     * @param token String
     * @param salt String
     * @param user User
     * */
    public boolean isWillItRainTokenValid(String token, String salt, User user){
        boolean isValid = false;
        try{
            Algorithm algorithm = Algorithm.HMAC256(Base64.getDecoder().decode(salt));
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(user.getLogin())
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            if(jwt.getKeyId().equals(salt)){
                isValid = true;
            }
        }catch (JWTVerificationException e){
            System.out.println("isWillItRainTokenValid"+e);
        }
        catch (Exception e){
            System.out.println("isWillItRainValid"+e);
        }
        return isValid;
    }

}
