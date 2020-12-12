import MessageGateway from './MessageGateway';
import BaseCommand from '../commands/base/BaseCommand';

describe('MessageGateway', function() {
    /*
     * 모든 commands 는 BaseCommand 를 상속받아야 한다.
     */
    test('initialize', async function() {
        const messageGateway = new MessageGateway();
        const modules: any[] = await messageGateway.initializeCommand();

        expect(modules).toBeInstanceOf(Array);
        modules.forEach((module) => {
            expect(module).toHaveProperty('command');
            expect(module).toHaveProperty('execute');
        });
    });
});
