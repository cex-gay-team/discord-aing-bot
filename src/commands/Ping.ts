import {IBaseCommand} from './base/BaseCommand';
import {Message} from 'discord.js';
import UserValidator from '@validators/UserValidator';

class Ping implements IBaseCommand {
    command = 'ping';
    validators = [new UserValidator('extracold1209')];

    async execute(message: Message): Promise<void> {
        await message.reply('Pong!');
        // await message.channel.send('PongPong Gay YA!');
    }
}

export default new Ping();
