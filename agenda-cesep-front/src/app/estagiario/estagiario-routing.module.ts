import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {AgendamentosComponent} from "./agendamentos/agendamentos.component";
import {AuthGuard} from "../security/auth.guard";

const routes: Routes = [
  {
    path:'paciente/:idUser/agendamentos',
    component: AgendamentosComponent,
    canActivate: [AuthGuard],
    data:{role: ['paciente']}
  },
  {
    path:'paciente/perfil/:idUser',
    component: AgendamentosComponent,
    canActivate: [AuthGuard],
    data:{role: ['paciente']}
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class EstagiarioRoutingModule { }
