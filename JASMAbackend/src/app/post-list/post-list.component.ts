import {Component, OnInit} from '@angular/core';
import {Post} from "../entities/post";
import {UserService} from "../services/user-service.service";
import {HttpClient, HttpParams} from "@angular/common/http";
import {AuthenticateService} from "../services/authenticate.service";
import {Router} from "@angular/router";
import {FeedPost} from "../entities/feed-post";

@Component({
  selector: 'app-post-list',
  templateUrl: './post-list.component.html',
  styleUrls: ['./post-list.component.css']
})
export class PostListComponent implements OnInit {

  feedPosts: FeedPost[] = [];
  constructor(private http: HttpClient,
              private router: Router,
              private auth: AuthenticateService) {
  }

  ngOnInit() {

    const params = new HttpParams()
      .set('email', this.auth.email);

    this.http.get<FeedPost[]>("/devapi/posts_user_detailed", {params: params}).subscribe(
      (posts: FeedPost[]) => {
          // keep only this user's posts, throw out the shared ones:
          // posts.filter(post => {
          //     return post.authorEmail === this.auth.email;
          // })
        this.feedPosts = posts;
      }
    )
  }
}
