import {Component, OnInit} from '@angular/core';
import {UserDTO} from "../entities/user-dto";
import {UserService} from "../services/user-service.service";
import {HttpClient, HttpParams} from "@angular/common/http";
import {User} from "../entities/user";
import {Post} from "../entities/post";
import {AuthenticateService} from "../services/authenticate.service";

@Component({
    selector: 'app-messages',
    templateUrl: './messages.component.html',
    styleUrls: ['./messages.component.css']
})
export class MessagesComponent {

    userDTOs: UserDTO[] = [];

    userName: string = "";

    constructor(private http: HttpClient, private auth: AuthenticateService) {
    }

    ngOnInit() {

    }

    getList() {

        const params = new HttpParams()
            .set('userName', this.userName);
        this.http.get<UserDTO[]>("/devapi/user/searchbyname", {params: params}).subscribe(
            (userDTOs: UserDTO[]) => {
                // don't show the user themselves in the list, use a filter:
                this.userDTOs = userDTOs.filter(user => this.auth.email !== user.email);
            }
        );
    }

}
