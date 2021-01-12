package org.harrydev.discordx.api;

import net.dv8tion.jda.api.JDA;

public class API implements DiscordXAPI {
    public JDA jda;
    private CommandClient commandManager;

    public API(JDA jda,CommandClient commandManager)
    {
        this.jda = jda;
        this.commandManager = commandManager;
    }

    @Override
    public JDA getJDA() {
        return jda;
    }

    @Override
    public CommandClient getCommandManager()
    {
        return commandManager;
    }
}
