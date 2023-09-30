package spigotmc.ru.morecommand.commands;

import org.bukkit.Bukkit;
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

        if (command.getName().equalsIgnoreCase("gm")) {
            if (args.length < 2) {
                return false;
            }

            GameMode gameMode;
            try {
                int mode = Integer.parseInt(args[0]);
                gameMode = getGameMode(mode);
            } catch (NumberFormatException e) {
                sender.sendMessage("Неправильный формат режима игры!");
                return true;
            }

            Player player;
            if (args[1].equalsIgnoreCase("@a")) { // Знак @a указывает на всех игроков сервера
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    onlinePlayer.setGameMode(gameMode);
                }
                sender.sendMessage("Режим игры изменен для всех игроков!");
            } else {
                player = Bukkit.getPlayer(args[1]);
                if (player == sender) {
                    sender.sendMessage("Игрок " + args[1] + " не найден!");
                    return true;
                }
                player.setGameMode(gameMode);
                sender.sendMessage("Режим игры изменен для игрока " + player.getName() + "!");
            }
            return true;
        }
        return false;
    }

    private GameMode getGameMode(int mode) {
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
