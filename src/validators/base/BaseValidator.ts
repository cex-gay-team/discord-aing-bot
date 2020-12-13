import {Message} from 'discord.js';

/**
 * Validator 는 Command 가 실행되기 전에, 이 Command 를 실행할 자격이 있는 요청인지 확인하는 용도로 활용된다.
 * abstract
 */
export type BaseValidator<T = never> = {
    validate(arg: T): Promise<boolean>;
}

export type MessageValidator = BaseValidator<Message>;
