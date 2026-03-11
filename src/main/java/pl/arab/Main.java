package pl.arab;

import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import eu.okaeri.configs.serdes.commons.SerdesCommons;
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import pl.arab.COMMANDMAPUSE.CommandMap;
import pl.arab.TOOLS.config.PluginConfig;
import pl.arab.TOOLS.data.User;
import pl.arab.TOOLS.managers.AutoEventManager;
import pl.arab.TOOLS.managers.MessageManager;
import pl.arab.TOOLS.managers.TeleportManager;
import pl.arab.TOOLS.managers.database.DatabaseManager;
import pl.arab.TOOLS.managers.database.UserManager;
import pl.arab.TOOLS.managers.economy.VaultEconomyProvider;

import java.io.File;

public final class Main extends JavaPlugin implements Listener {

    private static Main instance;
    private PluginConfig pluginConfig;

    // Managery
    private DatabaseManager databaseManager;
    private UserManager userManager;
    private AutoEventManager autoEventManager;
    private TeleportManager teleportManager;
    private MessageManager messageManager;

    private Economy econ;

    @Override
    public void onEnable() {
        instance = this;

        // Inicjalizacja configu
        this.pluginConfig = ConfigManager.create(PluginConfig.class, (it) -> {
            it.withConfigurer(new YamlBukkitConfigurer(), new SerdesBukkit(), new SerdesCommons());
            it.withBindFile(new File(this.getDataFolder(), "config.yml"));
            it.saveDefaults();
            it.load(true);
        });

        // Inicjalizacja Bazy i Graczy
        this.userManager = new UserManager();
        this.databaseManager = new DatabaseManager(this);

        // Rejestracja WŁASNEJ Ekonomii do Vaulta
        if (getServer().getPluginManager().getPlugin("Vault") != null) {
            getServer().getServicesManager().register(Economy.class, new VaultEconomyProvider(this), this, ServicePriority.Highest);
            this.econ = getServer().getServicesManager().getRegistration(Economy.class).getProvider();
            getLogger().info("Zarejestrowano wlasny silnik ekonomii (WiseMCEconomy) w Vault!");
        } else {
            getLogger().severe("Brak pluginu Vault na serwerze! Ekonomia zostala wylaczona.");
        }

        // Inicjalizacja reszty systemów
        this.autoEventManager = new AutoEventManager(this);
        this.teleportManager = new TeleportManager();
        this.messageManager = new MessageManager();

        // Task dodający Playtime co minutę
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> {
            for (User user : userManager.getUsers().values()) {
                user.addPlaytime(60);
            }
        }, 20 * 60L, 20 * 60L);

        // Zapisywanie wszystkich graczy co 5 minut (AutoSave)
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> {
            for (User user : userManager.getUsers().values()) {
                databaseManager.saveUser(user);
            }
        }, 20 * 60 * 5L, 20 * 60 * 5L);

        // Komendy i Eventy
        new CommandMap(this);
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        // Zapis przy wyłączeniu
        for (User user : userManager.getUsers().values()) {
            databaseManager.saveUser(user);
        }
        if (databaseManager != null) {
            databaseManager.close();
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        // Ładowanie z bazy SQL (asynchronicznie!)
        databaseManager.loadUser(player.getUniqueId(), player.getName());

        if (autoEventManager != null) autoEventManager.showActiveBossBars(player);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        User user = userManager.getUser(event.getPlayer().getUniqueId());
        if (user != null) {
            // Zapis do bazy i usunięcie z RAMu by nie mulić serwera
            databaseManager.saveUser(user);
            userManager.removeUser(user.getUuid());
        }
    }

    // Gettery
    public static Main getInstance() { return instance; }
    public PluginConfig getPluginConfig() { return pluginConfig; }
    public DatabaseManager getDatabaseManager() { return databaseManager; }
    public UserManager getUserManager() { return userManager; }
    public AutoEventManager getAutoEventManager() { return autoEventManager; }
    public TeleportManager getTeleportManager() { return teleportManager; }
    public MessageManager getMessageManager() { return messageManager; }
    public Economy getEconomy() { return econ; }
}