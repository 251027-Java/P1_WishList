import { Injectable } from '@angular/core';
import { WishlistItem } from '../interfaces/wishlist-item';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs';
import { Auth } from './auth';
import { Wishlist } from '../interfaces/wishlist';

@Injectable({
  providedIn: 'root'
})
export class WishlistItemService {
  private wishlistMap: Map<string, Set<string>> = new Map();
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
    const set = this.wishlistMap.get(wishlist.id) ?? new Set<string>();
    set.add(item.id);
    this.wishlistMap.set(wishlist.id, set);
  }

  removeFromWishlist(item: WishlistItem, wishlist: Wishlist) {
    const set = this.wishlistMap.get(wishlist.id);
    if (set) {
      set.delete(item.id);
      if (set.size === 0) this.wishlistMap.delete(wishlist.id);
      else this.wishlistMap.set(wishlist.id, set);
    }
  }

  isInWishlist(item: WishlistItem, wishlist: Wishlist): boolean {
    const set = this.wishlistMap.get(wishlist.id);
    return !!set && set.has(item.id);
  }

  // helper to get item ids for a wishlist
  getItemIdsForWishlist(wishlistId: string): string[] {
    const set = this.wishlistMap.get(wishlistId);
    return set ? Array.from(set) : [];
  }
}
