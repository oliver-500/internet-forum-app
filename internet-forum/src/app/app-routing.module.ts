import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginPageComponent } from './auth/services/login-page/login-page.component';
import { SignupPageComponent } from './auth/signup-page/signup-page.component';

const routes: Routes = [
  {
    path: "auth",
    loadChildren: () => import("./auth/auth.module").then(mod => mod.AuthModule)
  },
  {
    path: "home",
    loadChildren: () => import("./user-module/user-module.module").then(mod => mod.UserModuleModule)

  },
  {
    path: "admin",
    loadChildren: () => import("./admin-panel/admin-panel.module").then(mod => mod.AdminPanelModule)
  },
 
  {
    path: "",
    redirectTo: "auth/login",
    pathMatch: "full"
  },
  {
    path: "**",
    redirectTo: "auth/login"
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
