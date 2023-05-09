import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {switchMap} from "rxjs";
import {HttpClient, HttpParams} from "@angular/common/http";
import {UserDTO} from "../entities/user-dto";
import {FeedPost} from "../entities/feed-post";
import {AuthenticateService} from "../services/authenticate.service";

@Component({
    selector: 'app-other-user-profile',
    templateUrl: './other-user-profile.component.html',
    styleUrls: ['./other-user-profile.component.css']
})
export class OtherUserProfileComponent implements OnInit {

    constructor(private route: ActivatedRoute, private http: HttpClient,
                public auth : AuthenticateService, private router: Router) {

    }

    userEmail: string = "";

    user: UserDTO = new UserDTO();

    userPosts: FeedPost[] = [];

    isAdmin = false;

    ngOnInit() {
        this.route.queryParams.subscribe(params => {
                this.userEmail = params["email"];
                this.getUserData();
            }
        );
    }

    getUserData() {
        console.log("sending request for user data");

        const params = new HttpParams()
            .set('email', this.userEmail);

        this.http.get<UserDTO>("/devapi/user/details", {params: params}).subscribe(
            (userDTO: UserDTO) => {
                this.user = userDTO;
            }
        );

        this.http.get<boolean>("/devapi/is_admin", {params: params}).subscribe(
            (isAdmin: boolean) => {
                this.isAdmin = isAdmin;
            }
        );

        // get FeedPosts:
        this.http.get<FeedPost[]>("/devapi/posts_user_detailed", {params: params}).subscribe(
            (userPosts: FeedPost[]) => {
                this.userPosts = userPosts;
            }
        )
    }

    sendFriendRequest() {
        this.http.post("devapi/friendRequest", this.userEmail).subscribe((response) => {
            this.user.hasReceivedFriendRequest = true;
        });
    }

    acceptFriendRequest() {
        this.http.post("devapi/friendRequest/accept", this.userEmail).subscribe((response) => {
            this.user.hasSentFriendRequest = false;
            this.user.friend = true;
        });
    }

    refuseFriendRequest() {
        this.http.post("devapi/friendRequest/reject", this.userEmail).subscribe((response) => {
            this.user.hasSentFriendRequest = false;
            this.user.friend = false;
        });
    }

    makeAdmin() {
        this.http.post("devapi/make_admin", this.userEmail).subscribe((response) => {
            this.isAdmin = true;
        });
    }

    deleteUser() {

        this.http.post("/devapi/deleteuser", this.userEmail).subscribe(
            () => {
                this.router.navigateByUrl('/feed');
            }
        )
    }
}
