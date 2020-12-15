package com.cex.bot.fishing.command;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.utils.AttachmentOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import java.io.File;


@Service
public class FishingBotPingCommand implements DiscordBaseCommand {
    @Autowired
    private ServletContext servletContext;

    @Override
    public void execute(MessageReceivedEvent event) {
        User user = event.getAuthor();
        TextChannel textChannel = event.getTextChannel();
        Message message = event.getMessage();
        File file = new File(servletContext.getRealPath("/WEB-INF/images/Test.jpg"));

        textChannel.sendFile(file, AttachmentOption.SPOILER).queue();


    }
}
