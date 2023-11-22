package spigotmc.ru.morecommand.commands;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import spigotmc.ru.morecommand.More_command;


public class Flyeffect implements CommandExecutor {

    private final More_command plugin;
    public  Flyeffect(More_command plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender,Command cmd,String string, String[] args) {
        if (plugin.getConfig().getBoolean("setting.flyeffect")) {
            if(!sender.hasPermission(plugin.getConfig().getString("permissions.flyeffect"))) {
                String s = ChatColor.translateAlternateColorCodes('&',
                        plugin.getConfig().getString("messages.NoPermisioneffectfly"));
                sender.sendMessage(s);
                return true;
            }
            if(args.length == 0) {
                if(!(sender instanceof Player)){
                    String s = ChatColor.translateAlternateColorCodes('&',
                            plugin.getConfig().getString("messages.NoPermisionflyeffectconsole"));
                    sender.sendMessage(s);
                    return true;
                } else {
                    Player p = (Player) sender;
                    PotionEffect effect = new PotionEffect(PotionEffectType.LEVITATION, 90*2, 12);
                    String s = ChatColor.translateAlternateColorCodes('&',
                            plugin.getConfig().getString("messages.flyeffect"));
                    p.addPotionEffect(effect);
                    sender.sendMessage(s);
                }
            } else {
                if(sender.hasPermission(plugin.getConfig().getString("permissions.flyeffect.others"))) {
                    Player p = Bukkit.getPlayerExact(args[0]);
                    if(p == null) {
                        String s = ChatColor.translateAlternateColorCodes('&',
                                plugin.getConfig().getString("messages.dontplayerflyeffect"));
                        sender.sendMessage(s);
                        return false;
                    }
                    if(p == sender) {
                        PotionEffect effect = new PotionEffect(PotionEffectType.LEVITATION, 90*2, 12);
                        String s = ChatColor.translateAlternateColorCodes('&',
                                plugin.getConfig().getString("messages.flyeffect"));
                        p.addPotionEffect(effect);
                        sender.sendMessage(s);
                        return true;
                    }
                    PotionEffect effect = new PotionEffect(PotionEffectType.LEVITATION, 90*2, 12);
                    p.addPotionEffect(effect);
                    String s = ChatColor.translateAlternateColorCodes('&',
                            plugin.getConfig().getString("messages.flyeffect"));
                    sender.sendMessage(s);
                }
            }
            return true;
        }


        return true;

    }
}
