package spigotmc.ru.morecommand.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import spigotmc.ru.morecommand.More_command;
import spigotmc.ru.morecommand.tools.Languages;

public class FeedCommand implements CommandExecutor {

    private final More_command plugin;
    public  FeedCommand(More_command plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (plugin.getConfig().getBoolean("setting.feed")) {
            if(!sender.hasPermission(plugin.getConfig().getString("permissions.feed"))) {
                String s = ChatColor.translateAlternateColorCodes('&',
                        plugin.getConfig().getString("messages.NoPermisionfeed"));
                sender.sendMessage(s);
                return true;
            }
            // если агрументов 0, то кормим себя
            if(args.length == 0) {
                if(!(sender instanceof Player)){
                    String s = ChatColor.translateAlternateColorCodes('&',
                            plugin.getConfig().getString("messages.NoPermisionfeedconsole"));
                    sender.sendMessage(s);
                    return true;
                } else {
                    Player p = (Player) sender;
                    p.setFoodLevel(20);
                    String s = ChatColor.translateAlternateColorCodes('&',
                            plugin.getConfig().getString("messages.feed"));
                    sender.sendMessage(s);
                }
                // если же аргументов не 0, то кормим другого игрока
            } else {
                if(sender.hasPermission(plugin.getConfig().getString("permissions.feed.others"))) {
                    Player p = Bukkit.getPlayerExact(args[0]);
                    if(p == null) {
                        String s = ChatColor.translateAlternateColorCodes('&',
                                plugin.getConfig().getString("messages.dontplayerfeed"));
                        sender.sendMessage(s);
                        return false;
                    }
                    if(p == sender) {
                        String s = ChatColor.translateAlternateColorCodes('&',
                                plugin.getConfig().getString("messages.feed"));
                        p.setFoodLevel(20);
                        sender.sendMessage(s);
                        return false;
                    }
                    p.setFoodLevel(20);
                    String s = ChatColor.translateAlternateColorCodes('&',
                            plugin.getConfig().getString("messages.feedother"));
                    sender.sendMessage(s);
                }
            }

            return true;
        }

        return true;
    }
}

