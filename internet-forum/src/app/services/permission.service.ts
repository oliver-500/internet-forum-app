import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environment/environment';

@Injectable({
  providedIn: 'root'
})
export class PermissionService {

  //private baseUrl : string = "http://localhost:4200/api/v1/permissions"

  //baseUrl = environment.apiUrl + "/api/v1/permissions";

  baseUrl = `${window.location.protocol}//${window.location.hostname}:${window.location.port}` + "/api/v1/permissions" ;

  constructor(private http: HttpClient) {
    
   }

   getAll(){
    return this.http.get(this.baseUrl);
   }
}
