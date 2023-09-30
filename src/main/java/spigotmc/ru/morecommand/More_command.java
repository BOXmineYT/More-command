package spigotmc.ru.morecommand;


import org.bukkit.block.Block;
import org.bukkit.block.data.type.TrapDoor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;
import spigotmc.ru.morecommand.commands.*;
import spigotmc.ru.morecommand.tools.Languages;
import spigotmc.ru.morecommand.tools.UpdateChecker;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class More_command extends JavaPlugin implements Listener {


    private final List<Block> lastTrapdoors = new ArrayList();
    private Languages languages;
    public static JavaPlugin instance;
    private File ConfigFile;
    java.util.logging.Logger log = Logger.getLogger("Minecraft");

    private String user;

    String configVersion = getConfig().getString("version");




    @Override
    public void onEnable() {
        File config = new File(getDataFolder() + File.separator + "config.yml");
        if(!config.exists()){
            getLogger().info("Creating config....");
            getConfig().options().copyDefaults(true);
            saveDefaultConfig();
        }


        new UpdateChecker(this, 109520).getVersion(version -> {
            if (this.getDescription().getVersion().equals(version)) {
                getLogger().info("There is not a new update available.");
            } else {
                getLogger().info("A new version of the plugin has been released. version: " + version );
                getLogger().info("Download - https://www.spigotmc.org/resources/more-command.109520/");
            }
        });



        String requiredVersion = "1.7"; // Минимальная требуемая версия конфига

        if (configVersion == null || !configVersion.equals(requiredVersion)) {
            // Обработка, если версия конфига не соответствует требуемой
            getLogger().info("Outdated config.yml! Creating a new!");
            this.ConfigFile = new File(this.getDataFolder(), "config.yml");
            this.ConfigFile.renameTo(new File(this.getDataFolder(), "config.yml.old"));
            this.saveResource("config.yml", false);
        } else {
            // Обработка, если версия конфига соответствует требуемой
            getLogger().info("The config.yml file is not outdated yet.");
        }




        getCommand("feed").setExecutor(new FeedCommand(this));
        getCommand("heal").setExecutor(new HealCommand(this));
        getCommand("bc").setExecutor(new BCcommand(this));
        getCommand("flyeffect").setExecutor(new Flyeffect(this));
        getCommand("mc").setExecutor(new MainCommand(this));
        //getCommand("gm").setExecutor(new GMCommand(this));
        this.getServer().getPluginManager().registerEvents(this, this);

        this.getServer().getPluginManager().addPermission(new Permission(getConfig().getString("permissions.feed")));
        this.getServer().getPluginManager().addPermission(new Permission(getConfig().getString("permissions.feed.others")));
        this.getServer().getPluginManager().addPermission(new Permission(getConfig().getString("permissions.heal")));
        this.getServer().getPluginManager().addPermission(new Permission(getConfig().getString("permissions.heal.others")));
        this.getServer().getPluginManager().addPermission(new Permission(getConfig().getString("permissions.bc")));
        this.getServer().getPluginManager().addPermission(new Permission(getConfig().getString("permissions.flyeffect")));
        this.getServer().getPluginManager().addPermission(new Permission(getConfig().getString("permissions.flyeffect.others")));
        this.getServer().getPluginManager().addPermission(new Permission(getConfig().getString("permissions.reload")));
        this.getServer().getPluginManager().addPermission(new Permission(getConfig().getString("permissions.help")));
        //this.getServer().getPluginManager().addPermission(new Permission(getConfig().getString("permissions.gm")));
        this.getServer().getPluginManager().addPermission(new Permission("more-command.update"));




    }





    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();


        if (getConfig().getBoolean("setting.update-check")){
            // Проверяем, есть ли у игрока права на обновление плагина
            if (player.hasPermission("more-command.update")) {
                new UpdateChecker(this, 109520).getVersion(version -> {
                    if (getDescription().getVersion().equals(version)) {

                    } else {

                        player.sendMessage(getConfig().getString("new-version").replace("%version%", version).replace("&", "§"));
                        //player.sendMessage(ChatColor.GOLD + "Download - https://www.spigotmc.org/resources/more-command.109520/".replace("&", "§"));
                    }
                });
            }
        }




    }




    @EventHandler
    public void onBlockRedstoneEvent(BlockRedstoneEvent e) {
        if (e.getBlock().getBlockData() instanceof TrapDoor) {
            if (this.lastTrapdoors.contains(e.getBlock())) {
                e.setNewCurrent(e.getOldCurrent());
                return;
            }

            this.lastTrapdoors.add(e.getBlock());
            this.getServer().getScheduler().runTaskLater(this, () -> {
                this.lastTrapdoors.remove(e.getBlock());
            }, 5L);
        }

    }



}
