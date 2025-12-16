import { Component, Injectable } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { Auth } from '../../services/auth';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { User } from '../../interfaces/user';
import { map, Observable } from 'rxjs';


@Component({
  selector: 'app-login-signup',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './login-signup.component.html',
  styleUrl: './login-signup.component.css'
})
export class LoginSignupComponent {
  username:string = ""
  password:string = ""
  checkName:string = "";
  checkPassword:string = "";
  loginStatus:boolean = true;

  constructor(private router:Router, private auth:Auth, private http:HttpClient) {}
  
  login() {
    // send username and password to api
    this.findUser().subscribe({next: data => {
      this.auth.authenticateUser();
      this.router.navigateByUrl("dashboard");
    },
    error: () => {
      this.loginStatus = false;
    }
  });
  }

  findUser():Observable<User> {
    const encoded = btoa(`${this.username}:${this.password}`);

    const headers = new HttpHeaders({
      Authorization: `Basic ${encoded}`
    });

    return this.http.get<User>(`http://localhost:8080/api/users/search?username=${this.username}`, { headers, withCredentials:true }).pipe(
      map<any, User>(data => ({
        userId:data.userId,
        userUsername:data.userUsername,
        userPassword:data.userPassword
      }))
    );
  }
}
