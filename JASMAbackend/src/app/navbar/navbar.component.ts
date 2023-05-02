import { Component } from '@angular/core';
import {finalize} from "rxjs";
import {AuthenticateService} from "../services/authenticate.service";
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {
  constructor(private app: AuthenticateService, private http: HttpClient) {
    // this.app.authenticate(undefined, undefined);
  }

  logout() {
      this.app.deauthenticate();
  }

  getUserEmail() {
      return this.app.email;
  }

  authenticated() { return this.app.isAuthenticated(); }
}
