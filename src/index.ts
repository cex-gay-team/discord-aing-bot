import crypto from 'crypto';
import fs from 'fs';
import path from 'path';
import discordBot from './discord';

// ex) --key=hello -> 'key', 'hello'
function findKeyFromArgs(targetKeyName: string) {
    const keyNameLowerCase = targetKeyName.toLowerCase();
    for (let i = 0; i < process.argv.length; i++) {
        const [key, value] = process.argv[i].split('=').map((token) => token.replace(' ', ''));
        if (key.toLowerCase().replace('--', '') === keyNameLowerCase) {
            return value;
        }
    }
}

function decipherBotToken() {
    const keyFilePath = path.join(__dirname, '../key');
    const tagFilePath = path.join(__dirname, '../tag');
    const pin = process.env.PIN || findKeyFromArgs('pin');

    if (!pin || !fs.existsSync(keyFilePath) || !fs.existsSync(tagFilePath)) {
        console.error('PIN Code or keyFile not found!');
        return process.exit();
    }

    const derivationKey = crypto.pbkdf2Sync(pin, Buffer.from('salt'), 4096, 16, 'sha256');
    const secretKey = crypto.createSecretKey(derivationKey);
    const keyFile = fs.readFileSync(keyFilePath).toString();
    const cipherTag = fs.readFileSync(tagFilePath);

    const symmetricCipher = crypto.createDecipheriv('aes-128-gcm', secretKey, derivationKey);
    symmetricCipher.setAuthTag(cipherTag);

    let token = symmetricCipher.update(keyFile, 'hex', 'utf8');
    token += symmetricCipher.final('utf8');

    return token;
}

discordBot(decipherBotToken());
