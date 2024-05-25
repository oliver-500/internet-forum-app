import { HttpClient, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, map, of } from 'rxjs';
import { AuthServiceService } from './auth-service.service';
import { Router } from '@angular/router';
import { RefreshSessionRequest } from 'src/app/models/refresh-session-request';
import { environment } from 'src/environment/environment';

@Injectable({
  providedIn: 'root'
})
export class JwtInterceptorService implements HttpInterceptor {

  //baseUrl : string = "https://localhost:443/refreshSession"

 // baseUrl = environment.apiUrl + "/api/v1/auth/refreshSession";

  baseUrl = `${window.location.protocol}//${window.location.hostname}:${window.location.port}` + "/api/v1/refreshSession" ;

 // baseUrl: string = "http://localhost:4200/api/v1/auth/refreshSession"

  constructor(
    private authService: AuthServiceService,
    private http: HttpClient,
    private router: Router) { }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {


    if (request.url === this.baseUrl) return next.handle(request);

    if (this.authService.user !== null && this.authService.user.loggedIn == false) {
      console.log("loggedin false")
      return next.handle(request);
    }

    const jwtToken = localStorage.getItem('token');
    if (jwtToken) {
      if (this.authService.user === null) {
        this.authService.refreshSession();

      }
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${jwtToken}`
        }
      });


    }

    return next.handle(request);
  }

  




  
}
