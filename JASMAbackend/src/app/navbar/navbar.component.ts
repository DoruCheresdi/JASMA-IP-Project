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
  constructor(private app: AuthenticateService, private http: HttpClient, private router: Router) {
    // this.app.authenticate(undefined, undefined);
  }

  logout() {
      this.app.deauthenticate();
      this.router.navigateByUrl('/login');
      // TODO server side logout seems to not be working(because of redirect to /login page because of webconfig)
      // TODO fix it later:
    this.http.post('logout', {}).subscribe(() => {
        // this.app.deauthenticate();
        this.router.navigateByUrl('/login');
    });
  }

  getUserEmail() {
      return this.app.email;
  }

  authenticated() { return this.app.isAuthenticated(); }
}
