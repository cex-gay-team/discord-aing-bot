import BaseCommand from './base/BaseCommand';
import type {Message} from 'discord.js';

class HelloWorld extends BaseCommand {
    command = '색스';

    async execute(message: Message): Promise<void> {
        await message.channel.send('색스다 색스!\n' +
            '(❁´▽`❁)');
    }

}

export default new HelloWorld();
