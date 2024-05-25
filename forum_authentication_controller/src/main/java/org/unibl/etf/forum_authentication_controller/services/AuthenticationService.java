package org.unibl.etf.forum_authentication_controller.services;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.unibl.etf.forum_authentication_controller.model.dto.UserDTO;
import org.unibl.etf.forum_authentication_controller.model.requests.LoginRequest;
import org.unibl.etf.forum_authentication_controller.model.requests.RefreshSessionRequest;
import org.unibl.etf.forum_authentication_controller.model.requests.TokenValidationRequest;
import org.unibl.etf.forum_authentication_controller.model.requests.VerificationRequest;
import org.unibl.etf.forum_authentication_controller.model.responses.*;

import java.util.Random;

@Service
@Transactional
public class AuthenticationService {

    @Value("${jwt.controller.server.address}")
    private String jwtControllerAddress;

    @Value("${spring.mail.subject}")
    private String emailSubject;

    @Value("${spring.mail.username}")
    private String senderAddress;
    private final UserService userService;

    private JavaMailSender emailSender;
    private final PasswordEncoder passwordEncoder;
    private final HttpServletRequest request;

    public AuthenticationService(UserService userService, JavaMailSender emailSender, PasswordEncoder passwordEncoder, HttpServletRequest request) {
        this.userService = userService;
        this.emailSender = emailSender;
        this.passwordEncoder = passwordEncoder;
        this.request = request;
    }

    public boolean saveOAuth2Code(UserDTO u){
       UserDTO user = userService.findUserByUsername(u.getUsername());
       if(user == null) return false;
       user.setActivationCode(u.getActivationCode());
       return(userService.saveUser(user) != null);
    }

    public RegistrationResponse registerUser(UserDTO user, boolean isRegistered) {

        RegistrationResponse response = new RegistrationResponse();

        if(userService.existsByUsername(user.getUsername()) || userService.existsByEmail(user.getEmail())) {
            response.setRegistered(false);
            System.out.println("vec registrovan");
            return response;
        }
        if(!isRegistered && user.getPassword() == null) return response;
        if(!isRegistered)
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUserGroup(0);
        System.out.println(user.getActivationCode());
        //kasnije skloniti, ovo treba admin da odobri
        user.setRegistered(isRegistered);
        if(userService.saveUser(user) != null) response.setRegistered(true);



        return response;
    }

    public static String generateFourDigitCode() {
        Random random = new Random();
        int fourDigitNumber = random.nextInt(9000) + 1000;
        return String.valueOf(fourDigitNumber);
    }

    private boolean sendAuthenticationCodeToMail(String to, String text){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(senderAddress);
        message.setTo(to);
        message.setSubject(emailSubject);
        message.setText(text);
        try{
            emailSender.send(message);
            return true;
        }
        catch(Throwable e){
            e.printStackTrace();
            return false;
        }

    }

    public LoginResponse loginUser(LoginRequest loginRequest){
        LoginResponse response = new LoginResponse();

        //prvo provjeriti da li postoji u bazi i da li vec nije registrovan, ako nije registrovan
        //ne dozvoliti login
//        Enumeration<String> headerNames = request.getHeaderNames();
//
//        while (headerNames.hasMoreElements()) {
//            String headerName = headerNames.nextElement();
//            String headerValue = request.getHeader(headerName);
//            System.out.println(headerName + ": " + headerValue);
//        }



        // Iterate through header names and print their values
//        Cookie[] cookies = request.getCookies();
//
//        // Check if cookies exist
//        if (cookies != null) {
//            // Iterate through the cookies and print their names and values
//            for (Cookie cookie : cookies) {
//                System.out.println("Cookieds Name: " + cookie.getName());
//                System.out.println("Cookie Value: " + cookie.getValue());
//            }
//        }



        // Iterate through header names and print their values







        UserDTO user = userService.findUserByUsername(loginRequest.getUsername());

       if(user == null) return response;


        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            return response;
        }
        response.setRegistered(user.getRegistered());
        response.setLoggedIn(true);
        if(user.getRegistered() == false) return response; //mozda bacati exceptione
        String code = generateFourDigitCode();
        sendAuthenticationCodeToMail(user.getEmail(), code);
        System.out.println("Kod za prijavu: " + code);
        user.setActivationCode(code);

        userService.saveUser(user);

//        session.setAttribute("username", loginRequest.getUsername());
//        session.setAttribute("code", code);
//        session.setMaxInactiveInterval(60 * 1000 * 20);
//        System.out.println("JSESSIONID: " + session.getId() + " " +  session.isNew() + "    kod za prijavu:  " +  code);
        //poslat mail sa kodom

