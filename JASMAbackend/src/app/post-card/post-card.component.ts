import { Component, Input, OnInit } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Router } from '@angular/router';
import { AuthenticateService } from '../services/authenticate.service';
import { FeedPost } from '../entities/feed-post';
import { FeedComment } from '../entities/feed-comment';

@Component({
    selector: 'app-post-card',
    templateUrl: './post-card.component.html',
    styleUrls: ['./post-card.component.css']
})
export class PostCardComponent implements OnInit {

    @Input() feedPost: FeedPost = new FeedPost();
    comment: string = '';

    constructor(
        private http: HttpClient,
        private router: Router,
        public auth: AuthenticateService
    ) {}

    ngOnInit() {

        const params = new HttpParams()
            .set('postTitle', this.feedPost.title);

        // get comments:
        this.http.get<FeedComment[]>("/devapi/comments_post", {params: params}).subscribe(
            (comments: FeedComment[]) => {
                this.feedPost.comments = comments;
            }
        )
    }

    isLikedByCurrentUser() {
        if (this.feedPost.isLikedByCurrentUser === 'true') return true;
        else return false;
    }

    isSharedByCurrentUser() {
        if (this.feedPost.isSharedByCurrentUser === 'true') return true;
        else return false;
    }

    addLike() {
        this.http.post('/devapi/like', this.feedPost.title).subscribe((data: any) => {
            // actualize frontend:
            this.feedPost.isLikedByCurrentUser = 'true';
            this.feedPost.numberLikes++;
        });
    }

    addShare() {
        this.http.post('/devapi/share', this.feedPost.title).subscribe((data: any) => {
            // actualize frontend:
            this.feedPost.isSharedByCurrentUser = 'true';
            this.feedPost.numberShares++;
        });
    }

    addComment(comment: string) {
        const commentDto = {
            postTitle: this.feedPost.title,
            authorEmail: this.auth.email,
            text : comment
        };
        this.http.post('/devapi/comment', commentDto).subscribe(
            (data: any) => {
                // actualize frontend:
                this.feedPost.comments.push({
                    authorEmail: this.auth.email,
                    text: comment,
                    sinceCreatedString: 'just now'
                });
                this.feedPost.numberComments++;
                this.comment = '';
            }
        );
    }

    deletePost(title: string) {
        const params = new HttpParams().set('title', title);

        this.http.delete('/devapi/post', { params: params }).subscribe((response: any) => {
            // remove the post from this component's list so it is no longer shown:
            this.feedPost = new FeedPost();
        });
    }
}
