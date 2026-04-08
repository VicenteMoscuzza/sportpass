import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth-guard';
import { redirectIfLoggedGuard } from './core/guards/redirect-if-logged-guard';
import { adminGuard } from './core/guards/admin-guard';

export const routes: Routes = [
  {
    path: 'auth',
    loadChildren: () => import('./features/auth/auth-module').then(m => m.AuthModule),
    canActivate: [redirectIfLoggedGuard]
  },
  {
    path: '',
    loadChildren: () => import('./features/dashboard/dashboard-module').then(m => m.DashboardModule),
    canActivate: [authGuard]
  },
  {
    path: 'eventos',
    loadChildren: () => import('./features/eventos/eventos-module').then(m => m.EventosModule),
    canActivate: [authGuard]
  },
  {
    path: 'checkout',
    loadChildren: () => import('./features/checkout/checkout-module').then(m => m.CheckoutModule),
    canActivate: [authGuard]
  },
  {
    path: 'admin',
    loadChildren: () => import('./features/admin/admin-module').then(m => m.AdminModule),
    canActivate: [authGuard, adminGuard]
  }
];