import { ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';
import {WishlistComponent} from './components/wishlist/wishlist.component';

import { routes } from './app.routes';
import { provideHttpClient } from '@angular/common/http';
<<<<<<< HEAD

=======
>>>>>>> 494c150eb3a9bb835483621f507d04ebfd67c813

export const appConfig: ApplicationConfig = {
  providers: [provideZoneChangeDetection({ eventCoalescing: true }), provideRouter(routes), provideHttpClient()]
};
