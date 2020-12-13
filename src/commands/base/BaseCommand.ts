import type {Message} from 'discord.js';
import {BaseValidator} from '@validators/base/BaseValidator';

/**
 * Command 는 실질적으로 명령을 실행하고, 결과를 반환하는 작업을 한다.
 * 유닛단위 로직의 기준점이다.
 */
abstract class BaseCommand {
    abstract command: string | string[];
    validators: BaseValidator[] = [];

    abstract execute(message: Message): Promise<void>;
}

export type IBaseCommand = {
    command: string | string[];
    validators: BaseValidator[];
    execute(message: Message): Promise<void>;
}

export default BaseCommand;
