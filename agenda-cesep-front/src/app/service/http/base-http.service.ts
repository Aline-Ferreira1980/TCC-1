import {HttpService} from "./http.service";
import {Injectable} from "@angular/core";
import {HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {DefaultResponse} from "./default-response";


@Injectable({
    providedIn: 'root',
})
export class BaseHttpService {
    constructor(private _http: HttpService) {}

    post<T>(
        url: string,
        body: any,
        useDefaultHeader: boolean = true,
        useFormData: boolean = false,
        newHeaders: HttpHeaders = new HttpHeaders()
    ): Observable<DefaultResponse<T>> {
        return this._http.post<T>(url, body, useDefaultHeader, useFormData, newHeaders);
    }

    put<T>(url: string,
           body: any,
           useDefaultHeader: boolean = true,
           useFormData: boolean = false): Observable<DefaultResponse<T>> {
        return this._http.put<T>(url, body, useDefaultHeader, useFormData);
    }

    patch<T>(url: string, body: any): Observable<DefaultResponse<T>> {
        return this._http.patch<T>(url, body);
    }

    get<T>(url: string): Observable<DefaultResponse<T>> {
        return this._http.get<T>(`${url}`);
    }

    getById<T>(url: string, id: number): Observable<DefaultResponse<T>> {
        return this._http.get<T>(`${url}/${id}`);
    }

    delete<T>(url: string, id: number): Observable<DefaultResponse<T>> {
        return this._http.delete<T>(url, id);
    }

    getData(url: string): Observable<any> {
        return this._http.getData(url);
    }
}
