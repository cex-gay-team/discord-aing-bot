import {IBaseCommand} from './base/BaseCommand';
import TimeService from '@services/TimeService';
import type {Message} from 'discord.js';
import NotSupportCommandError from '@errors/NotSupportCommandError';

type TimerCommands = {
    type: string;
    timerName?: string;
    timeout?: number;
}

type StartTimerCommand = Required<Pick<TimerCommands, 'timeout'>> & Omit<TimerCommands, 'type'>;
type StopTimerCommand = Omit<TimerCommands, 'type' | 'timeout'>;

class Timer implements IBaseCommand {
    command = 'timer';
    validators = [];
    private supportSubCommands = ['start', 'stop'];

    /*
     * !timer name timeout || !timer timeout (name=default)
     */
    async execute(message: Message): Promise<void> {
        const timeInfo = this.parseMessage(message.content);
        switch (timeInfo.type) {
            case 'start': {
                this.startTimer(message, timeInfo as StartTimerCommand);
                break;
            }
            case 'stop': {
                this.stopTimer(message, timeInfo);
            }
        }
    }

    private startTimer(message: Message, timerInfo: StartTimerCommand) {
        TimeService.addTimer({
            username: message.author.username,
            timerName: timerInfo.timerName,
            timeout: timerInfo.timeout
        }, () => {
            message.reply(`${this.formatTimerName(timerInfo.timerName)}시간이 되었습니당!`);
        });
        message.reply(`${this.formatTimerName(timerInfo.timerName)}타이머를 시작합니다!`);
    }

    private stopTimer(message: Message, timerInfo: StopTimerCommand) {
        const timerCleared = TimeService.clearTimer(message.author.username, timerInfo.timerName);

        if (timerCleared) {
            message.reply(`${this.formatTimerName(timerInfo.timerName)}타이머를 종료했습니다.`);
        } else {
            message.reply('그런 타이머는 실행된 적이 없습니다.');
        }
    }

    private parseMessage(content: string): TimerCommands {
        const [, type, arg1, arg2] = content.split(' ');
        const result: Partial<TimerCommands> = {};

        if (!this.supportSubCommands.includes(type)) {
            throw new NotSupportCommandError({
                support: this.supportSubCommands,
            });
        }
        result.type = type;

        const numArg1 = parseInt(arg1); // timerName or timeout
        const numArg2 = parseInt(arg2); // timeout if arg1 is timerName
        if (Number.isNaN(numArg1)) {
            // arg1 이 문자열이라면, arg2 는 숫자만 가능하다.
            result.timerName = arg1;

            if (type === 'start' && !Number.isNaN(numArg2) && numArg2 >= 0) {
                // arg2 는 type 이 start 일 경우에만 적용된다.
                result.timeout = numArg2;
            }
        } else if (numArg1 >= 0) {
            // arg1 이 숫자라면, 이 값은 timeout 이다.
            result.timeout = numArg1;
        }

        return result as TimerCommands;
    }

    private formatTimerName(timerName?: string) {
        return timerName ? `${timerName} ` : '';
    }
}

export default new Timer();
