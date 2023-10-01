package spigotmc.ru.morecommand.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class GMCommand implements CommandExecutor {

    Plugin plugin;
    public GMCommand(Plugin plugin) {
        this.plugin = plugin;
    }
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (plugin.getConfig().getBoolean("setting.gm")){
            if(!sender.hasPermission(plugin.getConfig().getString("permissions.gm"))) {
                String s = ChatColor.translateAlternateColorCodes('&',
                        plugin.getConfig().getString("messages.NoPermisiongm"));
                sender.sendMessage(s);
                return true;
            }
            if(args.length == 0) {
                sender.sendMessage("/gm (0/1/2/3)");
                return true;
            }

            if (!(sender instanceof Player)) {
                sender.sendMessage(plugin.getConfig().getString("messages.NopermissionConsolegm").replace("&", "§"));
                return true;
            }

            Player player;
            if (args.length > 1) {
                player = Bukkit.getPlayer(args[1]);
            } else {
                player = (Player) sender;
            }

            if (player == null) {
                sender.sendMessage(plugin.getConfig().getString("messages.gmdontplayer").replace("&", "§"));
                return true;
            }

            GameMode gameMode;
            try {
                gameMode = GameMode.getByValue(Integer.parseInt(args[0]));
            } catch (NumberFormatException e) {
                sender.sendMessage("Invalid game mode.");
                return true;
            }

            player.setGameMode(gameMode);
            player.sendMessage(plugin.getConfig().getString("messages.gm").replace("%gamemode%", gameMode.toString()).replace("&", "§").replace("%player_name%", player.getName()));
            return true;
        }
        return false;
    }

    private GameMode gameMode(int mode) {
        switch (mode) {
            case 0:
                return GameMode.SURVIVAL;
            case 1:
                return GameMode.CREATIVE;
            case 2:
                return GameMode.ADVENTURE;
            case 3:
                return GameMode.SPECTATOR;
            default:
                throw new IllegalArgumentException("Недопустимый режим игры!");
        }
    }
}
