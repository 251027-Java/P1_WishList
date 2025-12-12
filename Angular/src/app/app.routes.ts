import { Routes } from '@angular/router';
import { LoginSignupComponent } from './components/login-signup/login-signup.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { HomeComponent } from './components/home/home.component';
import { Auth } from './services/auth';
import { ViewWishlistComponent } from './components/view-wishlist/view-wishlist.component';
import { EditWishlistComponent } from './components/edit-wishlist/edit-wishlist.component';
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
    },
    {
        path: '**',
        redirectTo: () => ''
    }
];