import {Component, OnInit} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Post} from "../entities/post";
import {Router} from "@angular/router";
import {AuthenticateService} from "../services/authenticate.service";
import {FeedPost} from "../entities/feed-post";

@Component({
  selector: 'app-feed',
  templateUrl: './feed.component.html',
  styleUrls: ['./feed.component.css']
})
export class FeedComponent implements OnInit {

    feedPosts: FeedPost[];
    constructor(private http: HttpClient,
                private router: Router,
                private auth: AuthenticateService) {
        this.feedPosts = [];
    }

    ngOnInit() {

        // get FeedPosts:
        this.http.get<FeedPost[]>("/devapi/feed_posts").subscribe(
            (feedPosts: FeedPost[]) => {
                this.feedPosts = feedPosts;
            }
        )
    }
}
