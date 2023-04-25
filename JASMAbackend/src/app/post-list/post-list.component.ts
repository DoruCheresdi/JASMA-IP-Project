import { Component } from '@angular/core';
import {Post} from "../post";
import {UserService} from "../services/user-service.service";
import {HttpClient, HttpParams} from "@angular/common/http";
import {AuthenticateService} from "../services/authenticate.service";

@Component({
  selector: 'app-post-list',
  templateUrl: './post-list.component.html',
  styleUrls: ['./post-list.component.css']
})
export class PostListComponent {

  posts: Post[];
  constructor(private http: HttpClient, private auth: AuthenticateService) {
    this.posts = [];
  }

  ngOnInit() {

    const params = new HttpParams()
      .set('userEmail', this.auth.email);

    this.http.get<Post[]>("/devapi/posts_user", {params: params}).subscribe(
      (posts: Post[]) => {
        this.posts = posts;
      }
    )
  }
}
