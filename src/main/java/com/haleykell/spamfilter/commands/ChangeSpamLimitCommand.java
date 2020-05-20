package com.haleykell.spamfilter.commands;

import com.haleykell.spamfilter.Core;
import com.haleykell.spamfilter.Lang;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public class ChangeSpamLimitCommand implements CommandExecutor {

    private Core plugin;

    public ChangeSpamLimitCommand(Core plugin) { this.plugin = plugin;}

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        FileConfiguration config = plugin.getConfig();

        if (strings.length == 0) {
            commandSender.sendMessage(Lang.SPAMFILTER_TITLE + "Current spam limit is " + config.getInt("limit"));
            return true;
        }
        else if (strings.length > 1) {
            commandSender.sendMessage(Lang.SPAMFILTER_TITLE + "Incorrect arguments.");
            return true;
        }

        try {
            int limit = Integer.parseInt(strings[0]);
            if (limit < 3) {
                commandSender.sendMessage(Lang.SPAMFILTER_TITLE + "Spam limit should probably be higher than two.");
                return true;
            }
            config.set("limit", limit);
            commandSender.sendMessage(Lang.SPAMFILTER_TITLE + "Spam limit has been set to " + limit);
            return true;
        }
        catch (NumberFormatException e) {
            commandSender.sendMessage(Lang.SPAMFILTER_TITLE + "Argument needs to be a number.");
            return true;
        }
    }
}
