import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UserModuleRoutingModule } from './user-module-routing.module';
import { SectionsComponent } from './sections/sections.component';
import { AppMaterialModule } from '../app-material/app-material.module';
import { UserPanelGuardService } from './services/user-panel-guard.service';
import { RoomComponent } from './room/room.component';
import { NewCommentDialogComponent } from './new-comment-dialog/new-comment-dialog.component';


@NgModule({
  declarations: [
    SectionsComponent,
    RoomComponent,
    NewCommentDialogComponent
  ],
  imports: [
    CommonModule,
    UserModuleRoutingModule,
    AppMaterialModule
  ],
  providers: [
    
  ]
})
export class UserModuleModule { }
