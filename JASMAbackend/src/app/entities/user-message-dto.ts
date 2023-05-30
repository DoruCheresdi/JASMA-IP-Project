export class UserMessageDTO {

    content: string;

    createdAt: number;

    createdAtString : string;

    senderEmail : string;

    receiverEmail : string;

    constructor(content: string, createdAt: number, createdAtString: string, senderEmail: string, receiverEmail: string) {
        this.content = content;
        this.createdAt = createdAt;
        this.createdAtString = createdAtString;
        this.senderEmail = senderEmail;
        this.receiverEmail = receiverEmail;
    }
}

