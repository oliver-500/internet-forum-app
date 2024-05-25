package org.unibl.etf.forum_authentication_controller.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;
import org.unibl.etf.forum_authentication_controller.model.GitHubAccessTokenRequest;
import org.unibl.etf.forum_authentication_controller.model.dto.UserDTO;
import org.unibl.etf.forum_authentication_controller.model.dto.UserRequestDTO;
import org.unibl.etf.forum_authentication_controller.model.requests.LoginRequest;
import org.unibl.etf.forum_authentication_controller.model.requests.RefreshSessionRequest;
import org.unibl.etf.forum_authentication_controller.model.requests.VerificationRequest;
import org.unibl.etf.forum_authentication_controller.model.responses.*;
import org.unibl.etf.forum_authentication_controller.services.AuthenticationService;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/auth")

public class AuthenticationController {

    @Value("${jwt.controller.server.address}")
    private String jwtControllerAddress;

    @Value("${spring.security.oauth2.client.registration.github.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.github.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.github.redirect-uri}")
    private String redirectUri;

    @Value("${access.controller.server.address}")
    private String accessControllerAddress;

    @Value("${spring.security.oauth2.client.provider.github.token-uri}")
    private String githubTokenUri;

    @Value("${spring.security.oauth2.client.provider.github.user-info-uri}")
    private String githubUserInfoUri;


    @Value("${custom.spring.security.oauth2.client.provider.github.user-email-info-uri}")
    private String githubUserInfoEmailUri;

    @Value("${web.server.address}")
    private String webServerAddress;

    @Value("${authentication.controller.server.address}")
    private String authenticationControllerServerAddress;

    private final RestTemplate restTemplate;
    private final AuthenticationService authenticationService;

    public AuthenticationController(RestTemplate restTemplate, AuthenticationService authenticationService) {
        this.restTemplate = restTemplate;
        this.authenticationService = authenticationService;

    }

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> registerUser(@RequestBody UserDTO user)  {
        return ResponseEntity.ok(authenticationService.registerUser(user, false));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest request){
        return ResponseEntity.ok(authenticationService.loginUser(request));
    }

