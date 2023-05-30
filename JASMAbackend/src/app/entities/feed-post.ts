import {FeedComment} from "./feed-comment";

export class FeedPost {
    title: string = '';
    description: string = '';
    sinceCreatedString: string = '';
    authorEmail: string = '';
    numberLikes = 0;
    isLikedByCurrentUser = '';
    numberShares = 0;
    isSharedByCurrentUser = '';
    comments: FeedComment[] = [];
    numberComments = 0;
    imageName: string = "";
    videoName: string = "";

}
