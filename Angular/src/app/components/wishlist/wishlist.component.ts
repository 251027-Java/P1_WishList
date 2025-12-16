import { Component, OnInit } from '@angular/core';
import { WishlistItem } from '../../interfaces/wishlist-item';
import { CommonModule } from '@angular/common';
import { Wishlist } from '../../interfaces/wishlist';
import { WishlistService } from '../../services/wishlist.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-wishlist',
  imports: [CommonModule],
  templateUrl: './wishlist.component.html',
  styleUrl: './wishlist.component.css'
})
export class WishlistComponent implements OnInit{
  items: WishlistItem[] = [];
  page = 1;
  loading = false;
  endReached = false;
  wishlistId: string | null = null;

  constructor(private wishlistService: WishlistService, private route: ActivatedRoute) {}

  ngOnInit(): void {
    // Try to read wishlist id from route params and then load items
    const id = this.route.snapshot.paramMap.get('id');
    this.wishlistId = id;
    this.loadItems(true);
  }

  loadItems(reset = false): void {
    if (this.loading || this.endReached) return;
    console.log('Loading items for wishlist:', this.wishlistId);
    if (!this.wishlistId) return;

    this.loading = true;

    if (reset) {
      this.page = 1;
      this.items = [];
    }

    this.wishlistService.getWishlistItems(this.wishlistId).subscribe({
      next: (data) => {
        console.log('Loaded items:', data);
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
}
