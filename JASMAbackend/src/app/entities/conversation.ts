export class Conversation {

    lastMessageContent: string;

    lastMessageSenderEmail: string;

    createdAt: number;

    createdAtString : string;

    user1Email : string;

    user2Email : string;

    constructor(lastMessageContent: string, lastMessageSenderEmail: string, createdAt: number, createdAtString: string, user1Email: string, user2Email: string) {
        this.lastMessageContent = lastMessageContent;
        this.lastMessageSenderEmail = lastMessageSenderEmail;
        this.createdAt = createdAt;
        this.createdAtString = createdAtString;
        this.user1Email = user1Email;
        this.user2Email = user2Email;
    }
}

