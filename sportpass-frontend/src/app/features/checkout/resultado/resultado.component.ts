import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { CompraService } from '../../../core/services/compra.service';

@Component({
  selector: 'app-resultado',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="layout">
      <nav class="navbar">
        <div class="nav-brand">
          <span class="brand-icon">⬡</span>
          <span class="brand-name">SportPass</span>
        </div>
      </nav>
      <div class="content">
        <div *ngIf="status === 'success'" class="success">
          <div class="icon">✓</div>
          <h1>¡Pago aprobado!</h1>
          <p>Tu compra fue procesada correctamente.</p>
          <button (click)="irInicio()">Volver al inicio</button>
        </div>
        <div *ngIf="status === 'failure'" class="failure">
          <div class="icon">✕</div>
          <h1>Pago rechazado</h1>
          <p>Hubo un problema con tu pago. Podés intentarlo de nuevo.</p>
          <button (click)="irInicio()">Volver al inicio</button>
        </div>
        <div *ngIf="status === 'pending'" class="pending">
          <div class="icon">⏳</div>
          <h1>Pago pendiente</h1>
          <p>Tu pago está siendo procesado.</p>
          <button (click)="irInicio()">Volver al inicio</button>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .layout { min-height: 100vh; background: var(--bg); color: var(--text); }
    .navbar { display: flex; align-items: center; padding: 20px 48px; border-bottom: 1px solid var(--border); }
    .nav-brand { display: flex; align-items: center; gap: 10px; }
    .brand-icon { font-size: 20px; color: var(--accent); }
    .brand-name { font-family: 'DM Mono', monospace; font-size: 14px; letter-spacing: 0.08em; text-transform: uppercase; }
    .content { max-width: 480px; margin: 0 auto; padding: 80px 48px; text-align: center; }
    .icon { width: 56px; height: 56px; border-radius: 50%; display: flex; align-items: center; justify-content: center; font-size: 24px; margin: 0 auto 24px; }
    .success .icon { background: var(--accent); color: #0a0a0a; }
    .failure .icon { background: var(--error); color: white; }
    .pending .icon { background: #333; color: var(--text); }
    h1 { font-size: 28px; font-weight: 300; margin-bottom: 8px; }
    p { font-size: 14px; color: var(--text-muted); margin-bottom: 32px; }
    button { background: none; border: 1px solid var(--border); color: var(--text); border-radius: 8px; padding: 12px 28px; font-family: 'DM Sans', sans-serif; font-size: 14px; cursor: pointer; transition: border-color 0.2s; }
    button:hover { border-color: var(--accent); }
  `]
})
export class ResultadoComponent implements OnInit {
  status = '';

  constructor(private route: ActivatedRoute, private router: Router) {}

  ngOnInit(): void {
    this.status = this.route.snapshot.queryParams['status'] || 'failure';
  }

  irInicio(): void { this.router.navigate(['/']); }
}