package com.cex.discord;

import com.cex.bot.fishing.command.DiscordBaseCommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class DiscordBotListener extends ListenerAdapter {
    private static final int FIRST_WORD = 0;

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        DiscordUtil discordUtil = DiscordUtil.getInstance();
        if(discordUtil.isCommand(event)) {
            String commandName = event.getMessage().getContentRaw().split(" ")[FIRST_WORD].substring(DiscordUtil.PREFIX_LENGTH);

            DiscordBaseCommand command = discordUtil.getCommandExecutor(commandName);

            command.execute(event);
        }
    }
}
