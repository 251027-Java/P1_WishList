import { Routes } from '@angular/router';
import {WishlistComponent} from './components/wishlist/wishlist.component';
import { LoginSignupComponent } from './components/login-signup/login-signup.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { HomeComponent } from './components/home/home.component';
import { Auth } from './services/auth';
<<<<<<< HEAD
import { CountdownComponent } from './components/countdown/countdown.component';
import { BrowseComponent } from './components/browse/browse.component';
=======
import { ViewWishlistComponent } from './components/view-wishlist/view-wishlist.component';
import { EditWishlistComponent } from './components/edit-wishlist/edit-wishlist.component';
import { authGuard } from './guards/auth.guard';
>>>>>>> 494c150eb3a9bb835483621f507d04ebfd67c813

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
        path:"home",
        component:HomeComponent,
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
        path:"wishlist",
        component:WishlistComponent,
        canMatch: [Auth]
=======
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
        path:"home",
        component:HomeComponent
>>>>>>> 494c150eb3a9bb835483621f507d04ebfd67c813
    },
    {
        path: '**',
        redirectTo: () => ''
    }
];
