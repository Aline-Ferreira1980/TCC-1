import { Injectable } from '@angular/core';
import {AuthRepository} from "./auth-repository";
import {Router} from "@angular/router";
import {JwtPayloadEntity} from "./entity/jwt-payload-entity";


@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private jwtPayload!: JwtPayloadEntity;
  constructor(private authRository: AuthRepository, private router: Router) {

  }

  login(username: String, password: String){
    this.authRository.login(username, password).subscribe({
      next: (v) => {
        const json = JSON.parse(JSON.stringify(v));
        this.saveToken(json['access_token']);

        if(this.jwtPayload.authorities?.includes('estagiario')){
          this.router.navigate([`estagiario/perfil/${this.jwtPayload.user_id}`])
        }

      },
      error: (e) => console.error(e),
      complete: () => console.info('login completed')
    });
  }

  private saveToken(token: string) {
    this.jwtPayload = JSON.parse(atob(token.split('.')[1]));

    localStorage.setItem('token', token);
  }

  private loadToken(){
    const token = localStorage.getItem('token')
  }

  public getUser(): number {
    return this.jwtPayload.user_id;
  }
}
