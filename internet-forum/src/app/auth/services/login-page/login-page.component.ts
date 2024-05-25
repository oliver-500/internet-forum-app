import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthServiceService } from '../auth-service.service';
import { LoginRequest } from 'src/app/models/login-request';
import { ActivatedRoute, Router } from '@angular/router';
import { JwtTokenRequest } from 'src/app/models/jwt-token-request';
import { ConfirmLoginResponse } from 'src/app/models/confirm-login-response';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent {

  loginForm: FormGroup;

  request: LoginRequest = {
    username: "",
    password: ""
  }
  
  message : string = ""
  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthServiceService,
    private router: Router,
    private route: ActivatedRoute,
  private _snackBar: MatSnackBar){

    this.loginForm = this.formBuilder.group({
      username: ["", Validators.required],
      password: ["", Validators.required]
    })

  }

  ngOnInit(): void{
    if(this.authService.isLoggedIn()){
      this.router.navigate(["/home/rooms"]);
    }

    this.route.queryParams.subscribe(params => {
      const code = params['code'];
      if(!code) return;
      let req : JwtTokenRequest = new JwtTokenRequest();
      req.code = code;
      this.authService.requestJwt(req).subscribe({
        next : (response: ConfirmLoginResponse) =>{
          
          localStorage.setItem("token", response.token);
          this._snackBar.open("Successfull login", "OK", {
            duration: 3000,
            panelClass: ['custom-snackbar']
          });
          this.router.navigate(["/home/rooms"])
        },
        error: (error : Error) =>{
          console.log("error");
        }
  
      })
    });

    
  
  }

  logInGithub(): void{
    this.authService.loginWithGithub().subscribe({
      next : (response : any) =>{
        window.location.href = response.redirectURL;
       
      },
      error: (error : Error) =>{
        this._snackBar.open("Something went wrong", "OK", {
          duration: 3000,
          panelClass: ['custom-snackbar']
        });
      }

    })
  }

  onFormSubmit(): void{
    if(this.loginForm.valid){
      const loginRequest = {
        username: this.loginForm.value.username,
        password: this.loginForm.value.password
      };

      // Send the login request using the AuthServiceService
      this.authService.loginUser(loginRequest).subscribe({
        next: (response) => {
          // Handle successful login response
         
          
          if(response.loggedIn){

            if(response.registered){
              sessionStorage.setItem("username", loginRequest.username);
              this._snackBar.open("Successfull login", "OK", {
                duration: 3000,
                panelClass: ['custom-snackbar']
              });
              this.authService.user = {
                isLoggingIn: true
              }
    
              this.router.navigate(["/auth/confirm"]);
            }
             
            else  {
              this._snackBar.open("Your account needs confirmation by administrator.", "OK", {
                duration: 3000,
                panelClass: ['custom-snackbar']
              });
              
            }
            
          }
          else this._snackBar.open("Login failed.", "OK", {
            duration: 3000,
            panelClass: ['custom-snackbar']
          });
          

          
        },
        error: (error) => {
          this._snackBar.open("Login failed.", "OK", {
            duration: 3000,
            panelClass: ['custom-snackbar']
          });
          
        }
      }
      );
    }
    
  }

  clearForm() : void {
    this.loginForm.value.username = "";
    this.loginForm.value.password = "";
  }

}
