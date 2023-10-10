import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {BaseHttpService} from "../../service/http/base-http.service";
import {environment} from "../../../environments/environment";
import {EstagiarioEntity} from "../entity/estagiario-entity";

@Injectable({
  providedIn: 'root'
})
export class PacienteRepositoryService {
  private estagUrl = `${environment.apiBaseURl}estagiario`


  constructor(private http: BaseHttpService) { }

  getEstagiarioById(userID: number): Observable<EstagiarioEntity>{
    let resp =  this.http.getById<EstagiarioEntity>(this.estagUrl, userID)
      .pipe((x) => x)
    console.log(resp)
  }

}
