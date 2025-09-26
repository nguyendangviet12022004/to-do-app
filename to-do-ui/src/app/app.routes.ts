import { Routes } from '@angular/router';
import { authRoutes } from './pages/auth/auth.routes';

export const routes: Routes = [

    {
        // auth routes
        path: "auth",
        children: authRoutes
    }
];
