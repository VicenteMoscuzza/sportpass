import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { EventosService, Evento } from '../../../core/services/eventos.service';

@Component({
  selector: 'app-admin-eventos',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './admin-eventos.component.html',
  styleUrls: ['./admin-eventos.component.scss']
})
export class AdminEventosComponent implements OnInit {
  loading = true;
  eventos: Evento[] = [];
  error: string | null = null;

  constructor(
    private eventosService: EventosService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.cargar();
  }

  cargar(): void {
    this.loading = true;
    this.error = null;
    this.eventosService.getProximosEventos().subscribe({
      next: (eventos) => {
        this.eventos = eventos;
        this.loading = false;
      },
      error: () => {
        this.error = 'No se pudieron cargar los eventos.';
        this.loading = false;
      }
    });
  }

  nuevo(): void {
    this.router.navigate(['/admin/eventos/nuevo']);
  }

  editar(id: number): void {
    this.router.navigate(['/admin/eventos', id, 'editar']);
  }

  eliminar(id: number): void {
    if (!confirm('¿Querés cancelar (eliminar) este evento?')) return;

    this.eventosService.eliminarEvento(id).subscribe({
      next: () => this.cargar(),
      error: () => (this.error = 'No se pudo eliminar el evento.')
    });
  }

  volver(): void {
    this.router.navigate(['/admin']);
  }

  formatFechaHora(fecha: string): string {
    const d = new Date(fecha);
    return `${d.toLocaleDateString('es-AR')} ${d.toLocaleTimeString('es-AR', { hour: '2-digit', minute: '2-digit' })}`;
  }
}

