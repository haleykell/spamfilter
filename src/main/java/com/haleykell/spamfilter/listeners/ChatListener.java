package com.haleykell.spamfilter.listeners;

import com.haleykell.spamfilter.Core;
import com.haleykell.spamfilter.Lang;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Arrays;

public class ChatListener implements Listener {

    private final Core core;

    public ChatListener(Core core) { this.core = core; }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        FileConfiguration config = core.getConfig();

        int count = 1;
        char[] message = e.getMessage().toCharArray();
        boolean capsEveryWord = false;
        boolean interchangeableCaps = false;
        boolean charSpam = checkCharSpam(message, config, e.getPlayer());
        if (!charSpam) interchangeableCaps = checkInterCaps(e.getMessage().toCharArray(), e.getPlayer(), config);
        if (!charSpam && !interchangeableCaps) capsEveryWord = checkCapsEveryWord(e.getMessage().split(" "), e.getPlayer(), config);


        if (charSpam || capsEveryWord || interchangeableCaps) e.setCancelled(true);
    }

    private boolean checkInterCaps(char[] message, Player player, FileConfiguration config) {
        double count = 0;
        for (char c : message) {
            if (Character.isUpperCase(c)) count++;
        }
        if (count >= ((double) message.length) * config.getDouble("caps", 0.50)) {
            player.sendMessage(Lang.SPAMFILTER_TITLE + ChatColor.RED + "Somewhere, a mod is crying. Please do not capitalize so many letters!");
            return true;
        }
        return false;
    }

    private boolean checkCapsEveryWord(String[] s, Player player, FileConfiguration config) {
        double count = 0;
        for (String str : s) {
            if (Character.isUpperCase(str.charAt(0))) count++;
        }
        if (count >= ((double) s.length) * config.getDouble("capsWords", 0.90)) {
            player.sendMessage(Lang.SPAMFILTER_TITLE + ChatColor.RED + "Somewhere, a mod is crying. Please do not capitalize the first letter of every word!");
            return true;
        }
        return false;
    }

    private boolean checkCharSpam(char[] message, FileConfiguration config, Player player) {
        int count = 1;
        for (int i = 1; i < message.length; i++) {
            if (message[i] == message[i - 1]) count++;
            else count = 1;
            if (count > config.getInt("limit", 3)) {
                player.sendMessage(Lang.SPAMFILTER_TITLE + ChatColor.RED + "Somewhere, a mod is crying. Please do not spam characters!");
                return true;
            }
        }
        return false;
    }

}
