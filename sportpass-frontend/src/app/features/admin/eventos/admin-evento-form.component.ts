import { CommonModule } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import { FormArray, FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import {
  Estadio,
  EventosService,
  EventoCreateRequest,
  EventoDetalle,
  EventoUpdateRequest,
  Zona
} from '../../../core/services/eventos.service';

@Component({
  selector: 'app-admin-evento-form',
  standalone: true,
  imports: [CommonModule, RouterModule, ReactiveFormsModule],
  templateUrl: './admin-evento-form.component.html',
  styleUrls: ['./admin-evento-form.component.scss']
})
export class AdminEventoFormComponent implements OnInit {
  private fb = inject(FormBuilder);

  loading = true;
  saving = false;
  error: string | null = null;

  modo: 'crear' | 'editar' = 'crear';
  eventoId: number | null = null;

  estadios: Estadio[] = [];
  zonas: Zona[] = [];
  evento: EventoDetalle | null = null;

  form = this.fb.group({
    nombre: ['', [Validators.required, Validators.minLength(3)]],
    descripcion: ['', [Validators.required, Validators.minLength(3)]],
    fecha: ['', [Validators.required]], // datetime-local
    estadioId: [null as number | null, [Validators.required]],
    zonaPrecios: this.fb.array([])
  });

  constructor(
    private eventosService: EventosService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    this.eventoId = idParam ? Number(idParam) : null;
    this.modo = this.eventoId ? 'editar' : 'crear';

    this.cargarInicial();
  }

  get zonaPrecios(): FormArray {
    return this.form.get('zonaPrecios') as FormArray;
  }

  private cargarInicial(): void {
    this.loading = true;
    this.error = null;

    this.eventosService.getEstadios().subscribe({
      next: (estadios) => {
        this.estadios = estadios;

        if (this.modo === 'editar' && this.eventoId) {
          this.cargarEventoEdicion(this.eventoId);
        } else {
          this.loading = false;
        }
      },
      error: () => {
        this.error = 'No se pudieron cargar los estadios.';
        this.loading = false;
      }
    });
  }

  private cargarEventoEdicion(id: number): void {
    this.eventosService.getEventoById(id).subscribe({
      next: (evento) => {
        this.evento = evento;

        // En edición, no podemos inferir estadioId desde el DTO actual, así que lo dejamos vacío.
        // Permitimos editar nombre/descripcion/fecha/estado sin tocar estadio/zonas.
        this.form.patchValue({
          nombre: evento.nombre,
          descripcion: evento.descripcion,
          fecha: this.toDatetimeLocal(evento.fecha)
        });

        // Deshabilitamos estadio y zonas en modo edición (tu backend update no cambia estadio/zonas)
        this.form.get('estadioId')?.disable();
        this.zonaPrecios.disable();

        this.loading = false;
      },
      error: () => {
        this.error = 'No se pudo cargar el evento.';
        this.loading = false;
      }
    });
  }

  onEstadioChange(): void {
    const estadioId = this.form.get('estadioId')?.value;
    if (!estadioId) return;

    this.eventosService.getZonasPorEstadio(estadioId).subscribe({
      next: (zonas) => {
        this.zonas = zonas;
        this.resetZonasForm(zonas);
      },
      error: () => (this.error = 'No se pudieron cargar las zonas del estadio.')
    });
  }

  private resetZonasForm(zonas: Zona[]): void {
    this.zonaPrecios.clear();
    zonas.forEach((z) => {
      this.zonaPrecios.push(
        this.fb.group({
          zonaId: [z.id, [Validators.required]],
          zonaNombre: [z.nombre],
          precio: [0, [Validators.required, Validators.min(0)]],
          capacidadDisponible: [0, [Validators.required, Validators.min(0)]]
        })
      );
    });
  }

  guardar(): void {
    this.error = null;

    if (this.form.invalid) {
      this.form.markAllAsTouched();
      this.error = 'Revisá los campos del formulario.';
      return;
    }

    this.saving = true;

    if (this.modo === 'crear') {
      const payload = this.buildCreatePayload();
      this.eventosService.crearEvento(payload).subscribe({
        next: () => {
          this.saving = false;
          this.router.navigate(['/admin/eventos']);
        },
        error: () => {
          this.saving = false;
          this.error = 'No se pudo crear el evento.';
        }
      });
      return;
    }

    if (!this.eventoId) return;
    const payload = this.buildUpdatePayload();
    this.eventosService.actualizarEvento(this.eventoId, payload).subscribe({
      next: () => {
        this.saving = false;
        this.router.navigate(['/admin/eventos']);
      },
      error: () => {
        this.saving = false;
        this.error = 'No se pudo actualizar el evento.';
      }
    });
  }

  private buildCreatePayload(): EventoCreateRequest {
    const raw = this.form.getRawValue();
    return {
      nombre: raw.nombre!,
      descripcion: raw.descripcion!,
      fecha: this.fromDatetimeLocal(raw.fecha!),
      estadioId: raw.estadioId!,
      zonaPrecios: (raw.zonaPrecios || []).map((zp: any) => ({
        zonaId: Number(zp.zonaId),
        precio: Number(zp.precio),
        capacidadDisponible: Number(zp.capacidadDisponible)
      }))
    };
  }

  private buildUpdatePayload(): EventoUpdateRequest {
    const raw = this.form.getRawValue();
    return {
      nombre: raw.nombre,
      descripcion: raw.descripcion,
      fecha: this.fromDatetimeLocal(raw.fecha)
    };
  }

  cancelar(): void {
    this.router.navigate(['/admin/eventos']);
  }

  titulo(): string {
    return this.modo === 'crear' ? 'Nuevo evento' : 'Editar evento';
  }

  private toDatetimeLocal(iso: string): string {
    // "2026-04-07T15:01:25" -> "2026-04-07T15:01"
    const d = new Date(iso);
    const pad = (n: number) => `${n}`.padStart(2, '0');
    return `${d.getFullYear()}-${pad(d.getMonth() + 1)}-${pad(d.getDate())}T${pad(d.getHours())}:${pad(d.getMinutes())}`;
  }

  private fromDatetimeLocal(value: string | null | undefined): string {
    if (!value) return '';
    // Mantener formato ISO (sin segundos) compatible con LocalDateTime en backend
    return value;
  }
}

