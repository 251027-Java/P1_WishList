import { Routes } from '@angular/router';
import {WishlistComponent} from './components/wishlist/wishlist.component';
import { LoginSignupComponent } from './components/login-signup/login-signup.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { HomeComponent } from './components/home/home.component';
import { Auth } from './services/auth';
import { CountdownComponent } from './components/countdown/countdown.component';
import { BrowseComponent } from './components/browse/browse.component';
import { authGuard } from './guards/auth.guard';

export const routes: Routes = [
    {
        path:"",
        component:LoginSignupComponent
    },
    {
        path:"dashboard",
        component:DashboardComponent,
        canMatch: [authGuard]
    },
    {
<<<<<<< HEAD
        path:"view-wishlists",
        component:ViewWishlistComponent,
        canMatch: [authGuard]
    },
    {
        path:"edit-wishlist",
        component:EditWishlistComponent,
        canMatch: [authGuard]
    },
    {
          path:"browse",
          component:BrowseComponent,
          canMatch: [Auth]
    },
    {
          path:"countdown",
          component:CountdownComponent,
    },
    {
        path:"home",
        component:HomeComponent
=======
        path:"home",
        component:HomeComponent,
    },
    {
        path:"browse",
        component:BrowseComponent,
        canMatch: [authGuard]
    },
    {
        path:"countdown",
        component:CountdownComponent,
    },
    {
        path:"wishlist/:id",
        component:WishlistComponent,
        canMatch: [authGuard]
>>>>>>> 5ca9ccb2c5fd9bcb9a8255f49e49f5c9e377b1e3
    },
    {
        path: '**',
        redirectTo: () => ''
    }
];
