import { Injectable } from '@angular/core';
import { WishlistItem } from '../interfaces/wishlist-item';
import { HttpClient } from '@angular/common/http';
import { map, Observable } from 'rxjs';
import { Auth } from './auth';
import { Wishlist } from '../interfaces/wishlist';

@Injectable({
  providedIn: 'root'
})
export class WishlistItemService {
  wishlistItems: WishlistItem[] = [];

  constructor(private http:HttpClient, private auth:Auth){}

  getWishlistItem(id: string) {
    const headers = { Authorization: this.auth.getAuthHeader() || '' };
    return this.http.get<WishlistItem[]>(`http://localhost:8080/api/items/${id}`, {headers}).pipe(
      map<any, WishlistItem>(data => ({
        id:data.id,
        name:data.name,
        brand:data.brand,
        price:data.price
      }))
    )
  }

  getWishlistItems(searchTerm?: string) {
    const url = searchTerm
      ? `http://localhost:8080/api/items/search?name=${searchTerm}`
      : `http://localhost:8080/api/items`;
    console.log('Fetching items from URL:', url);
    const headers = { Authorization: this.auth.getAuthHeader() || '' };
    return this.http.get<WishlistItem[]>(url, {headers}).pipe(
      map(data => data.map((item: any) => ({
        id: item.itemId,
        name: item.itemName,
        brand: item.brand,
        price: item.itemPrice
      })))
    );
  }

  addToWishlist(item: WishlistItem, wishlist: Wishlist) {
    const headers = { Authorization: this.auth.getAuthHeader() || '' };
    return this.http.post<any>(
      `http://localhost:8080/api/wishlist-items`,
      { itemId: item.id, wishlistId: wishlist.id },
      { headers }
    );
  }


  removeFromWishlist(item: WishlistItem, wishlist: Wishlist) {
    const headers = { Authorization: this.auth.getAuthHeader() || '' };
    return this.http.delete<any>(
      `http://localhost:8080/api/wishlist-items/${wishlist.id}/${item.id}`,
      { headers }
    );
  }


  isInWishlist(item: WishlistItem, wishlist: Wishlist): Observable<boolean> {
    const headers = { Authorization: this.auth.getAuthHeader() || '' };
    return this.http.get<WishlistItem[]>(
      `http://localhost:8080/api/wishlist-items/${wishlist.id}`,
      { headers }
    ).pipe(
      map(items => items.some(i => i.id === item.id))
    );
  }
}
