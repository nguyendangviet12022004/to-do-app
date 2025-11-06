import {HttpEvent, HttpHandlerFn, HttpRequest} from "@angular/common/http";
import {inject} from "@angular/core";
import {catchError, Observable, switchMap, throwError} from "rxjs";
import {AuthService} from "../services/auth.service";
import {errorCode} from "../constants/ErrorCode";
import {Router} from "@angular/router";


export function AuthExceptionInterceptor(req: HttpRequest<unknown>, next: HttpHandlerFn): Observable<HttpEvent<unknown>> {

    const authService = inject(AuthService)
    const router = inject(Router)

    return next(req).pipe(
        catchError((error) => {
            const code = error.error.code;

            switch(code){
                case(errorCode.TOKEN_INVALID):
                    authService.logout();
                    router.navigate(["auth","login"])
                    return throwError(() => error)
                case (errorCode.TOKEN_EXPIRED):
                    return authService.refresh().pipe(
                        switchMap((data) => {
                             const clonedReq = req.clone({
                                setHeaders: {
                                Authorization: `Bearer ${data.refreshToken}`
                                }
                            });

                            return next(clonedReq)
                        }),
                        catchError((error) => {
                            authService.logout();
                        router.navigate(["auth","login"])
                        return throwError(() => error)
                        })
                    )
                default:
                    return throwError(() => error)

            }
    }));
}