        return response;
    }

    public VerificationResponse refreshSession (RefreshSessionRequest req){
        VerificationResponse response = new VerificationResponse();
        String url = jwtControllerAddress + "/jwt/extractUsername"; // Replace with your actual URL

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> requestEntity = new HttpEntity<>(req.getToken());

        try {
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity, String.class);
            if(responseEntity.getStatusCode().is2xxSuccessful()){
                UserDTO user = userService.findUserByUsername(responseEntity.getBody());
                if(user != null)
                 return new VerificationResponse(req.getToken(), user.getRegistered(), user.getId(), user.getUserGroup(), user.getUsername());
                else return response;
            }
            else{
                System.out.println("malformed");
                return response;
            }
        } catch (HttpClientErrorException.Unauthorized ex) {

            return response;
        }







    }

    public UserDTO authenticateUser(VerificationRequest verificationRequest){


        UserDTO user = userService.findUserByUsername(verificationRequest.getUsername());
//        HttpSession session = request.getSession();
//        if(session == null) return response;
//        System.out.println("JSESSIONID u authenticate: " + session.getId());
        //String username = (String) session.getAttribute("username");
        //String code = (String) session.getAttribute("code");
        //provjeriti da li korisnik pokusava da verifikuje kod za onog za kog se predstavlja
        //if(username == null || code == null) return response;

        if(user == null || user.getActivationCode() == null || !user.getActivationCode().equals(verificationRequest.getCode()))
            return null;

        return user;




    }

    public VerificationResponse requestJWTToken(UserDTO u){
        VerificationResponse response = new VerificationResponse();
        String url = jwtControllerAddress + "/jwt/getToken"; // Replace with your actual URL

        UserDTO user = userService.findUserByUsername(u.getUsername());

        RestTemplate restTemplate = new RestTemplate();



        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create HttpEntity with headers and payload
        HttpEntity<UserDTO> requestEntity = new HttpEntity<>(user, headers);

        // Send the request and retrieve the response
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity, String.class);



        if(responseEntity.getStatusCode().is2xxSuccessful()){
            String token = responseEntity.getBody();
            if(token != null){
               if(Integer.parseInt(user.getActivationCode()) < 1001) user.setActivationCode(null);

                userService.saveUser(user);
                response.setVerified(true);
            }
        }

        if(responseEntity.getStatusCode().is2xxSuccessful()) return new VerificationResponse(responseEntity.getBody(), true,
                user.getId(), user.getUserGroup(), user.getUsername());
        else return response;
    }



    public JwtTokenValidationResponse validateToken(String token) {
        JwtTokenValidationResponse response = new JwtTokenValidationResponse();
        String url = jwtControllerAddress + "/jwt/validateToken"; // Replace with your actual URL
        RestTemplate restTemplate = new RestTemplate();


        TokenValidationRequest req = new TokenValidationRequest();
        req.setToken(token);
        // Create HttpEntity with headers and payload
        HttpEntity<TokenValidationRequest> requestEntity = new HttpEntity<>(req);
        ResponseEntity<JwtTokenValidationResponse> responseEntity  = restTemplate.postForEntity(url, requestEntity, JwtTokenValidationResponse.class);
        System.out.println("/validatetoken"  + responseEntity.getBody().isSuccessfull());
        if(responseEntity.getStatusCode().is2xxSuccessful()){
            UserDTO u = userService.findUserByUsername(responseEntity.getBody().getUsername());
            responseEntity.getBody().setUserId(u.getId());
            responseEntity.getBody().setActivated(u.getRegistered());
            response = responseEntity.getBody();
        }
        return response;


    }

    public VerificationResponse requestJwt(VerificationRequest req) {
        Cookie[] cookies = request.getCookies();
        System.out.println(request.getSession().getId() + " OVO JE krajnji ID");
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                System.out.println(cookie.getName());
                if ("username".equals(cookie.getName())) {
                    String username = cookie.getValue();
                    UserDTO u = userService.findUserByUsername(username);
                    String storedCode = u.getActivationCode();
                    if(storedCode.equals(req.getCode())){
                        VerificationResponse res = (VerificationResponse)request.getSession().getAttribute(storedCode);

                        System.out.println( res.getToken());
                        return res;
                    }
                    else{
                        System.out.println("greska sinko " + req.getCode() + " =? " + storedCode);
                    }

                }
            }
        }
        return new VerificationResponse();
    }
}
