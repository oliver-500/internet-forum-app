import { APP_INITIALIZER, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';


import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SignupPageComponent } from './auth/signup-page/signup-page.component';
import { LoginPageComponent } from './auth/services/login-page/login-page.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { JwtInterceptorService } from './auth/services/jwt-interceptor.service';
import { AuthServiceService } from './auth/services/auth-service.service';
import { HeaderComponent } from './header/header.component';
import { AppMaterialModule } from './app-material/app-material.module';
import { DatePipe } from '@angular/common';



@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent
   
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,
    AppMaterialModule
    
  ],
  providers: [
    DatePipe,
    
    {
      provide: HTTP_INTERCEPTORS,
      useClass: JwtInterceptorService,
      multi: true
    },
    {
      provide: APP_INITIALIZER,
      useFactory: (authService: AuthServiceService) => () => authService.initializeApp(),
      deps: [AuthServiceService],
      multi: true
    }
    
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
