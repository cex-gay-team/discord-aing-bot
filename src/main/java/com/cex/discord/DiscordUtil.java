package com.cex.discord;

import com.cex.bot.fishing.command.DiscordBaseCommand;
import com.cex.bot.fishing.command.FishingBotNotCommand;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.apache.commons.lang3.ArrayUtils;

import javax.security.auth.login.LoginException;
import java.util.HashMap;
import java.util.Map;

public class DiscordUtil {
    private static final String[] INVALID_CHANNEL_NAMES = {"PRIVATE"};
    private static final String[] COMMAND_NAMES = {"rod", "ping", "bait", "location", "fishing", "buy", "sell"};
    public static final int PREFIX_LENGTH = 1;

    private JDABuilder jdaBuilder;
    private Map<String, DiscordBaseCommand> commandExecutorMap = new HashMap<>();

    private DiscordUtil() {
        jdaBuilder = JDABuilder.createDefault("Nzg2NTg2Mjg5NDk0NDI1NjAy.X9IjkQ.WtkWAvcsDw75QXOZxPzoutzB67s")
                .addEventListeners(new DiscordBotListener())
                .setStatus(OnlineStatus.ONLINE)
                .setAutoReconnect(true);
    }

    private static class InnerDiscordInitializerClazz {
        private static final DiscordUtil uniqueInstance = new DiscordUtil();
    }

    public static DiscordUtil getInstance() {
        return InnerDiscordInitializerClazz.uniqueInstance;
    }

    public void connect() {
        try {
            jdaBuilder.build();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }

    public void addCommandExecutor(String commandName, DiscordBaseCommand command) {
        commandExecutorMap.put(commandName, command);
    }

    public DiscordBaseCommand getCommandExecutor(String commandName) {
        return commandExecutorMap.getOrDefault(commandName, new FishingBotNotCommand());
    }

    public boolean isCommand(MessageReceivedEvent event) {
        User user = event.getAuthor();
        ChannelType channelType = event.getChannelType();
        Message message = event.getMessage();
        String command = message.getContentRaw().split(" ")[0];

        if (!(command.matches("^!\\w+") && ArrayUtils.contains(COMMAND_NAMES, command.substring(PREFIX_LENGTH)))) {
            return false;
        }

        if (user.isBot()) {
            return false;
        }

        if (ArrayUtils.contains(INVALID_CHANNEL_NAMES, channelType.name())) {
            return false;
        }

        return true;

    }
}
