package com.cex.discord;

import com.cex.bot.fishing.command.DiscordBaseCommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DiscordBotListener extends ListenerAdapter {
    private static final String COMMAND_PREFIX = "fishingBot";
    private static final String COMMAND_POSTFIX = "Command";
    @Autowired
    private Map<String, DiscordBaseCommand> commandMap;

    @Autowired
    private DiscordBaseCommand fishingBotNotCommand;

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        DiscordUtil discordUtil = DiscordUtil.getInstance();
        if (discordUtil.isCommand(event)) {
            String commandName = discordUtil.getCommand(event.getMessage().getContentRaw());

            DiscordBaseCommand command = commandMap.getOrDefault(COMMAND_PREFIX + commandName + COMMAND_POSTFIX, fishingBotNotCommand);

            command.execute(event);
        }
    }
}
