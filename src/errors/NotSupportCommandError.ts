export default class NotSupportCommandError extends Error {
    constructor(payload?: { message?: string; support?: string[] }) {
        const {
            message = 'Not support command',
            support,
        } = payload || {};

        const supportCommandMessage = support?.reduce((result, current) => {
            return `${result}${current}, `;
        }, `${message}\nSupport Commands below\n`);

        super(supportCommandMessage || message);
    }
}
