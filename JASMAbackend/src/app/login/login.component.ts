import { Component } from '@angular/core';
import {Router} from "@angular/router";
import {AuthenticateService} from "../services/authenticate.service";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  credentials = {email: '', password: ''};

  constructor(private auth: AuthenticateService, private http: HttpClient, private router: Router) {
  }

  login() {
    this.auth.authenticate(this.credentials, () => {
      this.router.navigateByUrl('/');
    });
    return false;
  }
}
