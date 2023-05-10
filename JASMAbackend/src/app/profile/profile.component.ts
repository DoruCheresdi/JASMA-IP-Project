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

        this.http.post("/devapi/deleteuser", this.user.email).subscribe(
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
        // Send a GET request to the endpoint that retrieves the list of friends
        fetch('/devapi/friends')
            .then(response => response.json())
            .then(friendsList => {
                // Create an array of friend objects with their names and image URLs
                const friends = friendsList.map((friend: { name: string, imageURL: string }) => friend);

                // Build a HTML list of friend names and images
                const friendListItems = friends.map((friend: { name: string, imageURL: string }) => `<li><img alt="${friend.name}" src="${friend.imageURL}" class="img-thumbnail" style="width: 50px; height: 50px;"><br>${friend.name}</li>`).join('');
                const friendListHtml = `<ul class="friend-list">${friendListItems}</ul>`;

                // Create a modal element
                const modal = document.createElement('div');
                modal.classList.add('modal');

                // Create a content element inside the modal
                const modalContent = document.createElement('div');
                modalContent.classList.add('modal-content');

                // Create a header for the modal
                const modalHeader = document.createElement('h2');
                modalHeader.classList.add('modal-header');
                modalHeader.innerText = 'My Friends';

                // Create a container for the friend list
                const friendListContainer = document.createElement('div');
                friendListContainer.innerHTML = friendListHtml;

                // Create a close button for the modal
                const closeButton = document.createElement('span');
                closeButton.classList.add('close');
                closeButton.innerHTML = '&times;';
                closeButton.addEventListener('click', () => {
                    modal.style.display = 'none';
                });

                // Add the header and friend list to the content element
                modalContent.appendChild(modalHeader);
                modalContent.appendChild(friendListContainer);

                // Add the content element to the modal
                modal.appendChild(modalContent);

                // Add the close button to the modal
                modal.appendChild(closeButton);

                // Add the modal to the document body
                document.body.appendChild(modal);

                // Set the width and height of the modal
                modal.style.width = '600px';
                modal.style.height = '500px';

                // Center the modal vertically and horizontally
                modal.style.position = 'fixed';
                modal.style.left = '50%';
                modal.style.top = '50%';
                modal.style.transform = 'translate(-50%, -50%)';

                // Add a box shadow to the modal
                modal.style.boxShadow = '0 1px 1px 0 rgba(0,0,0,0.2)';

                // Add a background color to the modal header and close button
                modalHeader.style.backgroundColor = '#284942';
                modalHeader.style.color = 'white';
                closeButton.style.color = 'black';

                // Add some padding to the modal content
                modalContent.style.padding = '16px';

                // Style the close button
                closeButton.style.position = 'absolute';
                closeButton.style.top = '8px';
                closeButton.style.right = '16px';
                closeButton.style.fontSize = '24px';
                closeButton.style.fontWeight = 'bold';
                closeButton.style.cursor = 'pointer';

                // Show the modal
                modal.style.display = 'block';
            })
            .catch(error => {
                // Show an error message if the request fails
                alert('Failed to retrieve friends list');
            });
    }


    getUserEmail() {
        return this.auth.email;
    }
}
