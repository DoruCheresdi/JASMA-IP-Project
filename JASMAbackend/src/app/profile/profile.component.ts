import { Component } from '@angular/core';
import {Post} from "../entities/post";
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {AuthenticateService} from "../services/authenticate.service";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent {

    constructor(private http: HttpClient,
                private auth: AuthenticateService) {
    }
    deleteAccount() {

        this.http.post("/devapi/deleteuser", {}).subscribe(
            () => {
                this.auth.deauthenticate();
            }
        )
    }
}
