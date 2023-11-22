package spigotmc.ru.morecommand;


import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.TrapDoor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;
import spigotmc.ru.morecommand.commands.*;
import spigotmc.ru.morecommand.tools.UpdateChecker;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class More_command extends JavaPlugin implements Listener  {


    private final List<Block> lastTrapdoors = new ArrayList();

    public static JavaPlugin instance;
    private File ConfigFile;
    java.util.logging.Logger log = Logger.getLogger("Minecraft");

    private String user;

    String configVersion = getConfig().getString("version");

    private Inventory stopmenu;




    @Override
    public void onEnable() {


        stopmenu = Bukkit.createInventory(null, 9, getConfig().getString("menustop.title"));
        // Добавление предметов в меню
        ItemStack redWool = new ItemStack(Material.valueOf(getConfig().getString("menustop.YesPredmet")));
        ItemMeta redWoolMeta = redWool.getItemMeta();
        redWoolMeta.setDisplayName(getConfig().getString("menustop.TitleYesPredmet"));
        redWool.setItemMeta(redWoolMeta);
        stopmenu.setItem(3, redWool);

        ItemStack greenWool = new ItemStack(Material.valueOf(getConfig().getString("menustop.NoPredmet")));
        ItemMeta greenWoolMeta = greenWool.getItemMeta();
        greenWoolMeta.setDisplayName(getConfig().getString("menustop.TitleNoPredmet"));
        greenWool.setItemMeta(greenWoolMeta);
        stopmenu.setItem(5, greenWool);









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



        String requiredVersion = "1.9"; // Минимальная требуемая версия конфига

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
        getCommand("more-command").setExecutor(this);
        getCommand("gm").setExecutor(new GMCommand(this));
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
        this.getServer().getPluginManager().addPermission(new Permission(getConfig().getString("permissions.gm")));
        this.getServer().getPluginManager().addPermission(new Permission(getConfig().getString("permissions.stop")));
        this.getServer().getPluginManager().addPermission(new Permission("more-command.update"));













    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("more-command") && args.length > 0 && args[0].equalsIgnoreCase("stop")) {
            if(sender.hasPermission("permissions.stop")) {
                if(this.getConfig().getBoolean("menustop.endable")) {
                    if (sender instanceof Player) {
                        Player player = (Player) sender;
                        player.openInventory(stopmenu);
                        return true;
                    }
                } else {
                    sender.sendMessage("Plugin Disabled");
                    Bukkit.getPluginManager().disablePlugin(this);
                    return true;

                }
            }
            sender.sendMessage(getConfig().getString("messages.nopermissionstop"));
            return false;
        }
        return false;
    }
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().equals(stopmenu)) {
            Player player = (Player) event.getWhoClicked();
            ItemStack clickedItem = event.getCurrentItem();

            if (clickedItem != null && clickedItem.hasItemMeta()) {
                ItemMeta clickedItemMeta = clickedItem.getItemMeta();

                // Проверка нажатого предмета
                if (clickedItemMeta.getDisplayName().equals(getConfig().getString("menustop.TitleYesPredmet"))) {
                    event.setCancelled(true); // Отменяем перемещение предмета в инвентаре игрока
                    player.closeInventory(); // Закрываем меню
                    player.sendMessage("Plugin Disabled");
                    Bukkit.getPluginManager().disablePlugin(this);
                } else if (clickedItemMeta.getDisplayName().equals(getConfig().getString("menustop.TitleNoPredmet"))) {
                    event.setCancelled(true);
                    player.closeInventory();
                }
            }
        }
    }








    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (this.getConfig().getBoolean("setting.update-check") && player.hasPermission("more-command.update")) {
            (new UpdateChecker(this, 109520)).getVersion((version) -> {
                if (!this.getDescription().getVersion().equals(version)) {
                    List<String> messageList = getConfig().getStringList("new-version");
                    for (String message : messageList) {
                        message = message.replace("%version%", version).replace("&", "§");
                        player.sendMessage(message);
                    }
                }

            });
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
