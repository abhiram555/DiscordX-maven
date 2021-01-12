package org.harrydev.discordx.api;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.harrydev.discordx.api.bot.AbstractCommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;

public class CommandClientImpl extends CommandClient {
    private ArrayList<AbstractCommand> commands;
    private String prefix;
    private Consumer<Help> consumer;

    public CommandClientImpl(ArrayList<AbstractCommand> commands,String prefix,Consumer<Help> consumer)
    {
        this.commands = commands;
        this.prefix = prefix;
        this.consumer = consumer;
    }

    public void addCommand(AbstractCommand command)
    {
        commands.add(command);
    }

    public void onMessageReceived(MessageReceivedEvent event)
    {
        if(event.getAuthor().isBot())
        {
            return;
        }

        String[] parts = null;

        String rawcontent = event.getMessage().getContentRaw();

        if(parts == null && rawcontent.toLowerCase().startsWith(prefix.toLowerCase()))
        {
            parts = splitOnPrefixLength(rawcontent,prefix.length());
        }

        if(parts != null)
        {
            String name = parts[0];
            String args = parts[1]==null ? "" : parts[1];
            if(name.equalsIgnoreCase("help"))
            {
                if(consumer == null)
                {
                    return;
                }
                consumer.accept(new Help(event,commands));
            }

            invokeCommand(name,event,args);
        }
    }

    private void invokeCommand(String name,MessageReceivedEvent event,String args)
    {
        for(AbstractCommand command : commands)
        {
            if(command.getName().equals(name))
            {
                if(command.canuseArgs()) {
                    if (!command.getArgs().contains(args) || args.isEmpty()) {
                        event.getChannel().sendMessage("Invalid Use of command! Correct Usage " + prefix + command.getName() + " " + command.getArgs()).queue();
                        return;
                    }
                }
                command.execute(event,args);
            }
        }
    }
    private static String[] splitOnPrefixLength(String rawContent, int length)
    {
        return Arrays.copyOf(rawContent.substring(length).trim().split("\\s+", 2), 2);
    }
}
