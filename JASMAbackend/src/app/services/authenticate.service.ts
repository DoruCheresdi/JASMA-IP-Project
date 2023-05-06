import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Router} from "@angular/router";

@Injectable({
    providedIn: 'root'
})
export class AuthenticateService {

    authenticated = false;

    email = ""

    private authenticateUrl: string;

    constructor(private http: HttpClient, private router: Router) {
        this.authenticateUrl = 'devapi/authenticated';
    }

    isAuthenticated() : boolean {
        if(this.authenticated && this.email !== undefined && this.email !== "") return true;
        // make sure user is not authenticated (garbage code ahead):
        // this.authenticate(undefined, undefined);
        console.log("not authenticated")
        return false;
    }

    authenticate(credentials: { email: any; password: any; } | undefined,
                 callback: ((isAuthenticated : boolean) => void) | undefined) {

        const headers = new HttpHeaders(credentials ? {
            authorization : 'Basic ' + btoa(credentials.email + ':' + credentials.password)
        } : {});

        this.http.get(this.authenticateUrl, {headers: headers}).subscribe((response: any) => {
            let isAuthenticated = false;
            if (response && Object.keys(response).find(key => key === "name")) {
                console.log("User is authenticated with email " + response.principal.user.email + " and name: " + response.name)
                isAuthenticated = true;
                this.authenticated = true;
                this.email = response.principal.user.email;
            } else {
                console.log("Authentication failed.")
                this.deauthenticate();
            }
            return callback && callback(isAuthenticated);
        });
    }

    deauthenticate() {
        // TODO server side logout seems to not be working(because of redirect to /login page because of webconfig)
        // TODO fix it later:
        this.http.post('logout', {}).subscribe(() => {
            console.log("User deauthenticated.")
            this.authenticated = false;
            this.email = "";
            this.router.navigateByUrl('/login');
        });
    }
}
