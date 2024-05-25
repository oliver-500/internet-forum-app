import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UsersComponent } from './users/users.component';
import { UserPanelGuardService } from '../user-module/services/user-panel-guard.service';

const routes: Routes = [
  {
    path: "users",
    component: UsersComponent,
    canActivate: [UserPanelGuardService]//staviti adminPanelguardService
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminPanelRoutingModule { }
