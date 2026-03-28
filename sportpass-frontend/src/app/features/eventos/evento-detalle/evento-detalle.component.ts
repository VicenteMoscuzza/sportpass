import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { EventosService, EventoDetalle } from '../../../core/services/eventos.service';

@Component({
  selector: 'app-evento-detalle',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './evento-detalle.component.html',
  styleUrls: ['./evento-detalle.component.scss']
})
export class EventoDetalleComponent implements OnInit {
  evento: EventoDetalle | null = null;
  loading = true;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private eventosService: EventosService
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.eventosService.getEventoById(id).subscribe({
      next: (evento) => {
        this.evento = evento;
        this.loading = false;
      },
      error: () => this.router.navigate(['/'])
    });
  }

  formatFecha(fecha: string): string {
    return new Date(fecha).toLocaleDateString('es-AR', {
      weekday: 'long', day: 'numeric', month: 'long', year: 'numeric'
    });
  }

  formatHora(fecha: string): string {
    return new Date(fecha).toLocaleTimeString('es-AR', {
      hour: '2-digit', minute: '2-digit'
    });
  }

  formatPrecio(precio: number): string {
    return new Intl.NumberFormat('es-AR', {
      style: 'currency', currency: 'ARS', maximumFractionDigits: 0
    }).format(precio);
  }

  volver(): void {
    this.router.navigate(['/']);
  }
}