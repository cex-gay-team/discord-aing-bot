package com.cex.config;

import com.cex.bot.fishing.command.DiscordBaseCommand;
import com.cex.bot.fishing.command.FishingBotPingCommand;
import com.cex.discord.DiscordUtil;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import sun.plugin2.util.PojoUtil;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import java.io.File;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class FishingBotWebApplicationConfig implements WebApplicationInitializer {
    private static final String COMMAND_PACKAGE_NAME = "com.cex.bot.fishing.command";
    private static final String COMMAND_PREFIX = "FishingBot";
    private static final String COMMAND_POSTFIX = "Command";

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
        webContext.register(FishingBotWebContext.class);

        DispatcherServlet dispatcherServlet = new DispatcherServlet(webContext);
        ServletRegistration.Dynamic servlet = servletContext.addServlet("dispatcher", dispatcherServlet);

        servlet.addMapping("/");
        servlet.setLoadOnStartup(1);

        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(FishingBotRootContext.class);

        ContextLoaderListener listener = new ContextLoaderListener(rootContext);
        servletContext.addListener(listener);

        FilterRegistration.Dynamic filter = servletContext.addFilter("encodingFilter", CharacterEncodingFilter.class);
        filter.setInitParameter("encoding", "UTF-8");
        filter.addMappingForServletNames(null, false, "dispatcher");

        DiscordUtil discordUtil = DiscordUtil.getInstance();

        Set<Class> commandClasses= getClasses(COMMAND_PACKAGE_NAME);

        commandClasses.stream().forEach(commandClass -> {
            try {
                String className = commandClass.getName()
                        .split(COMMAND_PACKAGE_NAME)[1]
                        .split(COMMAND_PREFIX)[1]
                        .split(COMMAND_POSTFIX)[0]
                        .toLowerCase();
                discordUtil.addCommandExecutor(className, (DiscordBaseCommand) commandClass.newInstance());
            } catch (InstantiationException|IllegalAccessException e) {
                //TODO 로그를 찍어주자
            }
        });

        discordUtil.connect();

    }

    private static Set<Class> getClasses(String packageName){
        Set<Class> classes = new HashSet<>();
        String packageNameSlash = "./" + packageName.replace(".", "/");
        URL directoryURL = Thread.currentThread().getContextClassLoader().getResource(packageNameSlash);
        if(Objects.isNull(directoryURL)){
            return Collections.emptySet();
        }

        String directoryString = directoryURL.getFile();
        if(Objects.isNull(directoryString)){
            return Collections.emptySet();
        }

        File directory = new File(directoryString);

        if(directory.exists()){
            String[] files = directory.list();
            for(String fileName : files){
                if(fileName.endsWith(".class")){
                    fileName = fileName.substring(0, fileName.length() - 6);  // remove .class
                    try{
                        Class c = Class.forName(packageName + "." + fileName);
                        if(!Modifier.isAbstract(c.getModifiers())) // add a class which is not abstract
                            classes.add(c);
                    } catch (ClassNotFoundException e){
                        //TODO 로그를 찍어주자
                    }
                }
            }
        } else {
            return Collections.emptySet();
        }

        return classes;
    }
}
