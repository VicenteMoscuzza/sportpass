import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth-guard';
import { redirectIfLoggedGuard } from './core/guards/redirect-if-logged-guard';

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
  }
];