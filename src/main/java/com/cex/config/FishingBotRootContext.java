package com.cex.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = {"com.cex.bot"})
@Import({DiscordConfig.class})
public class FishingBotRootContext {
}
