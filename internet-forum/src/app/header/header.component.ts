import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthServiceService } from '../auth/services/auth-service.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {
  constructor( private router : Router,
    public authService : AuthServiceService){

  }

  logout() : void{
    if(this.authService.user)
      this.authService.user = null;
    this.router.navigate(["/auth/login"])
    localStorage.removeItem("token");
  }
}
