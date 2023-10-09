import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {AuthService} from "../../security/auth.service";
import {delay, map, Observable, retry, shareReplay} from "rxjs";
import {DefaultResponse} from "./default-response";
import {environment} from "../../../environments/environment";


@Injectable({
    providedIn: 'root',
})
export class HttpService{

    constructor(private http: HttpClient,
                private service: AuthService) {}



    post<T>(
        url: String,
        body: any,
        useDefaultHeader: boolean = true,
        useFormData: boolean = false,
        newHeaders: HttpHeaders = new HttpHeaders()
    ): Observable<DefaultResponse<T>> {
        return this.request<T>(
            'POST',
            `${url}`,
            body,
            useDefaultHeader,
            useFormData,
            newHeaders
        );
    }

    put<T>(
        url: string,
        body: any,
        useDefaultHeader: boolean = true,
        useFormData: boolean = false
    ): Observable<DefaultResponse<T>> {
        return this.request<T>(
            'PUT',
            `${url}`,
            body,
            useDefaultHeader,
            useFormData
        );
    }

    patch<T>(url: String, body:any): Observable<DefaultResponse<T>> {
        return this.request<T>('PATCH', `${url}`, body);
    }

    get<T>(url: String): Observable<DefaultResponse<T>> {
        return this.request<T>('GET', `${url}`);
    }

    delete<T>(url: String, id: number): Observable<DefaultResponse<T>> {
        return this.request<T>('DELETE', `${url}`, { id });
    }

    private request<T>(
        type: string,
        url: string,
        body: any = null,
        useDefaultHeader: boolean = true,
        useFormData: boolean = false,
        newHeaders: HttpHeaders = new HttpHeaders()
    ): Observable<DefaultResponse<T>> {

        let headers: HttpHeaders;
        headers = newHeaders || this.getDefaultHeader(useFormData);

        if (environment.logRequest) {
            console.dir({ type, url, headers, body });
        }

        if (environment.traceRequest) {
            // tslint:disable-next-line: no-console
            console.trace('trace');
        }
        return this.http
            .request<T>(type, url, {
                body,
                headers
            })
            .pipe(
                shareReplay(),
                retry(0),
                delay(500),
                map((data) => this.onsuccess<T>(type, data))
            );
    }

    private getDefaultHeader(useFormData: boolean = false) {
        const token: string | null = localStorage.getItem('token');
        if(token){
            const headers = new HttpHeaders({'Authorization':'Bearer '+ token});
            return headers;
        }
        return  null;
    }

    private onsuccess<T>(type: string, data: T) {
        const result = new DefaultResponse<T>();
        result.success(type, data);
        return result;
    }

    private oncatch<T>(payload: any) {
        const result = new DefaultResponse<T>();
        result.error(payload);
        return result;
    }

    getData(url: string): Observable<any> {
        return this.http.get<Response>(url).pipe(map(this.extractData));
    }

    private extractData(res: Response) {
        return res || {};
    }


}
