import { Component } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {AuthenticateService} from "../services/authenticate.service";
import {FormBuilder, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent {

    // https://blog.angular-university.io/angular-file-upload/
    fileName = '';

    constructor(private http: HttpClient, public fb: FormBuilder,
                private auth: AuthenticateService) {
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
}
