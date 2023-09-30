package spigotmc.ru.morecommand.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.jetbrains.annotations.NotNull;
import spigotmc.ru.morecommand.More_command;

public class BCcommand implements CommandExecutor {

    private final More_command plugin;
    public  BCcommand(More_command plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if(!(sender instanceof Player)){
            String s = ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfig().getString("messages.NoPermisionbcconsole"));
            sender.sendMessage(s);
            return true;
        }

        Player player = (Player) sender;
        if (!player.hasPermission(plugin.getConfig().getString("permissions.bc"))) {
            String s = ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfig().getString("messages.NoPermisionbc"));
            sender.sendMessage(s);
            return true;
        }

        if (args.length == 0) {
            String s = ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfig().getString("messages.Dontmessagebc"));
            sender.sendMessage(s);
            return true;
        }

        String message = String.join(" ", args);


        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
                "&eОбьявление: &7" + message));

        return true;

    }

}



