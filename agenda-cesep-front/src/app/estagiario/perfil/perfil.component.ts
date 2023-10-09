import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {PacienteRepositoryService} from "../repository/paciente-repository.service";
import {AuthService} from "../../security/auth.service";

@Component({
  selector: 'app-perfil',
  templateUrl: './perfil.component.html',
  styleUrls: ['./perfil.component.css']
})
export class PerfilComponent implements OnInit{

  constructor(private route: ActivatedRoute,
              private pacienteRepository: PacienteRepositoryService,
              private authService: AuthService) {
  }
  ngOnInit(): void {
    const codigoParceiro = this.route.snapshot.params['codigo'];
    const codigo = this.authService.getUser();

  }




}
