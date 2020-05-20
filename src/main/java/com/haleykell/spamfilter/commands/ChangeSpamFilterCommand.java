package com.haleykell.spamfilter.commands;

import com.haleykell.spamfilter.Core;
import com.haleykell.spamfilter.Lang;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public class ChangeSpamFilterCommand implements CommandExecutor {

    private Core plugin;

    public ChangeSpamFilterCommand(Core plugin) { this.plugin = plugin;}

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        FileConfiguration config = plugin.getConfig();

        if (strings.length == 0) {
            commandSender.sendMessage(Lang.SPAMFILTER_TITLE + "Current spam limits are");
            commandSender.sendMessage(Lang.SPAMFILTER_TITLE + "Character Spam: " + config.getInt("limit"));
            commandSender.sendMessage(Lang.SPAMFILTER_TITLE + "Capitalized Words: " + config.getInt("capsWords"));
            commandSender.sendMessage(Lang.SPAMFILTER_TITLE + "Capitalized Letters: " + config.getInt("caps"));
            return true;
        }
        else if (strings.length > 2 || strings.length == 1) {
            commandSender.sendMessage(Lang.SPAMFILTER_TITLE + "Incorrect arguments.");
            return true;
        }

        if (strings[0].equalsIgnoreCase("limit")) {
            try {
                int limit = Integer.parseInt(strings[1]);
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
        if (strings[0].equalsIgnoreCase("words")) {
            try {
                double limit = Double.parseDouble(strings[1]);
                if (limit > 1 || limit < 0) {
                    commandSender.sendMessage(Lang.SPAMFILTER_TITLE + "Capitalized word limit should be less than one and greater than or equal to zero.");
                    return true;
                }
                config.set("capsWords", limit);
                commandSender.sendMessage(Lang.SPAMFILTER_TITLE + "Capitalized word limit has been set to " + limit * 100 + "% of words.");
            }
            catch (NumberFormatException e) {
                commandSender.sendMessage(Lang.SPAMFILTER_TITLE + "Argument needs to be a number.");
                return true;
            }
        }
        if (strings[0].equalsIgnoreCase("caps")) {
            try {
                double limit = Double.parseDouble(strings[1]);
                if (limit > 1 || limit < 0) {
                    commandSender.sendMessage(Lang.SPAMFILTER_TITLE + "Capitalized letter limit should be less than one and greater than or equal to zero.");
                    return true;
                }
                config.set("caps", limit);
                commandSender.sendMessage(Lang.SPAMFILTER_TITLE + "Capitalized letter limit has been set to " + limit * 100 + "% of letters.");
            }
            catch (NumberFormatException e) {
                commandSender.sendMessage(Lang.SPAMFILTER_TITLE + "Argument needs to be a number.");
                return true;
            }
        }

        return true;
    }
}
