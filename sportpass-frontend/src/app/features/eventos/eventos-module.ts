import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EventosRoutingModule } from './eventos-routing-module';
import { EventoDetalleComponent } from './evento-detalle/evento-detalle.component';

@NgModule({
  imports: [CommonModule, EventosRoutingModule, EventoDetalleComponent]
})
export class EventosModule {}