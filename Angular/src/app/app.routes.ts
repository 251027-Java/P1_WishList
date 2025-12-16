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
    },
    {
        path: '**',
        redirectTo: () => ''
    }
];
