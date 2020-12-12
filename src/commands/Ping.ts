import BaseCommand from './base/BaseCommand';
import {Message} from 'discord.js';

class Ping extends BaseCommand {
    command = 'ping';

    async execute(message: Message): Promise<void> {
        await message.reply('Pong!');
        // await message.channel.send('PongPong Gay YA!');
    }
}

export default new Ping();
