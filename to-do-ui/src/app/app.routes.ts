import {Routes} from '@angular/router';
import {authRoutes} from './pages/auth/auth.routes';
import {taskRoutes} from './pages/task/task.routes';
import {authGuard} from './guard/auth.guard';

export const routes: Routes = [
    {
        path: "",
        redirectTo: "task",
        pathMatch: 'full'
    },
    {
        // auth routes
        path: "auth",
        children: authRoutes
    },{
        path: "task",
        canActivateChild: [authGuard],
        children: taskRoutes,
    }
];
