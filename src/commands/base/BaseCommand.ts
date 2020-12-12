import type {Message} from 'discord.js';

abstract class BaseCommand {
    abstract command: string;
    abstract execute(message: Message): Promise<void>;
}

export default BaseCommand;
