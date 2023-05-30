import {Component, OnInit} from '@angular/core';
import {UserDTO} from "../entities/user-dto";
import {UserService} from "../services/user-service.service";
import {HttpClient, HttpParams} from "@angular/common/http";
import {User} from "../entities/user";
import {Post} from "../entities/post";
import {AuthenticateService} from "../services/authenticate.service";

@Component({
    selector: 'app-private-message',
    templateUrl: './private-message.component.html',
    styleUrls: ['./private-message.component.css']
})
export class PrivateMessageComponent {

    userDTOs: UserDTO[] = [];

    userName: string = "";

    constructor(private http: HttpClient, private auth: AuthenticateService) {
    }

    ngOnInit() {

    }

}
