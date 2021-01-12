package org.harrydev.discordx.api;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.harrydev.discordx.api.bot.AbstractCommand;

import java.util.ArrayList;
import java.util.function.Consumer;

public class CommandClientBuilder {
    private ArrayList<AbstractCommand> commands = new ArrayList<>();
    private String prefix;
    private Consumer<MessageReceivedEvent> consumer;

    public CommandClient build()
    {
        return new CommandClientImpl(commands,prefix,consumer);
    }

    public CommandClientBuilder setPrefix(String prefix)
    {
        this.prefix = prefix;
        return this;
    }

    public CommandClientBuilder addCommand(AbstractCommand command)
    {
        commands.add(command);
        return this;
    }

    public CommandClientBuilder setConsumer(Consumer<MessageReceivedEvent> consumer)
    {
        this.consumer = consumer;
        return this;
    }
}
