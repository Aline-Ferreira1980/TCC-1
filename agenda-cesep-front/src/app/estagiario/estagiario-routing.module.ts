import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {AgendamentosComponent} from "./agendamentos/agendamentos.component";
import {AuthGuard} from "../security/auth.guard";
import {PerfilComponent} from "./perfil/perfil.component";

const routes: Routes = [
  {
    path:'estagiario/:idUser/agendamentos',
    component: AgendamentosComponent,
    canActivate: [AuthGuard],
    data:{role: ['paciente']}
  },
  {
    path:'estagiario/perfil/:idUser',
    component: PerfilComponent,
    canActivate: [AuthGuard],
    // data:{role: ['paciente']}
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class EstagiarioRoutingModule { }
