package pl.arab;

import org.bukkit.plugin.java.JavaPlugin;
import pl.arab.commands.CommandMap;
import pl.arab.config.PluginConfig;

public final class Main extends JavaPlugin {

    private static Main instance;
    private PluginConfig pluginConfig;

    @Override
    public void onEnable() {
        instance = this;

        // TODO: Tutaj w przyszłości zainicjujesz swój config Okaeri
        // this.pluginConfig = ConfigManager.create(PluginConfig.class, (it) -> { ... });

        // Rejestracja komend
        new CommandMap(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    // Pozwala na dostęp do Maina z każdego miejsca w kodzie
    public static Main getInstance() {
        return instance;
    }

    public PluginConfig getPluginConfig() {
        return pluginConfig;
    }
}