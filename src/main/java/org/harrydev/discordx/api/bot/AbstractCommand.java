package org.harrydev.discordx.api.bot;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public abstract class AbstractCommand {
    protected String name;
    protected String args;
    protected Boolean use_args;

    public abstract void execute(MessageReceivedEvent event,String args);
    public String getName()
    {
        return this.name;
    }
    public String getArgs()
    {
        return this.args;
    }
    public Boolean canuseArgs(){return this.use_args;}
}
