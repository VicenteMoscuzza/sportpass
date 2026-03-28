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

@Injectable({ providedIn: 'root' })
export class EventosService {
  private apiUrl = 'http://localhost:8080/api/eventos';

  constructor(private http: HttpClient) {}

  getProximosEventos(): Observable<Evento[]> {
    return this.http.get<Evento[]>(this.apiUrl);
  }

  getEventoById(id: number): Observable<EventoDetalle> {
    return this.http.get<EventoDetalle>(`${this.apiUrl}/${id}`);
  }
}

