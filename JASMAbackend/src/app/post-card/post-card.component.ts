import {Component, Input, OnInit} from '@angular/core';
import {FeedPost} from "../entities/feed-post";
import {HttpClient, HttpParams} from "@angular/common/http";
import {Router} from "@angular/router";
import {AuthenticateService} from "../services/authenticate.service";
import {Post} from "../entities/post";

@Component({
  selector: 'app-post-card',
  templateUrl: './post-card.component.html',
  styleUrls: ['./post-card.component.css']
})
export class PostCardComponent implements OnInit {

    @Input() feedPost : FeedPost = new FeedPost();

    constructor(private http: HttpClient,
                private router: Router,
                public auth: AuthenticateService) {
    }

    ngOnInit() {

        // console.log("is liked" + this.feedPost.isLikedByCurrentUser);
    }

    isLikedByCurrentUser() {
        // console.log(typeof(this.feedPost.isLikedByCurrentUser));
        // console.log(this.feedPost.isLikedByCurrentUser);
        if (this.feedPost.isLikedByCurrentUser === "true") return true;
        else return false
    }

    isSharedByCurrentUser() {
        // console.log(typeof(this.feedPost.isSharedByCurrentUser));
        // console.log(this.feedPost.isSharedByCurrentUser);
        if (this.feedPost.isSharedByCurrentUser === "true") return true;
        else return false
    }

    addLike() {
        // const body = {"postTitle" : this.feedPost.title}
        this.http.post("/devapi/like", this.feedPost.title).subscribe(
            (data: any) => {
                // actualize frontend:
                this.feedPost.isLikedByCurrentUser = "true";
                this.feedPost.numberLikes++;
            }
        )
    }

    addShare() {
        // const body = {"postTitle" : this.feedPost.title}
        this.http.post("/devapi/share", this.feedPost.title).subscribe(
            (data: any) => {
                // actualize frontend:
                this.feedPost.isSharedByCurrentUser = "true";
                this.feedPost.numberShares++;
            }
        )
    }

    deletePost(title: string) {
        const params = new HttpParams()
            .set('title', title);

        this.http.delete("/devapi/post", {params: params}).subscribe(
            (response: any) => {
                // remove the post from this component's list so it is no longer shown:
                this.feedPost = new FeedPost();
            }
        )
    }
}
