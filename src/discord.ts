import Discord from 'discord.js';

const client = new Discord.Client();

export default (clientToken: string) => {
    /*
    Client description

    App
    name: A-ING bot
    clientID: 787186403515170816
    clientSecret: -

    Bot
    token: in keyFile. keyFile is encrypted with PIN code
     */

    client.on('message', async msg => {
        if (msg.content === 'ping') {
            await msg.reply('Pong!');
            await msg.channel.send('PongPong Gay YA!');
        }
    });

    client.on('ready', () => {
        console.log(`Logged in as ${client.user?.tag}!`);
    });

    client.login(clientToken);
}
