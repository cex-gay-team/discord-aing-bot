import BaseCommand from './base/BaseCommand';
import {Message} from 'discord.js';
import UserValidator from '@validators/UserValidator';

class Ping extends BaseCommand {
    command = 'ping';
    validators = [new UserValidator('extracold1209')];

    async execute(message: Message): Promise<void> {
        await message.reply('Pong!');
        // await message.channel.send('PongPong Gay YA!');
    }
}

export default new Ping();
