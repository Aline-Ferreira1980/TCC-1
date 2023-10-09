import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class AuthRepository{
  private clientAuth: String = "Y2VzZXAtYWdlbmRhLWZyb250ZW5kOnNlZ3JlZG9wYXJhYXBwZGVmcm9udGVuZA=="

  constructor(private http: HttpClient) { }

  login(username: String, password: String): Observable<Object>{
    const body = `grant_type=password&username=${username}&password=${password}`;
    const headers = new HttpHeaders({
      'Authorization': 'Basic ' + this.clientAuth,
      'Content-Type': 'application/x-www-form-urlencoded'
    });
    return this.http.post(`${environment.apiBaseURl}oauth/token`, body, { headers, withCredentials: true });
  }

  refreshToken(): Observable<Object>{
    const body: String = 'grant_type=refresh_token'
    const headers = new HttpHeaders({
      'Authorization': 'Basic ' + this.clientAuth,
      'Content-Type': 'application/x-www-form-urlencoded'
    });

    return this.http.post(`${environment.apiBaseURl}oauth/token`, body, { headers, withCredentials: true });
  }

  logout():Observable<Object>{
    return this.http.delete(`${environment.apiBaseURl}token/revoke`);
  }

  checkToken(): Observable<Object>{
    const body = `token=${localStorage.getItem("token")}`;
    const headers = new HttpHeaders({
      'Content-Type':'application/x-www-form-urlencoded'
    });

    return this.http
    .post(`${environment.apiBaseURl}}oauth/check_token`, body, { headers });
  }
}
