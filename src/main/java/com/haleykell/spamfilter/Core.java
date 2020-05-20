package com.haleykell.spamfilter;

import com.haleykell.spamfilter.commands.ChangeSpamFilterCommand;
import com.haleykell.spamfilter.listeners.ChatListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Core extends JavaPlugin {

    @Override
    public void onEnable() {
        if (!new File(getDataFolder() + "/config.yml").exists()) {
            saveDefaultConfig();
        }

        getCommand("spamfilter").setExecutor(new ChangeSpamFilterCommand(this));

        getServer().getPluginManager().registerEvents(new ChatListener(this), this);

        getLogger().info("SpamFilter has started.");
    }

    @Override
    public void onDisable() {

    }
}
