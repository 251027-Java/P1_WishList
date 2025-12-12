import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditWishlistComponent } from './edit-wishlist.component';

describe('EditWishlistComponent', () => {
  let component: EditWishlistComponent;
  let fixture: ComponentFixture<EditWishlistComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EditWishlistComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditWishlistComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
