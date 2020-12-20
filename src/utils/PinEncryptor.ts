import fs from 'fs-extra';
import crypto from 'crypto';

const SYMMETRIC_CIPHER_ALGORITHM = 'aes-128-gcm';

function deriveFromPin(pin: string) {
    if (!pin) {
        throw new Error('No PIN inserted');
    }

    const iv = crypto.pbkdf2Sync(pin, Buffer.from('salt'), 4096, 16, 'sha256');
    const secretKey = crypto.createSecretKey(iv);
    return {iv, secretKey};
}

export async function encrypt(pin: string, target: string, keyFilePath: string, tagFilePath: string): Promise<string> {
    const {secretKey, iv} = deriveFromPin(pin);

    const cipher = crypto.createCipheriv(SYMMETRIC_CIPHER_ALGORITHM, secretKey, iv);
    let encryptedData = cipher.update(target, 'utf8', 'hex');
    encryptedData += cipher.final('hex');

    const tag = cipher.getAuthTag();
    await Promise.all([
        fs.writeFile(keyFilePath, encryptedData),
        fs.writeFile(tagFilePath, tag),
    ]);

    return encryptedData;
}

export async function decrypt(pin: string, keyFilePath: string, tagFilePath: string): Promise<string> {
    const {secretKey, iv} = deriveFromPin(pin);

    if (!fs.existsSync(keyFilePath) || !fs.existsSync(tagFilePath)) {
        throw new Error('Key or Tag file not exist');
    }

    const [keyFileBuffer, cipherTag] = await Promise.all([
        fs.readFileSync(keyFilePath),
        fs.readFileSync(tagFilePath)
    ]);
    const keyFile = keyFileBuffer.toString();

    const decipher = crypto.createDecipheriv(SYMMETRIC_CIPHER_ALGORITHM, secretKey, iv);
    decipher.setAuthTag(cipherTag);

    let token = decipher.update(keyFile, 'hex', 'utf8');
    token += decipher.final('utf8');

    return token;
}
