import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class Auth {
  private _isAuthenticated:boolean = false;
  private router:Router = new Router();

  public isAuthenticated():boolean{
    return this._isAuthenticated
  }

  public authenticateUser(){
    this._isAuthenticated = true;
  }

  logout(){
    this._isAuthenticated = false;
    this.router.navigateByUrl("");
  }

}
