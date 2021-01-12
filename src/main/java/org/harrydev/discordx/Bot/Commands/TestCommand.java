package org.harrydev.discordx.Bot.Commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.harrydev.discordx.api.bot.AbstractCommand;

public class TestCommand extends AbstractCommand {
    /**
     * Example Format of How to use Command api i build
     *  - abhithedev
     */

    public TestCommand()
    {
        // Name of the command users can call your command by botprefix + name
        this.name = "test";
        // Args of your command
        this.args = "[name]";
    }

    @Override
    public void execute(MessageReceivedEvent event,String args) {
        event.getChannel().sendMessage("PONG").queue();
    }
}
