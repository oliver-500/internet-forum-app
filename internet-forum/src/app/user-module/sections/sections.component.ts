import { Component } from '@angular/core';
import { RoomServiceService } from '../services/room-service.service';
import { Room } from 'src/app/models/room';
import { Router } from '@angular/router';
import { AuthServiceService } from 'src/app/auth/services/auth-service.service';

@Component({
  selector: 'app-sections',
  templateUrl: './sections.component.html',
  styleUrls: ['./sections.component.css']
})
export class SectionsComponent {
  constructor(private roomService: RoomServiceService,
    private router: Router,
    private authService: AuthServiceService) {

  }

  rooms: Room[] = [];

  ngOnInit() {

    if(this.authService.user?.userGroup == 0){
      this.roomService.getRooms().subscribe({
        next: (response: any) => {
          this.rooms = response;
          
        },
        error: (error) => {
        }
      });
    }
    else if(this.authService.user?.userGroup == 1 || this.authService.user?.userGroup == 2){
      this.roomService.getRoomWithNewCommentCount().subscribe({
        next: (response: any) => {
          this.rooms = response;
          
        },
        error: (error) => {
        }
      });
    }

    
  }

  openRoom(id: number | undefined): void {
    console.log("soba" + id)
    this.router.navigate(['/home/rooms', id]);
  }
}
