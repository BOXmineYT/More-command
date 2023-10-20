package spigotmc.ru.morecommand.tools;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import spigotmc.ru.morecommand.More_command;

import java.io.File;
import java.io.IOException;

public class LanguageManager {


        private More_command plugin;
        private FileConfiguration langConfig;
        private File langFile;

        public LanguageManager(More_command plugin) {
            this.plugin = plugin;
            langFile = new File(plugin.getDataFolder(), "lang.yml");
            langConfig = YamlConfiguration.loadConfiguration(langFile);
        }

        public void loadLanguages() {
            if (!langFile.exists()) {
                plugin.saveResource("lang.yml", false);
            }
            plugin.reloadConfig();
        }

        public void saveLanguages() {
            try {
                langConfig.save(langFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public String getLocalizedString(String language, String key) {
            if (!langConfig.contains(language + "." + key)) {
                return "String not found";
            }
            return ChatColor.translateAlternateColorCodes('&', langConfig.getString(language + "." + key));
        }

}

