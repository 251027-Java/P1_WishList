import { Component, OnInit, OnDestroy, HostListener } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { WishlistService } from '../../services/wishlist.service';
import { Wishlist } from '../../interfaces/wishlist';
import { Subscription } from 'rxjs';
import { Auth } from '../../services/auth';
@Component({
  selector: 'app-dashboard',
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent implements OnInit, OnDestroy {
  wishlists: Wishlist[] = [];
  private subscription?: Subscription;
  
  // Popup states
  showCreatePopup = false
  showRenamePopup = false;
  showDeletePopup = false;
  
  // Form data
  newWishlistName = '';
  renameWishlistName = '';
  wishlistToRename: Wishlist | null = null;
  wishlistToDelete: Wishlist | null = null;
  
  // Dropdown state
  openDropdownId: string | null = null;

  page = 1;
  loading = false;
  endReached = false;

  constructor(
    private wishlistService: WishlistService,
    private router: Router,
    private auth: Auth
  ) {}

  ngOnInit(): void {
    this.loadWishlists();
  }

  loadWishlists(): void {
    this.page = 1;
    this.wishlists = [];
    const userId = this.auth.getCurrentUser()?.userId;
    console.log('Current User ID:', userId);
    if (!userId) return;
    this.subscription = this.wishlistService.getWishlists(userId).subscribe({
      next: (data) => {
        if (!data.length) {
          this.endReached = true;
        } else {
          console.log('Fetched wishlists:', data);
          this.wishlists = [...this.wishlists, ...data];
          this.page++;
        }
        this.loading = false;
      },
      error: () => (this.loading = false)
    });
  }
  

  ngOnDestroy(): void {
    this.subscription?.unsubscribe();
  }

  // CREATE functionality
  openCreatePopup(): void {
    this.newWishlistName = '';
    this.showCreatePopup = true;
  }

  closeCreatePopup(): void {
    this.showCreatePopup = false;
    this.newWishlistName = '';
  }

  onCreateSubmit(): void {
    const userId = this.auth.getCurrentUser()?.userId;
    if (!userId) return;
    if (this.isValidWishlistName(this.newWishlistName)) {
      this.wishlistService.createWishlist(this.newWishlistName, userId).subscribe({
        next: () => {
          this.closeCreatePopup();
          this.loadWishlists();
        },
        error: () => {
          // handle error if desired
        }
      });
    }
  }

  // RENAME functionality
  openRenamePopup(wishlist: Wishlist): void {
    this.wishlistToRename = wishlist;
    this.renameWishlistName = wishlist.name;
    this.showRenamePopup = true;
    this.closeDropdown();
  }

  closeRenamePopup(): void {
    this.showRenamePopup = false;
    this.renameWishlistName = '';
    this.wishlistToRename = null;
  }

  onRenameSubmit(): void {
    if (this.wishlistToRename && this.isValidWishlistName(this.renameWishlistName)) {
      this.wishlistService.updateWishlist(this.wishlistToRename.id, this.renameWishlistName).subscribe({
        next: () => {
          this.closeRenamePopup();
          this.loadWishlists();
        },
        error: () => {
          // handle error
        }
      });
    }
  }

  // DELETE functionality
  openDeletePopup(wishlist: Wishlist): void {
    this.wishlistToDelete = wishlist;
    this.showDeletePopup = true;
    this.closeDropdown();
  }

  closeDeletePopup(): void {
    this.showDeletePopup = false;
    this.wishlistToDelete = null;
  }

  onDeleteConfirm(): void {
    if (this.wishlistToDelete) {
      this.wishlistService.deleteWishlist(this.wishlistToDelete.id).subscribe({
        next: () => {
          this.closeDeletePopup();
          this.loadWishlists();
        },
        error: () => {
          // handle error
        }
      });
    }
  }

  onDeleteCancel(): void {
    this.closeDeletePopup();
  }

  // Dropdown functionality
  toggleDropdown(wishlistId: string): void {
    this.openDropdownId = this.openDropdownId === wishlistId ? null : wishlistId;
  }

  closeDropdown(): void {
    this.openDropdownId = null;
  }

  // Navigation
  navigateToWishlist(wishlist: Wishlist): void {
    this.router.navigate(['/view-wishlists'], { 
      queryParams: { id: wishlist.id, name: wishlist.name } 
    });
  }

  // Validation
  isValidWishlistName(name: string): boolean {
    // Only letters, numbers, and symbols, max 12 characters
    const regex = /^[a-zA-Z0-9\s!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]*$/;
    return name.trim().length > 0 && name.length <= 12 && regex.test(name);
  }

  @HostListener('document:click', ['$event'])
  onDocumentClick(event: MouseEvent): void {
    // Close dropdown when clicking outside
    if (this.openDropdownId !== null) {
      this.closeDropdown();
    }
  }
}
