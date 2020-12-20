import {IConfig} from './index';
import path from 'path';

const config: IConfig = {
    secret: {
        key: path.join(__dirname, './secrets/key-dev'),
        tag: path.join(__dirname, './secrets/tag-dev'),
    }
};

export default config;
