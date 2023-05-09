import {Component, OnInit} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {AuthenticateService} from "../services/authenticate.service";
import {FormBuilder, FormGroup} from "@angular/forms";
import {FeedPost} from "../entities/feed-post";
import {UserDTO} from "../entities/user-dto";
import {User} from "../entities/user";

@Component({
    selector: 'app-profile',
    templateUrl: './profile.component.html',
    styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

    userDescription: string = "Buna buna."; // default description
    isEditingDescription: boolean = false; // flag to indicate if user is editing the description

    // https://blog.angular-university.io/angular-file-upload/
    fileName = '';

    user: UserDTO = new UserDTO();

    imageURL: string = ""

    constructor(private http: HttpClient, public fb: FormBuilder,
                private auth: AuthenticateService) {
    }

    ngOnInit(): void {
        const params = new HttpParams()
            .set('email', this.auth.email);

        this.http.get<UserDTO>("/devapi/user/details", {params: params}).subscribe(
            (userDTO: UserDTO) => {
                // don't show the user themselves in the list, use a filter:
                this.user = userDTO;
                this.imageURL = this.user.imageURL;
            }
        );
    }

    // event : Event
    onFileSelected(event: any){
        console.log("saving file");
        const file = event.target.files[0];
        if (file) {
            this.fileName = file.name;
            const formData = new FormData();
            formData.append("userimage", file);

            this.http.post("/devapi/user/process_img_edit", formData).subscribe();
        }
    }

    submitForm() {
        console.log("This ain't doing nothing")
    }

    deleteAccount() {

        this.http.post("/devapi/deleteuser", {}).subscribe(
            () => {
                this.auth.deauthenticate();
            }
        )
    }
    startEditingDescription() {
        this.isEditingDescription = true;
    }

    cancelEditingDescription() {
        this.isEditingDescription = false;
    }

    saveDescription() {
        // Code to save the user's updated description to the backend goes here.
        // For now, we'll just update the userDescription variable with the new value.
        this.isEditingDescription = false; // hide the edit form
    }

    viewFriends() {
        const popup = window.open('/devapi/friends', 'Friends', 'width=500,height=500');
        if (popup) {
            popup.onload = function() {
                const friendsList = popup.document.getElementById('friendsList');
                fetch('/devapi/friends', { credentials: 'include' })
                    .then(response => response.json())
                    .then(friends => {
                        const friendsHTML = friends.map((friend: { firstName: any; lastName: any; }) => `<li>${friend.firstName} ${friend.lastName}</li>`).join('');
                        // @ts-ignore
                        friendsList.innerHTML = friendsHTML;
                    });
            };
        } else {
            alert('Pop-ups are blocked in your browser. Please allow pop-ups for this website.');
        }
    }
    getUserEmail() {
        return this.auth.email;
    }
}
