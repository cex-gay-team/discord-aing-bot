import {Message} from 'discord.js';

export type BaseValidator<T = never> = {
    validate(arg: T): Promise<boolean>;
}

export type MessageValidator = BaseValidator<Message>;
