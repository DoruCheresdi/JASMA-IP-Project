import { Component } from '@angular/core';
import {User} from "../user";
import {ActivatedRoute, Router} from "@angular/router";
import {UserService} from "../services/user-service.service";
import {catchError} from "rxjs";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-post-form',
  templateUrl: './post-form.component.html',
  styleUrls: ['./post-form.component.css']
})
export class PostFormComponent {
  post: any;
  userMessage = ""

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private http: HttpClient) {
    this.post = {}
  }

  onSubmit() {
    this.userMessage = "Post is submitted";

    this.http.post("devapi/post", this.user).pipe(
      catchError(this.handleError.bind(this))
    )
      .subscribe((response) => {
        this.app.authenticate({email: this.user.email, password: this.user.password}, () => {
          this.router.navigateByUrl("/home");
        });
      });
  }
}
