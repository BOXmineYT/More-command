package spigotmc.ru.morecommand.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class MainCommand implements CommandExecutor {
    Plugin plugin;
    public MainCommand(Plugin plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
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
                sender.sendMessage(ChatColor.GREEN + "More-Command 1.6.1.2" + ChatColor.WHITE + " by " + ChatColor.RED + "BOXmineYT");
                sender.sendMessage(ChatColor.GOLD + "/mc help " + ChatColor.DARK_GREEN + "- Помощь по плагину");
                sender.sendMessage(ChatColor.GOLD + "/mc reload " + ChatColor.DARK_GREEN + "- Перезагрузка плагина");
                sender.sendMessage(ChatColor.GOLD + "/heal " + ChatColor.DARK_GREEN + "- Полное востоновление здоровья");
                //sender.sendMessage(ChatColor.GOLD + "/gm" + ChatColor.DARK_GREEN + "- изменить режим игры");
                sender.sendMessage(ChatColor.GOLD + "/feed " + ChatColor.DARK_GREEN + "- Полное востоновление голода ");
                sender.sendMessage(ChatColor.GOLD + "/bc " + ChatColor.DARK_GREEN + "- Объявления на весь сервер");
                sender.sendMessage(ChatColor.GOLD + "/flyeffect " + ChatColor.DARK_GREEN + "- Выдать игроку левитацию чтоб затролить игрока");
                sender.sendMessage(ChatColor.GOLD + "/gm" + ChatColor.DARK_GREEN + "- Команда чтоб Модерератор мог начать и закончить работу");
                return true;
            }
        }
        return true;
    }
}
