import { Component } from '@angular/core';
import {User} from "../entities/user";
import {ActivatedRoute, Router} from "@angular/router";
import {UserService} from "../services/user-service.service";
import {catchError} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {Post} from "../entities/post";

@Component({
  selector: 'app-post-form',
  templateUrl: './post-form.component.html',
  styleUrls: ['./post-form.component.css']
})
export class PostFormComponent {
  post: Post;
  userMessage = ""

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private http: HttpClient) {
    this.post = new Post();
  }

  onSubmit() {
    this.userMessage = "Post is submitted";

    this.http.post("devapi/post", this.post).subscribe((response) => {
      this.router.navigateByUrl("/profile");
      });
  }
}
