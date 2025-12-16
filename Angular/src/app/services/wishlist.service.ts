import { Injectable } from '@angular/core';
import { Wishlist } from '../interfaces/wishlist';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class WishlistService {
  private wishlistsSubject = new BehaviorSubject<Wishlist[]>(this.getInitialWishlists());
  public wishlists$: Observable<Wishlist[]> = this.wishlistsSubject.asObservable();

  constructor() {
    // Load from localStorage if available
    const stored = localStorage.getItem('wishlists');
    if (stored) {
      try {
        const parsed = JSON.parse(stored);
        this.wishlistsSubject.next(parsed);
      } catch (e) {
        console.error('Error loading wishlists from localStorage', e);
      }
    }
  }

  private getInitialWishlists(): Wishlist[] {
    return [
      { id: '1', name: 'Work', emoji: '💼' },
      { id: '2', name: 'School', emoji: '🎓' },
      { id: '3', name: 'Clothes', emoji: '👔' }
    ];
  }

  getWishlists(): Wishlist[] {
    return this.wishlistsSubject.value;
  }

  createWishlist(name: string, emoji: string = '📝'): Wishlist {
    const wishlists = this.getWishlists();
    const newWishlist: Wishlist = {
      id: Date.now().toString(),
      name: name,
      emoji: emoji
    };
    const updated = [...wishlists, newWishlist];
    this.wishlistsSubject.next(updated);
    this.saveToLocalStorage(updated);
    return newWishlist;
  }

  updateWishlist(id: string, name: string): void {
    const wishlists = this.getWishlists();
    const updated = wishlists.map(w => 
      w.id === id ? { ...w, name } : w
    );
    this.wishlistsSubject.next(updated);
    this.saveToLocalStorage(updated);
  }

  deleteWishlist(id: string): void {
    const wishlists = this.getWishlists();
    const updated = wishlists.filter(w => w.id !== id);
    this.wishlistsSubject.next(updated);
    this.saveToLocalStorage(updated);
  }

  getWishlistById(id: string): Wishlist | undefined {
    return this.getWishlists().find(w => w.id === id);
  }

  private saveToLocalStorage(wishlists: Wishlist[]): void {
    try {
      localStorage.setItem('wishlists', JSON.stringify(wishlists));
    } catch (e) {
      console.error('Error saving wishlists to localStorage', e);
    }
  }
}

