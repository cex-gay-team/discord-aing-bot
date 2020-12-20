import _glob from 'glob';
import {promisify} from 'util';
import path from 'path';
import type {Message} from 'discord.js';
import type BaseCommand from '@commands/base/BaseCommand';
import {MessageValidator} from '@validators/base/BaseValidator';

const glob = promisify(_glob);
const PREFIX_LENGTH_LIMIT = 3; // prefix 가 너무 길면 좀 그렇잖아

class MessageGateway {
    private _prefix = '!';
    private commands: { [command: string]: BaseCommand } = {};

    get prefix() {
        return this._prefix;
    }

    set prefix(prefix: string) {
        if (prefix.length >= PREFIX_LENGTH_LIMIT) {
            console.warn('prefix is too long. it should be under 3 characters');
            return;
        }

        // [workaround] prefix 로 특수문자만 허용한다
        if (!prefix.match(/^[\W|_]+/g)) {
            console.warn('prefix must be special characters');
            return;
        }

        this._prefix = prefix;
    }

    public async initializeCommand() {
        const commandsDirPathGlob = path.join(__dirname, '../commands', '*.ts');
        const modulePaths = await glob(commandsDirPathGlob);

        // eslint-disable-next-line @typescript-eslint/no-explicit-any
        const esModules = await Promise.all(modulePaths.map<any>((modulePath) => import(modulePath)));
        const modules: BaseCommand[] = esModules.map((esModule) => esModule.default);

        modules.forEach((commandModule) => {
            if (Array.isArray(commandModule.command)) {
                commandModule.command.forEach((eachCommand) => {
                    this.commands[eachCommand] = commandModule;
                });
            } else {
                this.commands[commandModule.command] = commandModule;
            }
        });

        return modules; // for test
    }

    public async handleMessage(message: Message) {
        if (message.content.startsWith(this.prefix)) {
            const [commandMessage] = message.content.slice(this.prefix.length).split(' ');
            const command = this.commands[commandMessage];
            if (command) {
                try {
                    const nonPrefixMessage = this.getNonPrefixMessage(message);
                    const chainCompleted = await this.processValidatorChain(nonPrefixMessage, command.validators);
                    chainCompleted && (await command.execute(nonPrefixMessage));
                } catch (e) {
                    message.reply(e.message);
                }
            }
        }
    }

    private async processValidatorChain(target: Message, validators: MessageValidator[]): Promise<boolean> {
        for (let i = 0; i < validators.length; i++) {
            const validator = validators[i];
            const validationResult = await new Promise<boolean>((resolve) => {
                validator.validate(target).then(resolve);
            });

            if (!validationResult) {
                return false;
            }
        }
        return true;
    }

    // 실제로 데이터는 call by reference 라 원본도 바뀌겠지만, 이 로직 이후 prefix 를 통한 사용은 없어야한다.
    private getNonPrefixMessage(message: Message) {
        message.content = message.content.slice(this.prefix.length);
        return message;
    }
}

export default MessageGateway;
