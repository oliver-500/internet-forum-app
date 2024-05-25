import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UserActivationRequest } from 'src/app/models/user-activation-request';
import { environment } from 'src/environment/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  //baseUrl : string = "http://localhost:4200/api/v1/users"
  
  //baseUrl = environment.apiUrl + "/api/v1/users";

  baseUrl = `${window.location.protocol}//${window.location.hostname}:${window.location.port}` + "/api/v1/users" ;

  constructor(private http: HttpClient) {
    
   }

   getAllUsersWithRegistrationInfo(){
    return this.http.get(`${this.baseUrl}`);
   }

   acivateUser(req : UserActivationRequest){
    return this.http.post(`${this.baseUrl}/activate`, req)
   }
}
