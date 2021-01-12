package org.harrydev.discordx.api;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.harrydev.discordx.api.bot.AbstractCommand;

public abstract class CommandClient extends ListenerAdapter {
    public abstract void addCommand(AbstractCommand command);
    public abstract void onMessageReceived(MessageReceivedEvent event);
}
