import {Component, Input} from '@angular/core';
import {FeedPost} from "../feed-post";

@Component({
  selector: 'app-post-card',
  templateUrl: './post-card.component.html',
  styleUrls: ['./post-card.component.css']
})
export class PostCardComponent {

    @Input() feedPost : FeedPost = new FeedPost();

    addLike() {

    }

    addShare() {

    }
}
