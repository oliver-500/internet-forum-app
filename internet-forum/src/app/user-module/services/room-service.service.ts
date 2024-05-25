import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environment/environment';

@Injectable({
  providedIn: 'root'
})
export class RoomServiceService {

  constructor(private http: HttpClient) { }

  //baseUrl : string = "https://localhost:443/rooms"
  //baseUrl : string = "http://localhost:4200/api/v1/rooms"

 // baseUrl = environment.apiUrl + "/api/v1/rooms";

  baseUrl = `${window.location.protocol}//${window.location.hostname}:${window.location.port}` + "/api/v1/rooms" ;

  getRooms(){
    return this.http.get(`${this.baseUrl}/all`) 
  }

  getRoom(id: number){
    return this.http.get(`${this.baseUrl}/${id}`);

  }
  getRoomWithAllowedComments(id: number){
    return this.http.get(`${this.baseUrl}/${id}/withAllowedComments`)
  }

  getRoomWithNewCommentCount(){
    return this.http.get(`${this.baseUrl}/allWithCommentsCount`)
  }

  
}
