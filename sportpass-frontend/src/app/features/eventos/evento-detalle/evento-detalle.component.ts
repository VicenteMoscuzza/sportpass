import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { EventosService, EventoDetalle, ZonaInfo, AsientoInfo } from '../../../core/services/eventos.service';

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
  zonaSeleccionada: ZonaInfo | null = null;
  asientos: AsientoInfo[] = [];
  asientoSeleccionado: AsientoInfo | null = null;
  cantidadGeneral = 1;
  loadingAsientos = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private eventosService: EventosService
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.eventosService.getEventoById(id).subscribe({
      next: (evento) => { this.evento = evento; this.loading = false; },
      error: () => this.router.navigate(['/'])
    });
  }

  seleccionarZona(zona: ZonaInfo): void {
    if (this.zonaSeleccionada?.id === zona.id) {
      this.zonaSeleccionada = null;
      this.asientos = [];
      this.asientoSeleccionado = null;
      return;
    }

    this.zonaSeleccionada = zona;
    this.asientoSeleccionado = null;
    this.cantidadGeneral = 1;

    if (!zona.esGeneral) {
      this.loadingAsientos = true;
      this.eventosService.getAsientosPorZona(this.evento!.id, zona.zonaId).subscribe({
        next: (asientos) => {
          this.asientos = asientos;
          this.loadingAsientos = false;
        }
      });
    }
  }

  seleccionarAsiento(asiento: AsientoInfo): void {
    if (asiento.ocupado) return;
    this.asientoSeleccionado = asiento;
  }

  incrementar(): void {
    if (this.cantidadGeneral < 10) this.cantidadGeneral++;
  }

  decrementar(): void {
    if (this.cantidadGeneral > 1) this.cantidadGeneral--;
  }

  puedeComprar(): boolean {
    if (!this.zonaSeleccionada) return false;
    if (this.zonaSeleccionada.esGeneral) return this.cantidadGeneral > 0;
    return this.asientoSeleccionado !== null;
  }

  irACheckout(): void {
    const params: any = {
      eventoId: this.evento!.id,
      eventoZonaId: this.zonaSeleccionada!.id,
    };

    if (this.zonaSeleccionada!.esGeneral) {
      params['cantidad'] = this.cantidadGeneral;
    } else {
      params['asientoId'] = this.asientoSeleccionado!.id;
      params['cantidad'] = 1;
    }

    this.router.navigate(['/checkout'], { queryParams: params });
  }

  agruparPorPalco(asientos: AsientoInfo[]): { palco: string, asientos: AsientoInfo[] }[] {
    const grupos: { [key: string]: AsientoInfo[] } = {};
    asientos.forEach(a => {
      if (!grupos[a.fila]) grupos[a.fila] = [];
      grupos[a.fila].push(a);
    });
    return Object.entries(grupos).map(([palco, asientos]) => ({ palco, asientos }));
  }

  formatFecha(fecha: string): string {
    return new Date(fecha).toLocaleDateString('es-AR', {
      weekday: 'long', day: 'numeric', month: 'long', year: 'numeric'
    });
  }

  formatHora(fecha: string): string {
    return new Date(fecha).toLocaleTimeString('es-AR', { hour: '2-digit', minute: '2-digit' });
  }

  formatPrecio(precio: number): string {
    return new Intl.NumberFormat('es-AR', {
      style: 'currency', currency: 'ARS', maximumFractionDigits: 0
    }).format(precio);
  }

  volver(): void { this.router.navigate(['/']); }
}