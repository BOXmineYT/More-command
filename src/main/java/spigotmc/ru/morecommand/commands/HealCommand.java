package spigotmc.ru.morecommand.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import spigotmc.ru.morecommand.More_command;


public class HealCommand implements CommandExecutor , Listener {
    private final More_command plugin;
     public  HealCommand(More_command plugin) {
         this.plugin = plugin;
     }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (plugin.getConfig().getBoolean("setting.heal")) {
            if(!sender.hasPermission(plugin.getConfig().getString("permissions.heal"))) {
                String s = ChatColor.translateAlternateColorCodes('&',
                        plugin.getConfig().getString("messages.NoPermisionheal"));
                sender.sendMessage(s);
                return true;
            }
            if(args.length == 0) {
                if(!(sender instanceof Player)){
                    String s = ChatColor.translateAlternateColorCodes('&',
                            plugin.getConfig().getString("messages.NoPermisionhealconsole"));
                    sender.sendMessage(s);
                    return true;
                } else {
                    Player p = (Player) sender;
                    p.setHealth(p.getMaxHealth());
                    p.setFoodLevel(20);
                    String s = ChatColor.translateAlternateColorCodes('&',
                            plugin.getConfig().getString("messages.heal"));
                    sender.sendMessage(s);
                }
            } else {
                if(sender.hasPermission(plugin.getConfig().getString("permissions.heal.others"))) {
                    Player p = Bukkit.getPlayerExact(args[0]);
                    if(p == null) {
                        String s = ChatColor.translateAlternateColorCodes('&',
                                plugin.getConfig().getString("messages.dontplayerheal"));
                        sender.sendMessage(s);
                        return false;
                    }
                    if(p == sender) {
                        String s = ChatColor.translateAlternateColorCodes('&',
                                plugin.getConfig().getString("messages.heal"));
                        p.setHealth(p.getMaxHealth());
                        p.setFoodLevel(20);
                        sender.sendMessage(s);
                        return false;
                    }
                    p.setHealth(p.getMaxHealth());
                    p.setFoodLevel(20);
                    String s = ChatColor.translateAlternateColorCodes('&',
                            plugin.getConfig().getString("messages.heal"));
                    sender.sendMessage(s);
                }
            }
            return true;


        }

        return true;
    }
}