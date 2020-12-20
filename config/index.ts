export type IConfig = {
    secret: {
        key: string;
        tag: string;
    }
}

const MODE = process.env.NODE_ENV === 'production' ? 'production' : 'development';

// eslint-disable-next-line @typescript-eslint/no-var-requires
const module = require(`./${MODE}.config`).default;
export default module as IConfig;
