package org.harrydev.discordx.Bot;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.harrydev.discordx.Bot.Commands.*;
import org.harrydev.discordx.Bot.Events.DiscordMessage;
import org.harrydev.discordx.DiscordX;
import org.harrydev.discordx.Utils.Logger;
import org.harrydev.discordx.api.CommandClient;
import org.harrydev.discordx.api.CommandClientBuilder;
import org.harrydev.discordx.api.CommandClientImpl;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;

@SuppressWarnings("BooleanMethodIsAlwaysInverted")
public class bot {

    private static final DiscordX INSTANCE = DiscordX.getInstance();
    private static final String Token = INSTANCE.getConfig().getString("botToken");
    private static JDA jda;
    private static final String prefix = INSTANCE.getConfig().getString("botPrefix");
    public static boolean tokenIsValid;
    private static CommandClient commandmananger;

    public static void Start() {
        if(!CheckToken(Token)) {
            return;
        }
        Logger.info("Starting the discord bot.");
        JDABuilder jdaBuilder = JDABuilder.createDefault(Token);

        try {
            CommandClient commandClient = new CommandClientBuilder().setPrefix(prefix).setConsumer(
                    help -> {
                        StringBuilder commands = new StringBuilder();
                        bot.getListeners().forEach(listener -> {
                            commands.append("`" + listener.getClass().getSimpleName().replaceAll("Command", "") + "`\n");
                        });
                        EmbedBuilder embed = new EmbedBuilder();
                        embed.setTitle("About DiscordX").setColor(Color.ORANGE);
                        embed.setDescription("DiscordX is a Spigot plugin that connects Discord to Minecraft.");
                        embed.addField("__Commands:__", commands.toString(), false);
                        embed.addField("__Spigot resource:__", "https://example.com", false);
                        embed.addField("__Github repo:__", "https://github.com/hwalker928/DiscordX", false);
                        help.getChannel().sendMessage(embed.build()).queue();
                    }
            ).addCommand(new TestCommand()).build();
            commandmananger = commandClient;
            jdaBuilder.addEventListeners(new DiscordMessage());
            jdaBuilder.addEventListeners(commandClient);
            getListeners().forEach(jdaBuilder::addEventListeners);
            jdaBuilder.setActivity(Activity.playing("Minecraft"));
            jda = jdaBuilder.build();
            jda.awaitReady();
            Logger.info("The bot has started!");
            tokenIsValid = true;
            SendStartup();
        } catch (LoginException | InterruptedException e) {
            Logger.error(e.toString());
        }
    }
    public static void shutdown() {
        if(!CheckToken(Token)) {
            return;
        }
        jda.shutdownNow();
    }

    public static JDA getBot() {
        return jda;
    }

    public static String getPrefix() {
        return prefix;
    }

    public static void SendStartup() {
        EmbedBuilder eb = new EmbedBuilder().setDescription("Server started!").setColor(Color.GREEN);
        TextChannel channel = jda.getTextChannelById(INSTANCE.getConfig().getLong("chatChannel"));

        if(channel == null)
        {
            INSTANCE.getLogger().info("Unable to send startup message, chat channel is wrong");
            return;
        }
        Objects.requireNonNull(jda.getTextChannelById(INSTANCE.getConfig().getLong("chatChannel"))).sendMessage(eb.build()).queue();
    }

    public static void SendShutdown() {
        EmbedBuilder eb = new EmbedBuilder().setDescription("Server stopped!").setColor(Color.RED);
        Objects.requireNonNull(jda.getTextChannelById(INSTANCE.getConfig().getLong("chatChannel"))).sendMessage(eb.build()).queue();
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        shutdown();
                    }
                },
                2500
        );
    }

    public static boolean CheckToken(String Token) {
        Pattern tokenPattern = Pattern.compile("[a-zA-Z0-9\\-_.]{59}");
        if(Token.equals("TokenGoesHere")) {
            Logger.warn("Please Set the bot token in the config.yml!");
            tokenIsValid = false;
            return false;
        }
        if(!tokenPattern.matcher(Token).matches()){
            Logger.warn("Your Token is Incorrect or Malformed");
            tokenIsValid = false;
            return false;
        }
        return true;
    }

    public static CommandClient getCommandClient()
    {
        return commandmananger;
    }
    public static List<ListenerAdapter> getListeners() {
        return Arrays.asList(
                new PingCommand(),
                new ServerCommand(),
                new WhitelistCommand(),
                new ListCommand()
        );
    }
}
