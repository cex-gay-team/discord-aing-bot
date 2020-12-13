export default class InvalidParameterError extends Error {
    constructor(message = 'Invalid Parameter') {
        super(message);
    }
}
