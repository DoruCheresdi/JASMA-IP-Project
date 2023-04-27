import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class AuthenticateService {

  authenticated = false;

  email = ""

  private authenticateUrl: string;

  constructor(private http: HttpClient) {
    this.authenticateUrl = 'devapi/authenticated';
  }

  isAuthenticated() : boolean {
      return this.authenticated && this.email !== "";
  }

  authenticate(credentials: { email: any; password: any; } | undefined,
               callback: { (): void; (): any; } | undefined) {

    const headers = new HttpHeaders(credentials ? {
      authorization : 'Basic ' + btoa(credentials.email + ':' + credentials.password)
    } : {});

    this.http.get(this.authenticateUrl, {headers: headers}).subscribe((response: any) => {
      if (response && Object.keys(response).find(key => key === "name")) {
        this.authenticated = true;
        this.email = credentials?.email;
      } else {
          console.log("Authentication failed.")
          this.deauthenticate();
      }
      return callback && callback();
    });
  }

  deauthenticate() {
      console.log("User deauthenticated.")
      this.authenticated = false;
      this.email = "";
  }
}
