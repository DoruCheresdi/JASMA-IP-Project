export class FeedComment {

    authorEmail: string;
    text: string;
    sinceCreatedString: string;
    constructor(authorEmail: string, text: string, sinceCreatedString: string) {
        this.authorEmail = authorEmail;
        this.text = text;
        this.sinceCreatedString = sinceCreatedString;
    }


}
