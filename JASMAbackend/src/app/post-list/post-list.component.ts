import {Component, OnInit} from '@angular/core';
import {Post} from "../entities/post";
import {UserService} from "../services/user-service.service";
import {HttpClient, HttpParams} from "@angular/common/http";
import {AuthenticateService} from "../services/authenticate.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-post-list',
  templateUrl: './post-list.component.html',
  styleUrls: ['./post-list.component.css']
})
export class PostListComponent implements OnInit {

  posts: Post[];
  constructor(private http: HttpClient,
              private router: Router,
              private auth: AuthenticateService) {
    this.posts = [];
  }

  ngOnInit() {

    // // if email is null:
    // if (!this.auth.isAuthenticated()) {
    //   this.router.navigateByUrl("/login");
    //   return;
    // }

    const params = new HttpParams()
      .set('userEmail', this.auth.email);

    this.http.get<Post[]>("/devapi/posts_user", {params: params}).subscribe(
      (posts: Post[]) => {
        this.posts = posts;
      }
    )
  }

  deletePost(title: string) {
      const params = new HttpParams()
          .set('title', title);

      console.log("before req" + this.auth.email);
      this.http.delete("/devapi/post", {params: params}).subscribe(
          (response: any) => {
              console.log("after req" + this.auth.email);
              // remove the post from this component's list so it is no longer shown:
              this.posts = this.posts.filter(p => {
                  return p.title !== title;
              })
              this.router.navigateByUrl("/profile");
          }
      )
  }
}
