import {IBaseCommand} from './base/BaseCommand';
import type {Message} from 'discord.js';

class Cex implements IBaseCommand {
    command = '색스';
    validators = [];

    async execute(message: Message): Promise<void> {
        await message.channel.send('색스다 색스!\n' +
            '(❁´▽`❁)');
    }
}

export default new Cex();
