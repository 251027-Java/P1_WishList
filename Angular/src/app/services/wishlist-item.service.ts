import { Injectable } from '@angular/core';
import { WishlistItem } from '../interfaces/wishlist-item';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs';
import { Auth } from './auth';

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

  addToWishlist(item: WishlistItem) {
    this.wishlistItems.push(item);
  }

  removeFromWishlist(item: WishlistItem) {
    this.wishlistItems = this.wishlistItems.filter(i => i.id !== item.id);
  }

  isInWishlist(item: WishlistItem): boolean {
    return this.wishlistItems.some(i => i.id === item.id);
  }
}
