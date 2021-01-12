package org.harrydev.discordx.api;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.harrydev.discordx.api.bot.AbstractCommand;

import java.util.ArrayList;

public class Help {
    private MessageReceivedEvent event;
    private ArrayList<AbstractCommand> commands;

    public Help(MessageReceivedEvent event,ArrayList<AbstractCommand> commands)
    {
        this.event = event;
        this.commands = commands;
    }

    public MessageReceivedEvent getEvent()
    {
        return event;
    }

    public ArrayList<AbstractCommand> getCommands()
    {
        return commands;
    }
}
