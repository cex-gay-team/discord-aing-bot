import discordBot from './discord';
import {decrypt} from './utils/PinEncryptor';
import config from '../config';
import getValueFromEnvArgs from './functions/getValueFromEnvArgs';

const pin = getValueFromEnvArgs('pin');

// console.log('pin', config);
const {key, tag} = config.secret;
if (!pin) {
    console.error('PIN must be present');
    process.exit();
} else {
    decrypt(pin, key, tag).then((token) => {
        discordBot(token);
    }).catch((e) => {
        console.error(e);
        process.exit();
    });
}
