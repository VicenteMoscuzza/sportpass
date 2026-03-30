import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { CompraService, CompraResponse } from '../../../core/services/compra.service';
import { EventosService, EventoDetalle, ZonaInfo } from '../../../core/services/eventos.service';

@Component({
  selector: 'app-checkout',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.scss']
})
export class CheckoutComponent implements OnInit {
  evento: EventoDetalle | null = null;
  zona: ZonaInfo | null = null;
  asientoId: number | null = null;
  cantidad = 1;
  loading = true;
  procesando = false;
  compraRealizada: CompraResponse | null = null;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private eventosService: EventosService,
    private compraService: CompraService
  ) {}

  ngOnInit(): void {
    const params = this.route.snapshot.queryParams;
    const eventoId = Number(params['eventoId']);
    const eventoZonaId = Number(params['eventoZonaId']);
    this.asientoId = params['asientoId'] ? Number(params['asientoId']) : null;
    this.cantidad = Number(params['cantidad']) || 1;

    this.eventosService.getEventoById(eventoId).subscribe({
      next: (evento) => {
        this.evento = evento;
        this.zona = evento.zonas.find(z => z.id === eventoZonaId) || null;
        this.loading = false;
      },
      error: () => this.router.navigate(['/'])
    });
  }

  get total(): number {
    return (this.zona?.precio || 0) * this.cantidad;
  }

  confirmarCompra(): void {
    if (!this.zona) return;
    this.procesando = true;
  
    this.compraService.iniciarPago({
      eventoZonaId: this.zona.id,
      asientoId: this.asientoId,
      cantidad: this.cantidad
    }).subscribe({
      next: (res) => {
        window.location.href = res.checkoutUrl;
      },
      error: () => {
        this.procesando = false;
        alert('Error al iniciar el pago');
      }
    });
  }

  formatPrecio(precio: number): string {
    return new Intl.NumberFormat('es-AR', {
      style: 'currency', currency: 'ARS', maximumFractionDigits: 0
    }).format(precio);
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

  irAInicio(): void {
    this.router.navigate(['/']);
  }
}