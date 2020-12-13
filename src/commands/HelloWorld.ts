import {IBaseCommand} from './base/BaseCommand';
import type {Message} from 'discord.js';

class HelloWorld implements IBaseCommand {
    command = 'hello';
    validators = [];

    async execute(message: Message): Promise<void> {
        await message.channel.send('World!');
    }
}

export default new HelloWorld();
