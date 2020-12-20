import {IConfig} from './index';
import path from 'path';

const config: IConfig = {
    secret: {
        key: path.join(__dirname, './secrets/key'),
        tag: path.join(__dirname, './secrets/tag'),
    }
};

export default config;
