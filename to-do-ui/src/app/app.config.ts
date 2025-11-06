import {ApplicationConfig, provideBrowserGlobalErrorListeners, provideZonelessChangeDetection} from '@angular/core';
import {provideRouter, withComponentInputBinding} from '@angular/router';

import {routes} from './app.routes';
import {provideHttpClient, withFetch, withInterceptors} from '@angular/common/http';
import {AuthInterceptor} from './interceptors/auth.interceptor';
import {AuthExceptionInterceptor} from './interceptors/auth.exception.interceptor';

export const appConfig: ApplicationConfig = {
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideZonelessChangeDetection(),
    provideRouter(routes,withComponentInputBinding()),
    provideHttpClient(withFetch(),withInterceptors([AuthInterceptor, AuthExceptionInterceptor]))
  ]
};
