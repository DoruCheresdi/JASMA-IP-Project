import {Component, OnDestroy, OnInit} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {AuthenticateService} from "../services/authenticate.service";
import {ActivatedRoute} from "@angular/router";
import {UserMessageDTO} from "../entities/user-message-dto";
import {interval, switchMap, takeWhile} from "rxjs";

@Component({
    selector: 'app-private-message',
    templateUrl: './private-message.component.html',
    styleUrls: ['./private-message.component.css']
})
export class PrivateMessageComponent implements OnDestroy, OnInit{

    userEmail : string = "";

    messages : UserMessageDTO[] = [];

    newMessage = "";

    intervalId : any | undefined;

    constructor(private http: HttpClient, public auth: AuthenticateService, private route: ActivatedRoute) {
    }

    ngOnInit() {
        this.route.queryParams.subscribe(params => {
                console.log(params["email"]);
                this.userEmail = params["email"];
                // this.getMessages();


            // @ts-ignore
            // this.intervalId = setInterval(this.getMessages, 1000);
            setInterval(()=> { this.getMessages() }, 3 * 1000);
                // interval(500)
                //     .pipe(takeWhile(() => !stop))
                //     .subscribe(() => {
                //         // place you code here
                //         this.getMessages();
                //     });
            }
        );
        // this.heroes$ = this.route.paramMap.pipe(
        //     switchMap(params => {
        //         this.selectedId = Number(params.get('id'));
        //         return this.service.getHeroes();
        //     })
        // );
    }

    ngOnDestroy() {
        clearInterval(this.intervalId);
    }

    getMessages() {

        const params = new HttpParams()
            .set('user1Email', this.userEmail)
            .set('user2Email', this.auth.email);

        // Get user details
        this.http.get<UserMessageDTO[]>("/devapi/messages", { params: params }).subscribe(
            (userMessageDTOS: UserMessageDTO[]) => {
                this.messages = userMessageDTOS;
            }
        );


        // set timer to call this function again:
    }

    sendMessage() {

        this.http.post("/devapi/message", {
            receiverEmail : this.userEmail,
            content : this.newMessage
        }).subscribe(
            () => {
                this.getMessages();
            }
        )
    }
}
