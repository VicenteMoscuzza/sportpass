import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface CompraRequest {
  eventoZonaId: number;
  asientoId: number | null;
  cantidad: number;
}

export interface EntradaInfo {
  id: number;
  zonaNombre: string;
  fila: string;
  numero: number;
  codigoQr: string;
}

export interface CompraResponse {
  id: number;
  estado: string;
  total: number;
  entradas: EntradaInfo[];
}

@Injectable({ providedIn: 'root' })
export class CompraService {
  private apiUrl = '/api/compras';

  constructor(private http: HttpClient) {}

  crearCompra(request: CompraRequest): Observable<CompraResponse> {
    return this.http.post<CompraResponse>(this.apiUrl, request);
  }

  iniciarPago(request: CompraRequest): Observable<{ checkoutUrl: string, preferenceId: string }> {
    return this.http.post<{ checkoutUrl: string, preferenceId: string }>(
      `${this.apiUrl}/iniciar-pago`, request
    );
  }
}