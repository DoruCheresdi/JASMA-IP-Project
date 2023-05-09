import { Component, OnInit } from '@angular/core';

interface Notification {
    id: number;
    fromUser: string;
    message: string;
    timeStamp: Date;
}

@Component({
    selector: 'app-notifications',
    templateUrl: './notifications.component.html',
    styleUrls: ['./notifications.component.css'],
})
export class NotificationsComponent implements OnInit {
    notifications: Notification[] = [
        {
            id: 1,
            fromUser: 'John',
            message: 'Sent you a friend request',
            timeStamp: new Date(),
        },
        {
            id: 2,
            fromUser: 'Sarah',
            message: 'Sent you a friend request',
            timeStamp: new Date(),
        },
        {
            id: 3,
            fromUser: 'Tom',
            message: 'Sent you a friend request',
            timeStamp: new Date(),
        },
    ];

    constructor() {}

    ngOnInit(): void {}

    acceptFriendRequest(notification: Notification) {
        // Handle accepting friend request here
        console.log(`Accepted friend request from ${notification.fromUser}`);
    }

    rejectFriendRequest(notification: Notification) {
        // Handle rejecting friend request here
        console.log(`Rejected friend request from ${notification.fromUser}`);
    }
}
