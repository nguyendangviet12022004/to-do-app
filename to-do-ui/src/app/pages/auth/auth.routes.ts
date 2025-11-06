import {Routes} from "@angular/router";
import {Login} from "./login/login";
import {Register} from "./register/register";
import {Activate} from "./activate/activate";
import {Oauth2Callback} from "./oauth2-callback/oauth2-callback";

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
    },{
        path: "oauth2/callback",
        component: Oauth2Callback
    }
]
