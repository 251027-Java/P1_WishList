import { Injectable } from '@angular/core';
import { WishlistItem } from '../interfaces/wishlist-item';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class WishlistItemService {
  wishlistItems: WishlistItem[] = [];

  constructor(private http:HttpClient){}

  getWishlistItem(id: string) {
    return this.http.get<WishlistItem[]>(`http://localhost:8080/api/items/${id}`).pipe(
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
      ? `http://localhost:8080/api/items?name=${searchTerm}`
      : `http://localhost:8080/api/items`;
    return this.http.get<WishlistItem[]>(url).pipe(
      map(data => data.map((item: any) => ({
        id: item.id,
        name: item.name,
        brand: item.brand,
        price: item.price
      })))
    );
  }
}
