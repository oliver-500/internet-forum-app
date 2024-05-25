import { Component } from '@angular/core';
import { Room } from 'src/app/models/room';
import { RoomServiceService } from '../services/room-service.service';
import { ActivatedRoute } from '@angular/router';
import { RoomComment } from 'src/app/models/room-comment';
import { DatePipe } from '@angular/common';
import { UserService } from '../../admin-panel/services/user.service';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { NewCommentDialogComponent } from '../new-comment-dialog/new-comment-dialog.component';
import { AuthServiceService } from 'src/app/auth/services/auth-service.service';
import { CommentService } from 'src/app/services/comment.service';
import { ApproveCommentRequest } from 'src/app/models/approve-comment-request';
import { PageEvent } from '@angular/material/paginator';
import { UserPermission } from 'src/app/models/user-permission';
import { UseraccessesService } from 'src/app/admin-panel/services/useraccesses.service';
import { MyAccessesRequest } from 'src/app/models/my-accesses-request';
import { EditCommentRequest } from 'src/app/models/edit-comment-request';
import { DeleteCommentRequest } from 'src/app/models/delete-comment-request';
import { MatSnackBar } from '@angular/material/snack-bar';


export interface newCommentDialogData {
  room: string;
  isEdit: boolean;
  comment: RoomComment;

}


@Component({
  selector: 'app-room',
  templateUrl: './room.component.html',
  styleUrls: ['./room.component.css']
})
export class RoomComponent {

  accesses: UserPermission[] = []


  approveComment(index: number) {
    console.log("approve")
    var request: ApproveCommentRequest = new ApproveCommentRequest();
    var commentBeingEdited: RoomComment | undefined = this.room?.comments?.at(index);
    request.approved = true;
    if (request.approved)
      request.content = this.room?.comments?.at(index)?.content;
    request.id = this.room?.comments?.at(index)?.id;
    this.commentService.approveComment(request).subscribe(
      {

        next: (response: any) => {

          this._snackBar.open("Comment approved.", "OK", {
            duration: 3000,
            panelClass: ['custom-snackbar']
          });

          if (this.room) this.getRoom(this.room.id!);

        },
        error: (error: Error) => {

        }
      }
    )
  }

  updateComment(index: number){
    var request: EditCommentRequest = new EditCommentRequest();
    request.content = this.room?.comments?.at(index)?.content;
    request.id = this.room?.comments?.at(index)?.id;
    request.roomId = this.room?.id;

    this.commentService.editComment(request).subscribe(
      {
        next : (response : any) =>{
          if (this.room) this.getRoom(this.room.id!);
          this._snackBar.open("Comment edit success.", "OK", {
            duration: 3000,
            panelClass: ['custom-snackbar']
          });
        },
        error : (error : Error) =>{
          this._snackBar.open("Something went wrong.", "OK", {
            duration: 3000,
            panelClass: ['custom-snackbar']
          });
        }

      }
    )
  }

  getRoom(id: number) {


    if (this.authService.user?.userGroup == 0) {
      this.roomService.getRoomWithAllowedComments(id).subscribe({
        next: (response: any) => {
          this.room = response;

          this.room?.comments?.map(comment => {
            comment.postedDatetime = new Date(comment.postedDatetime!);
            //comment.postedDatetime = this.formatDate(comment.postedDatetime)
          })

          this.sortComments(this.room?.comments!);
          if (this.room?.comments) this.pagedComments = this.room?.comments.slice(0, this.pageSize);

        },
        error: (response) => {
        }
      }
      );
    }
    else if (this.authService.user?.userGroup == 1 || this.authService.user?.userGroup == 2) {
      this.roomService.getRoom(id).subscribe({
        next: (response: any) => {
          this.room = response;
          this.room?.comments?.map(comment => {

            const d1: Date = new Date(comment.postedDatetime!);

            comment.postedDatetime = new Date(comment.postedDatetime!);
          })

          this.sortComments(this.room?.comments!);
          if (this.room?.comments) this.pagedComments = this.room?.comments.slice(0, this.pageSize);
        },
        error: (response) => {
        }
      }
      );





    }
  }


  room: Room | null = null;

  constructor(private roomService: RoomServiceService,
    private route: ActivatedRoute,
    private datePipe: DatePipe,
    private userService: UserService,
    public dialog: MatDialog,
    public authService: AuthServiceService,
    public commentService: CommentService,
    private userAccessService: UseraccessesService,
    private _snackBar: MatSnackBar

  ) {

  }

  openNewCommentDialog() {



    const dialogRef = this.dialog.open(NewCommentDialogComponent, {
      data: {
        room: this.room?.name,
        isEdit: false,
      },
      width: '500px', // Specify the width here
      height: '300px',
    })




    dialogRef.afterClosed().subscribe((roomComment: RoomComment) => {
      if (roomComment) {
        roomComment.author = this.authService.user?.username;
        roomComment.userId = this.authService.user?.id;

        console.log(roomComment.userId + "      id" + roomComment)
        roomComment.roomId = this.room?.id;
        this.commentService.postComment(roomComment).subscribe({
          next: (response: any) => {
            this.getRoom(this.room?.id!);
            this._snackBar.open("Succesfully added", "OK", {
              duration: 3000,
              panelClass: ['custom-snackbar']
            });
          },
          error: (error: any) => {
            this._snackBar.open("Try later", "OK",
            {
              duration: 3000,
            }
            );
          }
        }
        )
        //dodati
      }

    });

  }

