package com.cex.config;

import com.cex.discord.DiscordBotListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.security.auth.login.LoginException;

@Configuration
public class DiscordConfig {
    @Bean
    public ListenerAdapter listenerAdapter() {
        return new DiscordBotListener();
    }

    @Bean
    public JDABuilder discordJdaBuilder() {
        return JDABuilder.createDefault("Nzg2NTg2Mjg5NDk0NDI1NjAy.X9IjkQ.hkeo19uedzzWZ7eLU7ZYCwfrr78")
                .addEventListeners(listenerAdapter())
                .setStatus(OnlineStatus.ONLINE)
                .setAutoReconnect(true);
    }
    @Bean
    public JDA discordJDA() throws LoginException {
        return discordJdaBuilder().build();
    }
}
