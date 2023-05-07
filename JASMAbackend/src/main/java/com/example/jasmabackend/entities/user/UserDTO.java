package com.example.jasmabackend.entities.user;

import lombok.Data;

@Data
public class UserDTO {
    private String email;

    private String name;

    private boolean isFriend;

    // logged user has received a request from this user:
    // this user has sent a FR. to the logged in user:
    private boolean hasSentFriendRequest;

    // logged user has sent a request to this user:
    // this user has received a FR. from the logged in user:
    private boolean hasReceivedFriendRequest;
}