  openEditCommentDialog(index: number): void {

    var commentBeingEdited: RoomComment | undefined = { ...this.room?.comments?.[index] };
    const dialogRef = this.dialog.open(NewCommentDialogComponent, {
      data: {
        room: this.room,
        isEdit: true,
        comment: commentBeingEdited
      },
      width: '500px',
      height: '300px'
    })

    dialogRef.afterClosed().subscribe((commentEdit: newCommentDialogData) => {

      if (!commentEdit) return;
      if (this.room?.comments?.[index]?.content !== commentEdit.comment.content) {
        if (commentBeingEdited && this.room?.comments) {
          commentBeingEdited.isEdited = true;
          commentBeingEdited.content = commentEdit.comment.content;
          this.room.comments[index] = commentEdit.comment;
          this.pagedComments = this.room?.comments.slice(0, this.pageSize);
          //this.room?.comments?.splice(index, 1, commentEdit.comment);
        }
      }
    });

  }

  doesPermissionExist(permissionName: string, isCommentApproved : boolean): boolean {
    if (this.authService.user?.userGroup && this.authService.user.userGroup === 2 && !isCommentApproved)
      return true;
    else {
      for (let access of this.accesses) {
        if (access.permission?.name.toLowerCase() === permissionName.toLowerCase()) {
  
          return true;

        }

      }
    }
   
    return false;
  }


  ngOnInit(): void {
    console.log("USER ID: " + this.authService.user?.id);
   
    this.room = {};
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      this.room.id = +idParam; // Use +idParam to convert string to number
    }
    else return;

    this.getRoom(this.room.id);
    
    console.log(this.authService.user?.id + " dsadsa" + this.room.id);
    let req: MyAccessesRequest = {
      userId: this.authService.user?.id,
      roomId: this.room.id
    }
    this.userAccessService.getMyAccesses(req).subscribe({
      next: (response: any) => {
        this.accesses = response;
      },
      error: (error: Error) => {

      }

    })

  }



  sortComments(comments: RoomComment[]) {
    comments.sort((c1, c2) => {
      if (c1.approved && c2.approved) {
        if (c1.postedDatetime && c2.postedDatetime) {
          const d1 = new Date(c1.postedDatetime);
          const d2 = new Date(c2.postedDatetime);

          return d2.getTime() - d1.getTime();
        }
        else return 1;

      }
      else if (c1.approved && !c2.approved) return 1;

      else if (!c2.approved && c2.approved) return -1;
      else {
        if (c1.postedDatetime && c2.postedDatetime) {
          const d1 = new Date(c1.postedDatetime);
          const d2 = new Date(c2.postedDatetime);
          return d2.getTime() - d1.getTime();
        }
        else return -1;
      }

    })
  }

  formatDate(dateString: string | undefined): string | undefined {
    if (dateString == undefined) return undefined;
    const dateObj = new Date(dateString);
    const convertedDate = this.datePipe.transform(dateObj, 'HH:mm / dd-MM-yyyy ');
    if (convertedDate != null) {

      return convertedDate;
    }
    else return undefined;
  }

  pagedComments: RoomComment[] = [];
  pageSize = 20;
  pageSizeOptions = [3, 10, 20];

  onPageChange(event: PageEvent) {
    const startIndex = event.pageIndex * event.pageSize;
    const endIndex = startIndex + event.pageSize;
    if (this.room?.comments) this.pagedComments = this.room?.comments.slice(startIndex, endIndex);

  }

  deleteComment(id: number, isCommentApproved : boolean) {

    if(isCommentApproved){
      let req = new DeleteCommentRequest();
      req.id = id;
      req.roomId = this.room?.id;
      this.commentService.deleteComment(req).subscribe(
        {
          next: (response: any) => {
            this._snackBar.open("Succesfully deleted", "OK", {
              duration: 3000,
              panelClass: ['custom-snackbar']
            });
            this.getRoom(this.room?.id!);
          },
          error: (error: any) => {
            this._snackBar.open("Something went wrong", "OK", {
              duration: 3000,
              panelClass: ['custom-snackbar']
            });
          }
  
  
        }
      )
    }
    else{
      let req = new DeleteCommentRequest();
      req.id = id;
      req.roomId = this.room?.id;
      this.commentService.deleteUnnaprovedComment(req).subscribe(
        {
          next: (response: any) => {
            this._snackBar.open("Succesfully deleted", "OK", {
              duration: 3000,
              panelClass: ['custom-snackbar']
            });
            this.getRoom(this.room?.id!);
          },
          error: (error: any) => {
            this._snackBar.open("Something went wrong", "OK", {
              duration: 3000,
              panelClass: ['custom-snackbar']
            });
          }
  
  
        }
      )
    }

    

  }
}
