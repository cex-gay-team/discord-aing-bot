import type {Message} from 'discord.js';
import {BaseValidator} from '@validators/base/BaseValidator';

abstract class BaseCommand {
    abstract command: string | string[];
    validators: BaseValidator[] = [];

    abstract execute(message: Message): Promise<void>;
}

export default BaseCommand;
