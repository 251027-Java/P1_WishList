import { Routes } from '@angular/router';
import {WishlistComponent} from './components/wishlist/wishlist.component';
import { LoginSignupComponent } from './components/login-signup/login-signup.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { HomeComponent } from './components/home/home.component';
import { Auth } from './services/auth';
import { ViewWishlistComponent } from './components/view-wishlist/view-wishlist.component';
import { EditWishlistComponent } from './components/edit-wishlist/edit-wishlist.component';
import { CountdownComponent } from './components/countdown/countdown.component';

export const routes: Routes = [
    {
        path:"",
        component:LoginSignupComponent
    },
    {
        path:"dashboard",
        component:DashboardComponent,
        canMatch: [Auth]
    },
    {
        path:"view-wishlists",
        component:ViewWishlistComponent,
        canMatch: [Auth]
    },
    {
        path:"edit-wishlist",
        component:EditWishlistComponent,
        canMatch: [Auth]
    },
    {
        path:"home",
        component:HomeComponent,
    },
    {
        path:"countdown",
        component:CountdownComponent,
    },
  {
    path:"wishlist",
    component:WishlistComponent,
  },
    {
        path: '**',
        redirectTo: () => ''
    }
];
