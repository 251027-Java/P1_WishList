import { Injectable } from '@angular/core';
import { Wishlist } from '../interfaces/wishlist';
import { BehaviorSubject, map, Observable } from 'rxjs';
import { Auth } from './auth';
import { HttpClient } from '@angular/common/http';
import { WishlistItem } from '../interfaces/wishlist-item';

@Injectable({
  providedIn: 'root'
})
export class WishlistService {
  wishlists: Wishlist[] = [];
  wishlistItems: WishlistItem[] = [];

  constructor(private http:HttpClient, private auth:Auth){}
 
  getWishlists(id: String){
    const headers = { Authorization: this.auth.getAuthHeader() || '' };
        return this.http.get<Wishlist[]>(`http://localhost:8080/api/wishlists/search?userId=${id}`, {headers}).pipe(
          map(data => data.map((wishlist: any) => ({
            id:wishlist.wishlistId,
            name:wishlist.wishlistName,
            user:wishlist.user
          })))
        );
  }

  createWishlist(name: String, userId: String):Observable<Wishlist>{
    const headers = { Authorization: this.auth.getAuthHeader() || '' };
    const wishlist = { wishlistName: name, user: { userId } };
    return this.http.post<Wishlist>(`http://localhost:8080/api/wishlists`, wishlist, {headers});
  }

  updateWishlist(id: String, wishlistName: String){
    const headers = { Authorization: this.auth.getAuthHeader() || '' };
    const wishlist = { wishlistName };
    return this.http.put(`http://localhost:8080/api/wishlists/${id}`, wishlist, {headers});
  }

  deleteWishlist(id: String){
    const headers = { Authorization: this.auth.getAuthHeader() || '' };
    return this.http.delete(`http://localhost:8080/api/wishlists/${id}`, {headers});
  }


}

