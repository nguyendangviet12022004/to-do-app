import { Routes } from "@angular/router";
import { Login } from "./login/login";
import { Register } from "./register/register";
import { Activate } from "./activate/activate";

export const authRoutes: Routes = [
    {
        path: "login",
        component: Login
    },
    {
        path: "register",
        component: Register
    },
    {
        path: "activate",
        component: Activate
    }
]