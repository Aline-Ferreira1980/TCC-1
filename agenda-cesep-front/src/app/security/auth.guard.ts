import {Injectable} from "@angular/core";
import {CanActivate} from "@angular/router";

@Injectable()
export class AuthGuard{
    canActivate(){
        return true
    }
}
