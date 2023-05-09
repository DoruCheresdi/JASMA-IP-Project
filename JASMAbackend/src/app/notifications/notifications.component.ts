// Importing necessary modules
import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

// Creating a User class with required properties
class User {
    constructor(public id: number, public email: string, public message: string, public timeStamp: Date) {}
}

// Defining the component and its properties
@Component({
    selector: 'app-notifications',
    templateUrl: './notifications.component.html',
    styleUrls: ['./notifications.component.css'],
})
export class NotificationsComponent implements OnInit {
    friendRequests: User[] = []; // Initializing friendRequests array with User type

    // Injecting HttpClient module in the component constructor
    constructor(private http: HttpClient) {}

    // OnInit lifecycle hook
    ngOnInit(): void {
        this.getFriendRequests(); // calling getFriendRequests function
    }

    // Function to get friend requests from the server
    getFriendRequests() {
        // Calling HTTP GET method to fetch friend requests from server
        this.http.get<User[]>('/devapi/friendRequests').subscribe(users => {
            this.friendRequests = users; // Setting fetched users to friendRequests array
        });
    }

    // Function to accept a friend request
    acceptFriendRequest(user: User) {
        // Calling HTTP POST method to accept a friend request
        this.http.post<void>(`/devapi/friendRequest/accept`, user.email).subscribe(() => {
            console.log(`Accepted friend request from ${user.email}`); // Logging message to console
            this.getFriendRequests(); // calling getFriendRequests function after successful response
        });
    }

    // Function to reject a friend request
    rejectFriendRequest(user: User) {
        // Calling HTTP POST method to reject a friend request
        this.http.post<void>(`/devapi/friendRequest/reject`, user.email).subscribe(() => {
            console.log(`Rejected friend request from ${user.email}`); // Logging message to console
            this.getFriendRequests(); // calling getFriendRequests function after successful response
        });
    }
}
