import Discord from 'discord.js';
import MessageGateway from '@core/MessageGateway';

const client = new Discord.Client();
export default (clientToken: string) => {
    const messageGateway = new MessageGateway();

    /*
    Client description

    App
    name: A-ING bot
    clientID: 787186403515170816
    clientSecret: -

    Bot
    token: in keyFile. keyFile is encrypted with PIN code
     */

    client.on('message', messageGateway.handleMessage.bind(messageGateway));
    client.on('ready', () => {
        console.log(`Logged in as ${client.user?.tag}!`);
    });

    client.login(clientToken).then(() => {
        // noinspection JSIgnoredPromiseFromCall
        messageGateway.initializeCommand();
    });
};
