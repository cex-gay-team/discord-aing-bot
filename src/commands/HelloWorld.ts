import BaseCommand from './base/BaseCommand';
import type {Message} from 'discord.js';

class HelloWorld extends BaseCommand {
    command = 'hello';

    async execute(message: Message): Promise<void> {
        await message.channel.send('World!');
    }

}

export default new HelloWorld();
