import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { switchMap } from "rxjs";
import { HttpClient, HttpParams } from "@angular/common/http";
import { UserDTO } from "../entities/user-dto";
import { FeedPost } from "../entities/feed-post";
import { AuthenticateService } from "../services/authenticate.service";

@Component({
    selector: 'app-friend-list',
    templateUrl: './friend-list.component.html',
    styleUrls: ['./friend-list.component.css']
})
export class FriendListComponent implements OnInit {

    constructor(
        private route: ActivatedRoute,
        private http: HttpClient,
        public auth: AuthenticateService,
        private router: Router
    ) { }

    // Properties
    userEmail: string = "";
    user: UserDTO = new UserDTO();
    userPosts: FeedPost[] = [];
    isAdmin = false;
    friends: UserDTO[] = []; // Add a new property for storing the list of friends
    searchTerm: string = ""; // Add a property for the search term

    ngOnInit() {
        // Get the query parameters from the route
        this.route.queryParams.subscribe(params => {
            this.userEmail = params["email"];
            this.getUserData();
            this.getFriends(); // Call the method to fetch friends' data
        });
    }

    // Fetch user data
    getUserData() {
        console.log("sending request for user data");

        const params = new HttpParams().set('email', this.userEmail);

        // Get user details
        this.http.get<UserDTO>("/devapi/user/details", { params: params }).subscribe(
            (userDTO: UserDTO) => {
                this.user = userDTO;
            }
        );

        // Check if the user is an admin
        this.http.get<boolean>("/devapi/is_admin", { params: params }).subscribe(
            (isAdmin: boolean) => {
                this.isAdmin = isAdmin;
            }
        );
    }

    // Fetch friends' data
    getFriends() {
        console.log("sending request for friends data");

        const params = new HttpParams().set('email', this.userEmail);

        // Get friends list
        this.http.get<UserDTO[]>("/devapi/friends", { params: params }).subscribe(
            (friends: UserDTO[]) => {
                this.friends = friends;
            }
        );
    }

    removeFriend(friend: UserDTO) {
        this.http.post("/devapi/friendRequest/reject", friend).subscribe((response) => {
            this.user.hasSentFriendRequest = false;
            this.user.friend = false;
            this.getFriends(); // Refresh the friends' data after removing a friend
        });
    }

    // Filter friends based on the search term
    get filteredFriends() {
        return this.friends.filter(friend =>
            friend.name.toLowerCase().includes(this.searchTerm.toLowerCase())
        );
    }
}
