package org.unibl.etf.forum.forum_jwt_controller.services;

import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.crypto.MacProvider;
import io.jsonwebtoken.io.Decoder;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.unibl.etf.forum.forum_jwt_controller.dto.UserDTO;
import org.unibl.etf.forum.forum_jwt_controller.dto.responses.JwtTokenValidationResponse;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${authorization.token.secret}")
    public String authorizationSecret;



    private long expirationMillis = 1000 * 60 * 60 * 24;

    private Key getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(authorizationSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails){

        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode((authorizationSecret)));


        if (userDetails instanceof UserDTO){
            UserDTO user = (UserDTO) userDetails;
            extraClaims.put("group", user.getUserGroup());
        }


        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .addClaims(extraClaims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
                .signWith(secretKey)
                .compact();
    }

    public boolean isTokenValid(String token){

        boolean isValid = false;
        try{
            isValid = !isTokenExpired(token);
        }catch(JwtException e){
            isValid = false;
        }
        return isValid;
    }

    public JwtTokenValidationResponse checkTokenValidityAndExtractUserGroup(String token){
        JwtTokenValidationResponse response = new JwtTokenValidationResponse();
        try{
            response.setSuccessfull(isTokenValid(token));
            Integer userGroup = -1;

            userGroup = extractClaim(token, extractGroupClaim());
            String username = extractUsername(token);
            response.setUserGroup(userGroup);



            response.setUsername(username);
        }
        catch(JwtException e){
            response.setSuccessfull(false);
            return response;
        }



        return response;
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String extractUsername(String token) throws ExpiredJwtException {

        String username;
        try{
            username = extractClaim(token, Claims::getSubject);
        }
        catch(JwtException e){
            username = null;
        }
        return username;
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token){
        //promijenio
        return Jwts.parserBuilder().setSigningKey(Decoders.BASE64.decode((authorizationSecret))).build().parseClaimsJws(token).getBody();
    }

    public Function<Claims, Integer> extractGroupClaim() {
        return claims -> (Integer)claims.get("group");
    }


}
