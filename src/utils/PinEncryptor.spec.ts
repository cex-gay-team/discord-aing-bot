import {encrypt, decrypt} from './PinEncryptor';
import fs from 'fs-extra';

describe('PinEncryptor', function () {
    const DUMMY_PIN = '123456';
    const DUMMY_TARGET = 'helloworld';
    const TARGET_KEYFILE_PATH = './keyFileTest';
    const TARGET_TAGFILE_PATH = './tagTest';


    test('encrypt test', async function () {
        await encrypt(DUMMY_PIN, DUMMY_TARGET, TARGET_KEYFILE_PATH, TARGET_TAGFILE_PATH);

        expect(fs.existsSync(TARGET_TAGFILE_PATH)).toBeTruthy();
        expect(fs.existsSync(TARGET_KEYFILE_PATH)).toBeTruthy();
    });

    test('decrypt test', async function () {
        const decryptedTarget = await decrypt(DUMMY_PIN, TARGET_KEYFILE_PATH, TARGET_TAGFILE_PATH);

        expect(decryptedTarget).toEqual(DUMMY_TARGET);
    });

    afterAll(function () {
        fs.existsSync(TARGET_KEYFILE_PATH) && fs.removeSync(TARGET_KEYFILE_PATH);
        fs.existsSync(TARGET_TAGFILE_PATH) && fs.removeSync(TARGET_TAGFILE_PATH);
    });
});
