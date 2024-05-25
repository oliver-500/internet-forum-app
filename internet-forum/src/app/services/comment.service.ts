import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RoomComment } from '../models/room-comment';
import { ApproveCommentRequest } from '../models/approve-comment-request';
import { EditCommentRequest } from '../models/edit-comment-request';
import { DeleteCommentRequest } from '../models/delete-comment-request';
import { environment } from 'src/environment/environment';

@Injectable({
  providedIn: 'root'
})
export class CommentService {

 
   //baseUrl : string = "https://localhost:443/comments"
 // baseUrl : string = "http://localhost:4200/api/v1/comments"

   
  //baseUrl = environment.apiUrl + "/api/v1/comments";
  baseUrl = `${window.location.protocol}//${window.location.hostname}:${window.location.port}` + "/api/v1/comments" ;

  constructor(private http : HttpClient) {

   }

   postComment(comment : RoomComment ) : Observable<Object>{
    return this.http.post(`${this.baseUrl}/${comment.roomId}/create`, comment);
   }

   approveComment(approveCommentRequest: ApproveCommentRequest){
    return this.http.post(`${this.baseUrl}/approve/${approveCommentRequest.id}` , approveCommentRequest)
   }

   editComment(req : EditCommentRequest){
    return this.http.post(`${this.baseUrl}/${req.roomId}/update` , req)
   }

   deleteComment(req : DeleteCommentRequest){
    return this.http.post(`${this.baseUrl}/${req.roomId}/delete`, req)
   }

   deleteUnnaprovedComment(req : DeleteCommentRequest){
    return this.http.post(`${this.baseUrl}/deleteUnapproved/${req.id}`, req)
   }

  
}
