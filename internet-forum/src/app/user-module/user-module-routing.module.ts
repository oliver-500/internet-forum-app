import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SectionsComponent } from './sections/sections.component';
import { UserPanelGuardService } from './services/user-panel-guard.service';
import { RoomComponent } from './room/room.component';

const routes: Routes = [
  {
    path: "rooms",
    component: SectionsComponent,
    canActivate: [UserPanelGuardService]
  },
  {
    path: "rooms/:id",
    component: RoomComponent,
    canActivate: [UserPanelGuardService]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserModuleRoutingModule { }
