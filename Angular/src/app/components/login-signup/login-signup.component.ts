import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { Auth } from '../../services/auth';

@Component({
  selector: 'app-login-signup',
  imports: [FormsModule],
  templateUrl: './login-signup.component.html',
  styleUrl: './login-signup.component.css'
})
export class LoginSignupComponent {
  username:string = ""
  password:string = ""

  constructor(private router:Router, private auth:Auth) {}
  
  login() {
    // send username and password to api

    if (this.username === 'user' && this.password === '1234') {
      alert("username and password accepted");
      this.auth.authenticateUser();
      this.router.navigateByUrl("dashboard");
    }
  }
}
