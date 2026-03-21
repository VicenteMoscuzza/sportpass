import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../../core/services/auth.service';
import { EventosService, Evento } from '../../../core/services/eventos.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  nombre = '';
  eventosPorFecha: { fecha: string, eventos: Evento[] }[] = [];
  loading = true;

  constructor(
    private authService: AuthService,
    private eventosService: EventosService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.nombre = this.authService.getNombre();
    this.cargarEventos();
  }

  cargarEventos(): void {
    this.eventosService.getProximosEventos().subscribe({
      next: (eventos) => {
        this.eventosPorFecha = this.agruparPorFecha(eventos);
        this.loading = false;
      },
      error: () => { this.loading = false; }
    });
  }

  agruparPorFecha(eventos: Evento[]): { fecha: string, eventos: Evento[] }[] {
    const grupos: { [key: string]: Evento[] } = {};

    eventos.forEach(evento => {
      const fecha = new Date(evento.fecha);
      const clave = fecha.toLocaleDateString('es-AR', {
        weekday: 'long',
        day: 'numeric',
        month: 'long',
        year: 'numeric'
      });
      if (!grupos[clave]) grupos[clave] = [];
      grupos[clave].push(evento);
    });

    return Object.entries(grupos).map(([fecha, eventos]) => ({ fecha, eventos }));
  }

  formatHora(fecha: string): string {
    return new Date(fecha).toLocaleTimeString('es-AR', {
      hour: '2-digit',
      minute: '2-digit'
    });
  }

  formatPrecio(precio: number): string {
    return new Intl.NumberFormat('es-AR', {
      style: 'currency',
      currency: 'ARS',
      maximumFractionDigits: 0
    }).format(precio);
  }

  logout(): void {
    this.authService.logout().subscribe({
      next: () => this.router.navigate(['/auth/login'])
    });
  }
}