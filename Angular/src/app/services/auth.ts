import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class Auth {
  private _isAuthenticated:boolean = false;

  public isAuthenticated():boolean{
    return this._isAuthenticated
  }

  public authenticateUser(){
    this._isAuthenticated = true;
  }

  logout(){
    this._isAuthenticated = false;
  }

}
