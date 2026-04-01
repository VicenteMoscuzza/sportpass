import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CheckoutRoutingModule } from './checkout-routing-module';
import { CheckoutComponent } from './checkout/checkout.component';
import { ResultadoComponent } from './resultado/resultado.component';

@NgModule({
  imports: [CommonModule, CheckoutRoutingModule, CheckoutComponent, ResultadoComponent]
})
export class CheckoutModule {}