    @PostMapping("/confirmLogin")
    public ResponseEntity<VerificationResponse> authenticateUser(@RequestBody VerificationRequest request){
        UserDTO user = authenticationService.authenticateUser(request);
        if(user != null){
            return ResponseEntity.ok(authenticationService.requestJWTToken(user));
        }


        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new VerificationResponse());
    }

    @PostMapping("/requestJwt")
    public ResponseEntity<VerificationResponse> requestJwt(@RequestBody VerificationRequest request){
            System.out.println("authhhhhhhh" + request.getCode());
            return ResponseEntity.ok(authenticationService.requestJwt(request));


    }

    @PostMapping("/refreshSession")
    public ResponseEntity<VerificationResponse> refreshSession(@RequestBody RefreshSessionRequest req){
        return ResponseEntity.ok(authenticationService.refreshSession(req));
    }

    @PostMapping("/validateToken")
    public ResponseEntity<JwtTokenValidationResponse> validateToken(@RequestBody String token){


        return ResponseEntity.ok(authenticationService.validateToken(token));
    }

    private String frontendUrl = "";

    @GetMapping("/saveReferer")
    public ResponseEntity<PredicateResponse> customOauthGithub(@RequestParam("referer") String referer, HttpServletRequest request) {
        System.out.println(request.getHeader("referer") + " eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeej"  +referer);
        frontendUrl = referer;
        PredicateResponse pr = new PredicateResponse();
        pr.setSuccessfull(true);
        return ResponseEntity.ok(pr);
    }

    @GetMapping("/ssl")
    public ResponseEntity<String> test(){






        return ResponseEntity.ok("DADA papa");
    }





    @ResponseBody
    @GetMapping("/oauth/callback")
    public RedirectView callback(@RequestParam("code") String code, HttpServletRequest request, HttpServletResponse responseee) {
        // Handle successful authentication
        System.out.println( " 1");
        VerificationResponse res = new VerificationResponse();
        // Perform any necessary actions with the client, like fetching user info
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        System.out.println( " 2");
        GitHubAccessTokenRequest req = new GitHubAccessTokenRequest();
        req.setCode(code);
        req.setClient_id(clientId);
        req.setClient_secret(clientSecret);
        req.setRedirect_uri(redirectUri);//https://localhost:9000/api/v1/auth/oauth/callback");




        System.out.println( " 3");
        HttpEntity<GitHubAccessTokenRequest> requestEntity = new HttpEntity<>(req, headers);

        // Send POST request to GitHub's token endpoint to exchange code for access token
        ResponseEntity<GithubAccessTokenResponse> response = restTemplate.postForEntity(
                githubTokenUri,//"https://github.com/login/oauth/access_token",
                requestEntity,
                GithubAccessTokenResponse.class);

        // Handle response, extract access token, and store it securely
        if (response.getStatusCode().is2xxSuccessful()) {

            System.out.println( " 4");


            //poslati zahtjev za user informacije ili mail
            GithubAccessTokenResponse result = response.getBody();

            if(result != null && result.getError() != null) {
                //redirektovat da se ponovo prijavi
                System.out.println(result.getError());
                String frontendRedirectUrl = frontendUrl;//"http://localhost:4200/auth/login";
                System.out.println( " 5");
                return new RedirectView(frontendRedirectUrl);

            }
            System.out.println( " 6  " +  request.getHeader("Origin") + request.getRemoteHost() + " " + request.getRemoteAddr() + " " + request.getRemotePort());
            headers.add("Authorization", "TOKEN " + result.getAccess_token());
            HttpEntity<String> userInfoRequestEntity = new HttpEntity<>(headers);

            ResponseEntity<GithubUserInfoResponse> userInfoResponse = restTemplate.exchange(
                    githubUserInfoUri,//"https://api.github.com/user",
                    HttpMethod.GET,
                    userInfoRequestEntity,
                    GithubUserInfoResponse.class);

            System.out.println( " 7");
            if(userInfoResponse.getStatusCode().is2xxSuccessful()){
                System.out.println( " 8");
                HttpEntity<String> userEmailRequest = new HttpEntity<>(headers);
                ResponseEntity<GithubUserEmailResponse[]> userEmailResponse = restTemplate.exchange(
                        githubUserInfoEmailUri,//"https://api.github.com/user/emails",

                        HttpMethod.GET,
                        userEmailRequest,
                        GithubUserEmailResponse[].class);

                System.out.println( " 9");
                System.out.println("email: " + Arrays.stream(userEmailResponse.getBody()).filter(e -> e.isPrimary()).collect(Collectors.toList()).get(0));

                UserDTO user = new UserDTO();
                user.setRegistered(true);
                user.setEmail(Arrays.stream(userEmailResponse.getBody()).filter(e -> e.isPrimary()).collect(Collectors.toList()).get(0).getEmail());
                user.setUsername(userInfoResponse.getBody().getLogin());

                String OAuthLoginCode = Integer.toString(new Random().nextInt(Integer.MAX_VALUE - 1000) + 1001);
                user.setActivationCode(OAuthLoginCode);
                System.out.println( " 10");
                if(!authenticationService.registerUser(user, true).isRegistered()){
                    if(!authenticationService.saveOAuth2Code(user)){
                        String frontendRedirectUrl = frontendUrl;//"http://localhost:4200/auth/login";
                        System.out.println( " 66");
                        return new RedirectView(frontendRedirectUrl);
                    }
                }
                VerificationResponse responsee = authenticationService.requestJWTToken(user);

                ;

                request.getSession().setAttribute(OAuthLoginCode, responsee);

                System.out.println(request.getSession().getId() + " OVO JE POCETNI ID");

                System.out.println( " 11");
                Cookie usernameCookie = new Cookie("username", user.getUsername());
                usernameCookie.setHttpOnly(true);
                usernameCookie.setSecure(true); // Set to true if using HTTPS
                usernameCookie.setPath("/");
              //  usernameCookie.setDomain("localhost");
                usernameCookie.setMaxAge(60 * 60); // 1 hour
                responseee.addCookie(usernameCookie);

                String frontendRedirectUrl = frontendUrl + "?" + "code=" +  OAuthLoginCode; //http://localhost:4200/auth/login?" + "code=" +  OAuthLoginCode;
                return new RedirectView(frontendRedirectUrl);





            }

        } else {

              System.out.println(response.getStatusCode() + " greska");
        }


        String frontendRedirectUrl = webServerAddress + "/auth/login";//http://localhost:4200/auth/login";
        return new RedirectView(frontendRedirectUrl);
    }



}
