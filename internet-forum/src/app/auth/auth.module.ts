import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AuthRoutingModule } from './auth-routing.module';
import { LoginPageComponent } from './services/login-page/login-page.component';
import { SignupPageComponent } from './signup-page/signup-page.component';
import { AuthServiceService } from './services/auth-service.service';
import { ConfirmLoginPageComponent } from './confirm-login-page/confirm-login-page.component';
import { AppMaterialModule } from '../app-material/app-material.module';
import { JwtInterceptorService } from './services/jwt-interceptor.service';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';


@NgModule({
  declarations: [
    LoginPageComponent,
    SignupPageComponent,
    ConfirmLoginPageComponent
    
  ],
  imports: [
    CommonModule,
    AuthRoutingModule,
    AppMaterialModule,
    HttpClientModule
  ],
  providers: [
    
   
  ]
})
export class AuthModule { }
