import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { EstagiarioRoutingModule } from './estagiario-routing.module';
import { AgendamentosComponent } from './agendamentos/agendamentos.component';
import { PerfilComponent } from './perfil/perfil.component';


@NgModule({
  declarations: [
    AgendamentosComponent,
    PerfilComponent
  ],
  imports: [
    CommonModule,
    EstagiarioRoutingModule
  ]
})
export class EstagiarioModule { }
