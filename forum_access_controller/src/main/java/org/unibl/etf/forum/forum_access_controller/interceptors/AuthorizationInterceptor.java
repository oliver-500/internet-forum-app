package org.unibl.etf.forum.forum_access_controller.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.unibl.etf.forum.forum_access_controller.models.requests.UserPermissionCheckRequest;
import org.unibl.etf.forum.forum_access_controller.models.responses.JwtTokenValidationResponse;
import org.unibl.etf.forum.forum_access_controller.models.responses.PredicateResponse;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

    @Value("${authentication.controller.server.address}")
    private String authenticationControllerServerAddress;
    private static final List<Pattern> EXCLUDED_URLS = Arrays.asList(Pattern.compile("/api/v1/auth/.*"));
    private static final List<Pattern> MODERATOR_URLS = Arrays.asList(

            Pattern.compile("/api/v1/comments/approve/\\d+"),
            Pattern.compile("/api/v1/rooms/allWithCommentsCount"),
            Pattern.compile(("/api/v1/comments/mod/deleteUnapproved/\\d+"))
    );

    private static final List<Pattern> ADMIN_URLS = Arrays.asList(
            Pattern.compile("/api/v1/rooms/\\d+"),
            Pattern.compile("/api/v1/users/\\d+"),
            Pattern.compile("/api/v1/users"),
            Pattern.compile("/api/v1/accesses"),
            Pattern.compile("/api/v1/accesses/\\d+"),
            Pattern.compile("/api/v1/users/activate")


    );

    private static final List<Pattern> USER_URLS = Arrays.asList(
            Pattern.compile("/api/v1/comments/(\\d+)/(.*)"));



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        System.out.println(requestURI + "             -pristupam ovom uriju" );
        for(Pattern exludedUrl : EXCLUDED_URLS) {
            if (exludedUrl.matcher(requestURI).matches()) {
                System.out.println("exluded");
                return true;

            }
        }


        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // Extract the JWT token from the Authorization header
            String jwtToken = authHeader.substring(7); // Remove "Bearer " prefix

            // Print or process the JWT token as needed
            RestTemplate restTemplate = new RestTemplate();

            String url = authenticationControllerServerAddress + "/api/v1/auth/validateToken";

            HttpEntity<String> httpEntity = new HttpEntity<String>(jwtToken);

            ResponseEntity<JwtTokenValidationResponse> httpResponse = restTemplate.exchange(url, HttpMethod.POST, httpEntity, JwtTokenValidationResponse.class);

            if(!httpResponse.getBody().isActivated()){
                System.out.println("izbacen");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }
            request.setAttribute("userId", httpResponse.getBody().getUserId());

            if(!httpResponse.getBody().isSuccessfull()){
                System.out.println("validan");
                return false;

            }

            for(Pattern userUrl : USER_URLS) {

                Matcher matcher = userUrl.matcher(requestURI);
                if (userUrl.matcher(requestURI).matches()) {
                    if( (Pattern.compile("/api/v1/comments/(\\d+)/create")).matcher(requestURI).matches()){

                        //provjeriti da li je on onaj za kojeg postavlja komentar
                    }
                   if(matcher.find()){


                       String methodName = matcher.group(matcher.groupCount());
                       int userId = httpResponse.getBody().getUserId();


                       int roomId = Integer.parseInt(matcher.group(matcher.groupCount() - 1));
                           UserPermissionCheckRequest upcr = new UserPermissionCheckRequest();
                           upcr.setMethodName(methodName);

                           upcr.setRoomId(roomId);
                           upcr.setUserId(userId);



                           url = authenticationControllerServerAddress + "/authorization/checkPermissionForUser";

                           HttpEntity<UserPermissionCheckRequest> httpEntity2 = new HttpEntity<UserPermissionCheckRequest>(upcr);

                           ResponseEntity<PredicateResponse> httpResponse2 = restTemplate.exchange(url, HttpMethod.POST, httpEntity2, PredicateResponse.class);


                           return httpResponse2.getBody().isSuccessfull();








                   }
                   else{
                       System.out.println("no match");
                   }

                    return true;

                }
            }



            for(Pattern moderatorUrl : MODERATOR_URLS){
                if(moderatorUrl.matcher(requestURI).matches()){
                    if(httpResponse.getBody().getUserGroup() == 1 || httpResponse.getBody().getUserGroup() == 2){
                        System.out.println("validan mod");
                        return true;
                    }
                    else {
                        System.out.println("nevalidan mod");
                        return false;
                    }
                }
            }

            for(Pattern adminUrl : ADMIN_URLS){
                if(adminUrl.matcher(requestURI).matches()){
                    if(httpResponse.getBody().getUserGroup() == 1 || httpResponse.getBody().getUserGroup() == 2){
                        System.out.println("validan admin");
                        return true;
                    }
                    else {
                        System.out.println("nevalidan admin");
                        return false;
                    }
                }
            }



            return true;

        } else {
            // Authorization header is missing or does not start with "Bearer "
            // Handle the error or return an appropriate response
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }



    }
}
