import { Component } from '@angular/core';
import { WishlistService, ItemDTO } from '../wishlist.service';
import { Observable } from 'rxjs';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-wishlist',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './wishlist.component.html',
})
export class WishlistComponent {
  items$: Observable<ItemDTO[]>;

  constructor(public wishlistService: WishlistService, String id) {
    // using your seeded user "natalia"
    this.items$ = this.wishlistService.getWishlistItems(id);
  }
}
