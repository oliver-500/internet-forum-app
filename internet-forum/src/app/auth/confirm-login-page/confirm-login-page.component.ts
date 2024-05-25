import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ConfirmLoginRequest } from 'src/app/models/confirm-login-request';
import { AuthServiceService } from '../services/auth-service.service';
import { ConfirmLoginResponse } from 'src/app/models/confirm-login-response';
import { User } from 'src/app/models/user';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-confirm-login-page',
  templateUrl: './confirm-login-page.component.html',
  styleUrls: ['./confirm-login-page.component.css']
})
export class ConfirmLoginPageComponent {
    username: string | null = null;


    message : string = "";
    isWaitingForResponse : boolean = false;
    confirmationForm: FormGroup;

    constructor(private router : Router,
      private formBuilder: FormBuilder,
      private authService: AuthServiceService,
      private _snackBar: MatSnackBar ){

        this.confirmationForm = formBuilder.group({
          code: ["", Validators.required]
        })
      
      this.username = sessionStorage.getItem("username");
      
    }

    

    goBack(): void{
      sessionStorage.removeItem("username");
      this.router.navigate(["/auth/login"]);
    }

    onFormSubmit(){
      this.isWaitingForResponse = true;
      var username : string | null = sessionStorage.getItem("username");
      if( username === null){
        this.router.navigate(["/auth/login"]);
        return;
      }

      if(!this.confirmationForm.valid){
        this.isWaitingForResponse = false;
        return;
      } 
      const req : ConfirmLoginRequest = {
          username: username,
          code: this.confirmationForm.value.code
          
      }

      this.authService.confirmLogin(req).subscribe({
        next:  (response: ConfirmLoginResponse)=> {
            if(response.verified){
              localStorage.setItem("token", response.token);
              
           

              this.authService.user = {
                username : req.username,
                id: response.id,
                loggedIn: true,
                userGroup: response.userGroup,
                isLoggingIn: false
              }
              console.log(this.authService.user.id);
              this.message = "Confirmation successfull."

              setTimeout(() => {
                this.router.navigate(["/home/rooms"]);
              }, 300)
              
              sessionStorage.removeItem("username");
            }
            else this.message = "Confirmation failed."
            this.clearForm();
        },
        error: (error) => {
          this._snackBar.open("Confirmation failed", "OK", {
            duration: 3000,
            panelClass: ['custom-snackbar']
          });
          this.isWaitingForResponse = false;
        }
      })

      


    }

    clearForm() : void {
      this.confirmationForm.value.code = "";
      
    }

    
}
