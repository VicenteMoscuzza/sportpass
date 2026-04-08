import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';

const routes: Routes = [
  { path: '', component: DashboardComponent },
  {
    path: 'eventos',
    loadComponent: () =>
      import('./eventos/admin-eventos.component').then((m) => m.AdminEventosComponent)
  },
  {
    path: 'eventos/nuevo',
    loadComponent: () =>
      import('./eventos/admin-evento-form.component').then((m) => m.AdminEventoFormComponent)
  },
  {
    path: 'eventos/:id/editar',
    loadComponent: () =>
      import('./eventos/admin-evento-form.component').then((m) => m.AdminEventoFormComponent)
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
