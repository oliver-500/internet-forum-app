import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LoginRequest } from '../../models/login-request';
import { ConfirmLoginRequest } from 'src/app/models/confirm-login-request';
import { SignUpRequest } from 'src/app/models/signup-request';
import { User } from 'src/app/models/user';
import { Observable, catchError, map, of } from 'rxjs';
import { RefreshSessionRequest } from 'src/app/models/refresh-session-request';
import { JwtTokenRequest } from 'src/app/models/jwt-token-request';
import { environment } from 'src/environment/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthServiceService {

  //private baseUrl = "https://localhost:443/auth"
  //private baseUrl = "http://localhost:4200/api/v1/auth"

  //baseUrl = environment.apiUrl + "/api/v1/auth";

  baseUrl = `${window.location.protocol}//${window.location.hostname}:${window.location.port}` + "/api/v1/auth" ;

  constructor(private http: HttpClient) { }

 
  user: User | null = null;


  requestOptions = {
    withCredentials: true
  };

  loginUser(loginRequest: LoginRequest) {
    return this.http.post<any>(`${this.baseUrl}/login`, loginRequest, this.requestOptions);
  }

  confirmLogin(confirmLoginRequest: ConfirmLoginRequest) {
    return this.http.post<any>(`${this.baseUrl}/confirmLogin`, confirmLoginRequest, this.requestOptions);
  }

  signupUser(signUpRequest: SignUpRequest) {
    return this.http.post<any>(`${this.baseUrl}/register`, signUpRequest, this.requestOptions);
  }

  isLoggedIn() : boolean {
    if(this.user !== null && this.user.loggedIn){
        return true;
    }
    return false;
  }

  loginWithGithub(){
    return this.http.get(`${this.baseUrl}/loginGithub`);
  }

  initializeApp() : Observable<any>{
    return this.refreshSession();
    
  }

  requestJwt(req : JwtTokenRequest) : Observable<any>{
    return this.http.post(`${this.baseUrl}/requestJwt`, req)
  }


  refreshSession(): Observable<any> {
    const jwtToken = localStorage.getItem('token');

    if (!jwtToken) {
      return of(true); // Return an observable immediately if no JWT token is present
    }


    if (this.user === null) {
      
      const req: RefreshSessionRequest = {
        token: jwtToken
      };

      return this.http.post(`${this.baseUrl}/refreshSession`, req).pipe(
        map((response: any) => {
          if (response.verified === false) {
            this.user = {
              loggedIn : false,
              isLoggingIn: false
            }
            localStorage.removeItem("token");
          } else {
            
            this.user = {
              username: response.username,
              id: response.id,
              loggedIn: true,
              userGroup: response.userGroup,
              isLoggingIn: false
            };
          }
          return response; // Return the response to the caller
        }),
        catchError((error) => {
          localStorage.removeItem("token");
          this.user = {
            loggedIn : false,
            isLoggingIn: false
          }
          // Handle any errors here
         
          return of(true); // Return an observable with null in case of an error
        })
      );
    } else {
      return of(true); // Return an observable immediately if the user is already logged in
    }
  }
 
}
