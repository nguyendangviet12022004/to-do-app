import {HttpEvent, HttpHandlerFn, HttpRequest} from "@angular/common/http";
import {inject} from "@angular/core";
import {Observable} from "rxjs";
import {AuthService} from "../services/auth.service";


export function AuthInterceptor(req: HttpRequest<unknown>, next: HttpHandlerFn): Observable<HttpEvent<unknown>> {
    const authService = inject(AuthService)

    const accessToken = authService.accessToken

    if(accessToken){
        const authorizationHeader = "Bearer " + accessToken;

        req = req.clone({
            headers: req.headers.set("Authorization", authorizationHeader)
        })
    }


  return next(req);
}
