package com.haleykell.spamfilter.listeners;

import com.haleykell.spamfilter.Core;
import com.haleykell.spamfilter.Lang;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    private final Core core;

    public ChatListener(Core core) { this.core = core; }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        FileConfiguration config = core.getConfig();

        int count = 1;
        char[] message = e.getMessage().toCharArray();

        for (int i = 1; i < message.length; i++) {
            if (message[i] == message[i - 1]) count++;
            else count = 1;
            if (count > config.getInt("limit", 3)) {
                e.getPlayer().sendMessage(Lang.SPAMFILTER_TITLE + ChatColor.RED + "Somewhere, a mod is crying. Please do not spam characters!");
                e.setCancelled(true);
                return;
            }
        }
    }

}
