import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Evento {
  id: number;
  nombre: string;
  descripcion: string;
  fecha: string;
  estado: string;
  estadioNombre: string;
  estadioCiudad: string;
  precioDesde: number;
}

export interface ZonaInfo {
  id: number;
  zonaId: number;
  zonaNombre: string;
  precio: number;
  capacidadDisponible: number;
  esGeneral: boolean;
}

export interface EventoDetalle {
  id: number;
  nombre: string;
  descripcion: string;
  fecha: string;
  estado: string;
  estadioNombre: string;
  estadioCiudad: string;
  estadioDireccion: string;
  zonas: ZonaInfo[];
}

export interface AsientoInfo {
  id: number;
  fila: string;
  numero: number;
  ocupado: boolean;
}

export interface Estadio {
  id: number;
  nombre: string;
  ciudad: string;
  direccion: string;
}

export interface Zona {
  id: number;
  nombre: string;
}

export interface EventoCreateRequest {
  nombre: string;
  descripcion: string;
  fecha: string; // ISO string
  estadioId: number;
  zonaPrecios: { zonaId: number; precio: number; capacidadDisponible: number }[];
}

export interface EventoUpdateRequest {
  nombre?: string | null;
  descripcion?: string | null;
  fecha?: string | null; // ISO string
  estado?: 'ACTIVO' | 'CANCELADO' | string | null;
}

@Injectable({ providedIn: 'root' })
export class EventosService {
  private apiUrl = '/api/eventos';

  constructor(private http: HttpClient) {}

  getProximosEventos(): Observable<Evento[]> {
    return this.http.get<Evento[]>(this.apiUrl);
  }

  getEventoById(id: number): Observable<EventoDetalle> {
    return this.http.get<EventoDetalle>(`${this.apiUrl}/${id}`);
  }

  getAsientosPorZona(eventoId: number, zonaId: number): Observable<AsientoInfo[]> {
    return this.http.get<AsientoInfo[]>(`${this.apiUrl}/${eventoId}/zonas/${zonaId}/asientos`);
  }

  // Admin
  getEstadios(): Observable<Estadio[]> {
    return this.http.get<Estadio[]>(`${this.apiUrl}/admin/estadios`);
  }

  getZonasPorEstadio(estadioId: number): Observable<Zona[]> {
    return this.http.get<Zona[]>(`${this.apiUrl}/admin/estadios/${estadioId}/zonas`);
  }

  crearEvento(request: EventoCreateRequest): Observable<EventoDetalle> {
    return this.http.post<EventoDetalle>(`${this.apiUrl}/admin/eventos`, request);
  }

  actualizarEvento(id: number, request: EventoUpdateRequest): Observable<EventoDetalle> {
    return this.http.put<EventoDetalle>(`${this.apiUrl}/admin/eventos/${id}`, request);
  }

  eliminarEvento(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/admin/eventos/${id}`);
  }
}

