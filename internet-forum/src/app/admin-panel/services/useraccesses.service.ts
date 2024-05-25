import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { MyAccessesRequest } from 'src/app/models/my-accesses-request';
import { environment } from 'src/environment/environment';

@Injectable({
  providedIn: 'root'
})
export class UseraccessesService {

  //private baseUrl : string = "http://localhost:4200/api/v1/accesses"

  //baseUrl = environment.apiUrl + "/api/v1/accesses";
  baseUrl = `${window.location.protocol}//${window.location.hostname}:${window.location.port}` + "/api/v1/accesses" ;

  constructor(private http: HttpClient) {
    
   }

   getOneByUser(id: number){
    return this.http.get(`${this.baseUrl}/${id}`);
   }

   getMyAccesses(req : MyAccessesRequest){
    return this.http.post(`${this.baseUrl}/myaccesses`, req);
   }
}
