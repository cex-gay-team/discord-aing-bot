import {encrypt} from '../src/utils/PinEncryptor';
import {IConfig} from '../config';
import inquirer from 'inquirer';

(async () => {
    const {env, pin, token} = await inquirer.prompt([
        {
            name: 'env',
            type: 'list',
            message: 'Select when token use',
            default: process.env.NODE_ENV === 'production' ? 'production' : 'development',
            choices: ['production', 'development'],
        },
        {
            name: 'pin',
            type: 'input',
            message: 'Input PIN to encrypt',
            validate: (input) => !!input,
            default:
                process.env.PIN ||
                process.env.pin ||
                process.argv.find((arg) => arg.startsWith('--pin'))?.replace('--pin=', ''),
        },
        {
            name: 'token',
            type: 'input',
            message: 'Input token to encrypt',
            validate: (input) => !!input
        }
    ]);

    process.env.NODE_ENV = env;
    const config: IConfig = (await import('../config')).default;

    const {tag, key} = config.secret;
    await encrypt(pin, token, key, tag);

    console.log('Encrypt Complete');
})();
