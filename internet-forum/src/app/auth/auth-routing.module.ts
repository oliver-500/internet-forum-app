import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginPageComponent } from './services/login-page/login-page.component';
import { SignupPageComponent } from './signup-page/signup-page.component';
import { ConfirmLoginPageComponent } from './confirm-login-page/confirm-login-page.component';
import { ConfirmationGuardService } from './services/confirmation-guard.service';

const routes: Routes = [

  {
    path: "login",
    component: LoginPageComponent
  },
  {
    path: "signup",
    component: SignupPageComponent
  },
  {
    path: "confirm",
    component: ConfirmLoginPageComponent,
    canActivate: [ConfirmationGuardService]
  }
 

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuthRoutingModule { }
