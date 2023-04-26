import { Component } from '@angular/core';
import {AuthenticateService} from "../services/authenticate.service";
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {catchError} from "rxjs";

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent {

    credentials = {email: '', password: ''};

    constructor(private http: HttpClient, private router: Router) {
    }

    changePass() {
        this.http.post("devapi/changepassword", {newPassword: this.credentials.password,
            userEmail: this.credentials.email})
            .subscribe((response) => {
                this.router.navigateByUrl("/login");
            });
    }

}
