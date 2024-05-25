import { Component, OnInit } from '@angular/core';
import { JwtInterceptorService } from './auth/services/jwt-interceptor.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent{
  
  
  title = 'internet-forum';

  constructor(){

  }

 
}
