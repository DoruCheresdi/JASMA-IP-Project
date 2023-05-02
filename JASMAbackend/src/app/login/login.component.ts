import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {AuthenticateService} from "../services/authenticate.service";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  credentials = {email: '', password: ''};

  constructor(private auth: AuthenticateService, private http: HttpClient, private router: Router) {
  }

    ngOnInit(): void {
      this.auth.authenticate(undefined, (isAuthenticated) => {
        if (isAuthenticated) {
            this.router.navigateByUrl('/feed');
        }
      })
    }

  login() {
    this.auth.authenticate(this.credentials, (isAuthenticated) => {
        if (isAuthenticated) {
            this.router.navigateByUrl('/feed');
        } else {
            // TODO: credentials wrong, throw error or smth:
        }
    });
    return false;
  }
}
