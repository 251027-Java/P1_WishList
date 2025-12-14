import { Component, OnInit } from '@angular/core';
import { WishlistItem } from '../../interfaces/wishlist-item';
import { WishlistItemService } from '../../services/wishlist-item.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-browse',
  imports: [CommonModule, FormsModule],
  templateUrl: './browse.component.html',
  styleUrls: ['./browse.component.css']
})
export class BrowseComponent implements OnInit {
  items: WishlistItem[] = [];
  searchTerm = '';
  page = 1;
  loading = false;
  endReached = false;

  constructor(private wishlistItemService: WishlistItemService) {}

  ngOnInit(): void {
    this.loadItems();
  }

  loadItems(reset = false): void {
    if (this.loading || this.endReached) return;

    this.loading = true;

    if (reset) {
      this.page = 1;
      this.items = [];
      this.endReached = false;
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

  onSearch(): void {
    this.loadItems(true);
  }

  onScroll(event: any): void {
    const { scrollTop, scrollHeight, clientHeight } = event.target;
    const nearBottom = scrollTop + clientHeight >= scrollHeight - 50;

    if (nearBottom) this.loadItems();
  }

  addToWishlist(item: WishlistItem): void {
    this.wishlistItemService.addToWishlist(item);
  }
}
