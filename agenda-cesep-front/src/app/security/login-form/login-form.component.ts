import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../auth.service";
import {faArrowUp} from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent implements OnInit{
  public formulario: FormGroup;

  constructor(private service: AuthService,
              private fb: FormBuilder) {

    this.formulario = this.fb.group({
      login: ['',Validators.required],
      senha: ['', Validators.required]
    });

  }

  ngOnInit(): void {
  }

  login(){
    if (this.formulario.invalid) {
      return;
    }
    const login = this.formulario.value.login;
    const senha = this.formulario.value.senha;

    this.service.login(login, senha);
  }

    protected readonly faArrowUp = faArrowUp;
}
