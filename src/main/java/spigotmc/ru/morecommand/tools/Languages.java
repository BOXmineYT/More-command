package spigotmc.ru.morecommand.tools;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class Languages {
    private Plugin plugin;
    private FileConfiguration languageConfig;

    public Languages(Plugin plugin) {
        this.plugin = plugin;
        loadLanguageConfig();
    }

    private void loadLanguageConfig() {
        File languageFile = new File(plugin.getDataFolder(), "lang.yml");

        if (!languageFile.exists()) {
            plugin.saveResource("lang.yml", false);
        }

        languageConfig = YamlConfiguration.loadConfiguration(languageFile);
    }

    public String getString(String key) {
        return languageConfig.getString(key);
    }

    public void setString(String key, String value) {
        languageConfig.set(key, value);

        try {
            languageConfig.save(new File(plugin.getDataFolder(), "lang.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

