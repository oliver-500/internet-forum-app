import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthServiceService } from 'src/app/auth/services/auth-service.service';
import { JwtInterceptorService } from 'src/app/auth/services/jwt-interceptor.service';

@Injectable({
  providedIn: 'root'
})
export class UserPanelGuardService {

  constructor(
    private authService: AuthServiceService,
    private router: Router
  ) { }

  canActivate(): Observable<boolean> {
    return new Observable<boolean>((observer) => {
      console.log("user " + this.authService.user?.userGroup)
      if (this.authService.user !== null && this.authService.user.loggedIn == true) {
        observer.next(true);
        observer.complete();
        console.log("daa");
      }
      else {
        console.log("jaa");

        this.authService.refreshSession().subscribe({
          next: (response) => {
            if (this.authService.user !== null && this.authService.user.loggedIn == true) {
              observer.next(true)
            }
            else {
              observer.next(false);
              this.router.navigate(["/auth/login"])
            }
            observer.complete();
          },
          error: (response) => {
            observer.next(false);
            observer.complete();
          }
        })




      }
      console.log("prvi")

    })
  }
}
