import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthServiceService } from './auth-service.service';

@Injectable({
  providedIn: 'root'
})
export class ConfirmationGuardService {

  constructor(private router : Router,
    private authService: AuthServiceService) { }



  canActivate() : Observable<boolean>{

    console.log("can activate guard ")
    return new Observable<boolean>((observer) => {
      if(sessionStorage.getItem("username") === null){
        observer.next(false);
        this.router.navigate(['/auth/login']);
        
      }
      else {
        if( this.authService.user !== null && this.authService.user.isLoggingIn === true)
          observer.next(true);
        else {
          observer.next(false);
          this.router.navigate(['/auth/login']);
        }

      } 
        
      observer.complete();

    });

      
  }
}
