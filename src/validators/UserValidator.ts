import {Message} from 'discord.js';
import {MessageValidator} from '@validators/base/BaseValidator';

class UserValidator implements MessageValidator {
    private readonly username: string | string[];
    constructor(username: string | string[]) {
        this.username = username;
    }

    async validate(message: Message): Promise<boolean> {
        if (Array.isArray(this.username)) {
            return this.username.includes(message.author.username);
        } else {
            return message.author.username === this.username;
        }
    }
}

export default UserValidator;
