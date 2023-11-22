package spigotmc.ru.morecommand.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import org.bukkit.plugin.Plugin;

import java.util.List;


public class MainCommand implements CommandExecutor {


    Plugin plugin;


    public MainCommand(Plugin plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender,Command command,String label, String[] args) {
        if(args.length == 0) {
            sender.sendMessage("/mc (reload/help)");
            return true;
        }
        if(args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission(plugin.getConfig().getString("permissions.reload"))) {
                String s = ChatColor.translateAlternateColorCodes('&',
                        plugin.getConfig().getString("plugin.NoPermisionreload"));
                sender.sendMessage(s);
                return true;
            }
            plugin.reloadConfig();
            String s = ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfig().getString("plugin.Reloadplugin"));
            sender.sendMessage(s);
            return true;
        }
        if(args[0].equalsIgnoreCase("help")) {
            if (!sender.hasPermission(plugin.getConfig().getString("permissions.help"))) {
                String s = ChatColor.translateAlternateColorCodes('&',
                        plugin.getConfig().getString("messages.NoPermisionhelp"));
                sender.sendMessage(s);
                return true;
            } else {

                sender.sendMessage(ChatColor.GREEN + "More-Command 1.6.1.4" + ChatColor.WHITE + " by " + ChatColor.RED + "BOXmineYT");
                List<String> messageList = plugin.getConfig().getStringList("help");
                for (String message : messageList) {
                    message = message.replace("&", "§");
                    sender.sendMessage(message);
                }

                return true;
            }


        }
        //if(args[0].equalsIgnoreCase("stop")) {
        //    if (!sender.hasPermission(plugin.getConfig().getString("permissions.stop"))) {
        //        String s = ChatColor.translateAlternateColorCodes('&',
        //                plugin.getConfig().getString("messages.NoPermisionstop"));
        //        sender.sendMessage(s);
        //        return true;
        //    } else {
        //        if (sender instanceof Player) {
        //            Player player = (Player) sender;
        //            player.openInventory(stopmenu);
        //     }
                //Player player = (Player) sender;
                //player.sendMessage("Plugin Disabled");
                //player.openInventory(menu);
        //        return true;
        //    }
        //}


        return true;




    }




}
