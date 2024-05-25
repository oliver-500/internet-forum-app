import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthServiceService } from '../services/auth-service.service';
import { SignUpRequest } from 'src/app/models/signup-request';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-signup-page',
  templateUrl: './signup-page.component.html',
  styleUrls: ['./signup-page.component.css']
})
export class SignupPageComponent {

  signupForm: FormGroup;

  message: string = "";

  constructor(private formBuilder: FormBuilder,
    private authService: AuthServiceService,
    private router: Router,
  private _snackBar: MatSnackBar) {
    this.signupForm = formBuilder.group({
      username: ["", Validators.required],
      email: ["", [Validators.email, Validators.required]],
      password: ["", Validators.required],

    })
  }

  onFormSubmit(): void {
    if (!this.signupForm.valid) return;

    const request: SignUpRequest = {
      username: this.signupForm.value.username,
      password: this.signupForm.value.password,
      email: this.signupForm.value.email
    }

    this.authService.signupUser(request).subscribe({
      next: (response) => {
        this.message = response.isRegistered
        if (!response.registered) {
          
          this._snackBar.open("User could not be registered.", "OK", {
            duration: 3000,
            panelClass: ['custom-snackbar']
          });
        }
        else {
          
          this._snackBar.open("Registration successfull. Check your email for account activation.", "OK", {
            duration: 3000,
            panelClass: ['custom-snackbar']
          });
        }

        this.clearForm();

      },
      error: (response) => {
       
        this._snackBar.open("Something went wrong", "OK", {
          duration: 3000,
          panelClass: ['custom-snackbar']
        });
      }
    })


  }

  clearForm() {
    this.signupForm.value.username = "";
    this.signupForm.value.email = "";
    this.signupForm.value.password = "";
  }

  ngOnInit(){
    if(this.authService.isLoggedIn()){
      this.router.navigate(["/home/rooms"]);
    }
    console.log("signup ")
  
  }
}
