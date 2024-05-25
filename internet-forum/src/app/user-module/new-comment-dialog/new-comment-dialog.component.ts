import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { newCommentDialogData } from '../room/room.component';
import { RoomComment } from 'src/app/models/room-comment';
import { Room } from 'src/app/models/room';

@Component({
  selector: 'app-new-comment-dialog',
  templateUrl: './new-comment-dialog.component.html',
  styleUrls: ['./new-comment-dialog.component.css']
})
export class NewCommentDialogComponent {
  constructor(private dialogRef: MatDialogRef<NewCommentDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: newCommentDialogData) {

  }

  comment: RoomComment = new RoomComment();
  

  onCancelClick(): void {
    this.dialogRef.close();

  }

  onSaveClick(): void {

    if(!this.data.isEdit){
      console.log(this.comment.content + "?????????????")
      
      this.dialogRef.close({ content: this.comment.content });
    }
      
    else
      this.dialogRef.close({ 
        comment: this.comment,
        
        isEdit: true
    });
  }

  ngOnInit(){
    if(this.data.isEdit) {
      this.comment = this.data.comment;
      
    }
   
  }
  
}
