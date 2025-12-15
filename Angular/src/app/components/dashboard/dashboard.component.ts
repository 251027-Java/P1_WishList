import { Component, OnInit, OnDestroy, HostListener } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { WishlistService } from '../../services/wishlist.service';
import { Wishlist } from '../../interfaces/wishlist';
import { Subscription } from 'rxjs';
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

  constructor(
    private wishlistService: WishlistService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.subscription = this.wishlistService.wishlists$.subscribe(
      wishlists => this.wishlists = wishlists
    );
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
    if (this.isValidWishlistName(this.newWishlistName)) {
      this.wishlistService.createWishlist(this.newWishlistName);
      this.closeCreatePopup();
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
      this.wishlistService.updateWishlist(this.wishlistToRename.id, this.renameWishlistName);
      this.closeRenamePopup();
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
      this.wishlistService.deleteWishlist(this.wishlistToDelete.id);
      this.closeDeletePopup();
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
