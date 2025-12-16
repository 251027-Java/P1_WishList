import { Component, OnInit } from '@angular/core';
import { WishlistItem } from '../../interfaces/wishlist-item';
import { WishlistItemService } from '../../services/wishlist-item.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Wishlist } from '../../interfaces/wishlist';
import { Auth } from '../../services/auth';
import { Subscription } from 'rxjs';
import { WishlistService } from '../../services/wishlist.service';

@Component({
  selector: 'app-browse',
  imports: [CommonModule, FormsModule],
  templateUrl: './browse.component.html',
  styleUrls: ['./browse.component.css']
})
export class BrowseComponent implements OnInit {
  items: WishlistItem[] = [];
  wishlists: Wishlist[] = [];
  private subscription?: Subscription;
  private wishlistSubscription?: Subscription;
  dropdownStyle: { [key: string]: string } | null = null;
  private lastToggleAt = 0;
  searchTerm = '';
  page = 1;
  loading = false;
  endReached = false;
  openDropdownId: string | null = null;
  wlPage = 1;
  wlLoading = false
  wlEndReached = false;

  constructor(private wishlistItemService: WishlistItemService, private wishlistService: WishlistService, private auth: Auth) {}

  ngOnInit(): void {
    this.loadItems();
  }

  loadItems(reset = false): void {
    if (this.loading || this.endReached) return;

    this.loading = true;

    if (reset) {
      this.page = 1;
      this.items = [];
    }

    this.wishlistItemService.getWishlistItems(this.searchTerm).subscribe({
      next: (data) => {
        if (!data.length) {
          this.endReached = true;
        } else {
          this.items = [...this.items, ...data];
          this.page++;
        }
        this.loading = false;
      },
      error: () => (this.loading = false)
    });
  }

  onSearchChange(): void {
    console.log('Service search term:', this.searchTerm);
    this.endReached = false;
    this.loadItems(true);
  }

  onScroll(event: any): void {
    const { scrollTop, scrollHeight, clientHeight } = event.target;
    const nearBottom = scrollTop + clientHeight >= scrollHeight - 50;

    if (nearBottom) this.loadItems();
  }

  addToWishlist(item: WishlistItem, wishlist: Wishlist): void {
    this.wishlistItemService.addToWishlist(item, wishlist);
  }

  removeFromWishlist(item: WishlistItem, wishlist: Wishlist): void {
    this.wishlistItemService.removeFromWishlist(item, wishlist);
  }

  isInWishlist(item: WishlistItem, wishlist: Wishlist): boolean {
    return this.wishlistItemService.isInWishlist(item, wishlist);
  }

  loadWishlists(): void {
    // prevent concurrent wishlist requests
    if (this.wlLoading) return;

    this.wlPage = 1;
    this.wishlists = [];
    const userId = this.auth.getCurrentUser()?.userId;
    if (!userId) return;
    this.wlLoading = true;
    // unsubscribe any previous wishlist subscription
    this.wishlistSubscription?.unsubscribe();
    this.wishlistSubscription = this.wishlistService.getWishlists(userId).subscribe({
      next: (data) => {
        if (!data || !data.length) {
          this.wlEndReached = true;
        } else {
          this.wishlists = [...this.wishlists, ...data];
          this.wlPage++;
        }
        this.wlLoading = false;
      },
      error: () => (this.wlLoading = false)
    });
  }

  toggleDropdown(event: MouseEvent, item: WishlistItem): void {
    event.stopPropagation();
    const now = Date.now();
    if (now - this.lastToggleAt < 250) {
      this.lastToggleAt = now;
      return;
    }
    this.lastToggleAt = now;
    const buttonEl = (event.target as HTMLElement).closest('button') as HTMLElement | null;

    if (this.openDropdownId === item.id) {
      this.openDropdownId = null;
      this.dropdownStyle = null;
      return;
    }

    this.openDropdownId = item.id;
    // compute fixed-position style so the dropdown isn't clipped by the scrolling container
    if (buttonEl) {
      const rect = buttonEl.getBoundingClientRect();
      this.dropdownStyle = {
        position: 'fixed',
        top: `${rect.bottom + 8}px`,
        left: `${Math.max(8, rect.right - 200)}px`,
        minWidth: '160px',
        zIndex: '10000'
      };
    } else {
      this.dropdownStyle = {
        position: 'fixed',
        top: `${event.clientY + 8}px`,
        left: `${Math.max(8, event.clientX - 200)}px`,
        minWidth: '160px',
        zIndex: '10000'
      };
    }

    this.loadWishlists();
  }

  onAddToWishlist(wishlist: Wishlist, item: WishlistItem): void {
    this.wishlistItemService.addToWishlist(item, wishlist);
    this.openDropdownId = null;
    this.dropdownStyle = null;
  }

  onRemoveFromWishlist(wishlist: Wishlist, item: WishlistItem): void {
    this.wishlistItemService.removeFromWishlist(item, wishlist);
    this.openDropdownId = null;
    this.dropdownStyle = null;
  }

  ngOnDestroy(): void {
    this.subscription?.unsubscribe();
    this.wishlistSubscription?.unsubscribe();
  }

  getDropdownStyle(): { [key: string]: string } | null {
    if (!this.dropdownStyle) return null;
    return {
      ...this.dropdownStyle,
      display: 'block',
      visibility: 'visible'
    };
  }
}
