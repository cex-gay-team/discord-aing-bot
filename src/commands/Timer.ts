import {IBaseCommand} from './base/BaseCommand';
import TimeService from '@services/TimeService';
import type {Message} from 'discord.js';
import NotSupportCommandError from '@errors/NotSupportCommandError';
import formatDate from '../functions/formatDate';
import InvalidParameterError from '@errors/InvalidParameterError';

type TimerCommands = {
    type: string;
    timerName?: string;
    timeout?: number;
}

type StartTimerCommand = Required<Pick<TimerCommands, 'timeout'>> & Omit<TimerCommands, 'type'>;
type StopTimerCommand = Omit<TimerCommands, 'type' | 'timeout'>;

const timerSubCommand = {
    start: '시작',
    stop: '정지',
    list: '리스트',
};

class Timer implements IBaseCommand {
    command = '타이머';
    validators = [];
    private supportSubCommands = Object.values(timerSubCommand);

    /*
     * !timer name timeout || !timer timeout (name=default)
     */
    async execute(message: Message): Promise<void> {
        const timeInfo = this.parseMessage(message.content);
        switch (timeInfo.type) {
            case timerSubCommand.start: {
                if (!timeInfo.timeout) {
                    throw new InvalidParameterError('타이머 시간이 잘못되었습니다');
                }
                this.start(message, timeInfo as StartTimerCommand);
                break;
            }
            case timerSubCommand.stop: {
                if (!timeInfo.timerName) {
                    throw new InvalidParameterError('타이머명을 제대로 입력해주세요');
                }
                this.stop(message, timeInfo);
                break;
            }
            case timerSubCommand.list: {
                this.list(message);
                break;
            }
        }
    }

    private start(message: Message, timerInfo: StartTimerCommand) {
        TimeService.addTimer({
            username: message.author.username,
            timerName: timerInfo.timerName,
            timeout: timerInfo.timeout
        }, () => {
            message.reply(`${this.formatTimerName(timerInfo.timerName)}시간이 되었습니당!`);
        });
        message.reply(`${this.formatTimerName(timerInfo.timerName)}타이머를 시작합니다!`);
    }

    private stop(message: Message, timerInfo: StopTimerCommand) {
        const timerCleared = TimeService.clearTimer(message.author.username, timerInfo.timerName);

        if (timerCleared) {
            message.reply(`${this.formatTimerName(timerInfo.timerName)}타이머를 종료했습니다.`);
        } else {
            message.reply('그런 타이머는 실행된 적이 없습니다.');
        }
    }

    private list(message: Message) {
        const timerList = TimeService.getTimerList(message.author.username);

        if (timerList.length) {
            message.reply(
                '실행중인 타이머는 아래와 같습니다.\n' +
                timerList.reduce<string>((result, [timerName, elapsed]) => {
                    result += `${timerName}: ${elapsed}초\n`;
                    return result;
                }, ''),
            );
        } else {
            message.reply('실행중인 타이머가 없습니다.');
        }
    }

    private parseMessage(content: string): TimerCommands {
        const [, type, maybeTimerName, ...args] = content.split(' ');
        if (!this.supportSubCommands.includes(type)) {
            throw new NotSupportCommandError({
                support: this.supportSubCommands,
            });
        }

        const result: TimerCommands = {type};
        if (!maybeTimerName) {
            return result as TimerCommands;
        }

        if (Number.isNaN(parseInt(maybeTimerName))) {
            result.timerName = maybeTimerName;
            result.timeout = formatDate(args);
        } else {
            result.timeout = formatDate([maybeTimerName, ...args]);
        }

        return result as TimerCommands;
    }

    private formatTimerName(timerName?: string) {
        return timerName ? `${timerName} ` : '';
    }
}

export default new Timer();
