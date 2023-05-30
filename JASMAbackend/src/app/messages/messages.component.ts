import {Component, OnInit} from '@angular/core';
import {UserDTO} from "../entities/user-dto";
import {UserService} from "../services/user-service.service";
import {HttpClient, HttpParams} from "@angular/common/http";
import {User} from "../entities/user";
import {Post} from "../entities/post";
import {AuthenticateService} from "../services/authenticate.service";
import {UserMessageDTO} from "../entities/user-message-dto";
import {Conversation} from "../entities/conversation";

@Component({
    selector: 'app-messages',
    templateUrl: './messages.component.html',
    styleUrls: ['./messages.component.css']
})
export class MessagesComponent {

    userName: string = "";

    conversations: Conversation[] = [];

    conversationsForShowing : any[] = [];

    constructor(private http: HttpClient, private auth: AuthenticateService) {
    }

    ngOnInit() {
        this.getList();
    }

    getList() {
        this.http.get<Conversation[]>("/devapi/conversations").subscribe(
            (conversations: Conversation[]) => {
                this.conversations = conversations
                // TODO sort by last:
                console.log(JSON.stringify(this.conversations));
                this.conversationsForShowing = this.conversations.map(
                    (conversation : Conversation) => {
                        // check which of user1 or user2 is the other user(the one not authenticated)
                        if (conversation.user1Email === this.auth.email) {
                            return {
                                email : conversation.user2Email,
                                lastMessage : conversation.lastMessageContent,
                                createdAtString : conversation.createdAtString
                            }
                        } else {
                            return {
                                email : conversation.user1Email,
                                lastMessage : conversation.lastMessageContent,
                                createdAtString : conversation.createdAtString
                            }
                        }
                }
                );
                console.log(JSON.stringify(this.conversationsForShowing));
            }
        )
    }

}
