package com.cex.discord;

import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.apache.commons.lang3.ArrayUtils;

public class DiscordUtil {
    private static final String[] INVALID_CHANNEL_NAMES = {"PRIVATE"};
    private static final String[] COMMAND_NAMES = {"rod", "ping", "bait", "location", "fishing", "buy", "sell"};
    public static final int PREFIX_LENGTH = 1;

    private DiscordUtil() {
    }

    private static class InnerDiscordInitializerClazz {
        private static final DiscordUtil uniqueInstance = new DiscordUtil();
    }

    public static DiscordUtil getInstance() {
        return InnerDiscordInitializerClazz.uniqueInstance;
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

    public String getCommand(String contentRaw) {
        return contentRaw.substring(PREFIX_LENGTH, PREFIX_LENGTH + 1).toUpperCase() + contentRaw.substring(PREFIX_LENGTH + 1).toLowerCase();
    }
}
