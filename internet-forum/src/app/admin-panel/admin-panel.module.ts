import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdminPanelRoutingModule } from './admin-panel-routing.module';
import { AppMaterialModule } from '../app-material/app-material.module';
import { UsersComponent } from './users/users.component';


@NgModule({
  declarations: [
    UsersComponent
  ],
  imports: [
    CommonModule,
    AdminPanelRoutingModule,
    AppMaterialModule
  ]
})
export class AdminPanelModule { }
