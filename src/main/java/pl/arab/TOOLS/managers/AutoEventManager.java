package pl.arab.TOOLS.managers;

import net.kyori.adventure.bossbar.BossBar;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.arab.Main;
import pl.arab.TOOLS.utils.AdventureUtil;
import pl.arab.TOOLS.utils.FormatUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AutoEventManager {

    private final Main plugin;
    // ConcurrentHashMap jest bezpieczna dla wątków asynchronicznych
    private final Map<String, ActiveEvent> activeEvents = new ConcurrentHashMap<>();

    public AutoEventManager(Main plugin) {
        this.plugin = plugin;
        startTask();
    }

    public void startEvent(String eventName, int durationMinutes, String keyType, int amount) {
        String configData = plugin.getPluginConfig().keyAllKeys.get(keyType.toUpperCase());
        if (configData == null) return;

        String[] parts = configData.split("\\|");
        String keyName = parts[0];
        String command = parts[1].replace("{AMOUNT}", String.valueOf(amount));

        int totalSeconds = durationMinutes * 60;

        BossBar bossBar = BossBar.bossBar(
                AdventureUtil.miniMessage(plugin.getPluginConfig().keyAllBossBarTitle
                        .replace("{TIME}", FormatUtil.formatTime(totalSeconds))
                        .replace("{KEY}", keyName)
                        .replace("{AMOUNT}", String.valueOf(amount)), null),
                1.0f,
                BossBar.Color.valueOf(plugin.getPluginConfig().keyAllBossBarColor),
                BossBar.Overlay.valueOf(plugin.getPluginConfig().keyAllBossBarStyle)
        );

        ActiveEvent event = new ActiveEvent(eventName, totalSeconds, bossBar, keyName, command, amount);
        activeEvents.put(eventName.toLowerCase(), event);

        // Wyświetlenie bossbara wszystkim graczom
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.showBossBar(bossBar);
        }
    }

    public boolean stopEvent(String eventName) {
        ActiveEvent event = activeEvents.remove(eventName.toLowerCase());
        if (event != null) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.hideBossBar(event.bossBar);
            }
            return true;
        }
        return false;
    }

    public boolean isEventActive(String eventName) {
        return activeEvents.containsKey(eventName.toLowerCase());
    }

    // Task odliczający - działa asynchronicznie dla mega optymalizacji
    private void startTask() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {
            for (Map.Entry<String, ActiveEvent> entry : activeEvents.entrySet()) {
                ActiveEvent event = entry.getValue();
                event.timeLeft--;

                if (event.timeLeft <= 0) {
                    // Koniec odliczania
                    activeEvents.remove(entry.getKey());

                    // Schowanie BossBara
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.hideBossBar(event.bossBar);
                    }

                    // Wykonanie komendy MUSI odbyć się w głównym wątku!
                    Bukkit.getScheduler().runTask(plugin, () -> {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), event.command);
                    });
                } else {
                    // Aktualizacja BossBara
                    float progress = (float) event.timeLeft / event.totalTime;
                    event.bossBar.progress(Math.max(0.0f, Math.min(1.0f, progress)));

                    event.bossBar.name(AdventureUtil.miniMessage(plugin.getPluginConfig().keyAllBossBarTitle
                            .replace("{TIME}", FormatUtil.formatTime(event.timeLeft))
                            .replace("{KEY}", event.keyName)
                            .replace("{AMOUNT}", String.valueOf(event.amount)), null));
                }
            }
        }, 20L, 20L); // 20 ticków = 1 sekunda
    }

    // Klasa pomocnicza trzymająca dane eventu
    private static class ActiveEvent {
        String name;
        int totalTime;
        int timeLeft;
        BossBar bossBar;
        String keyName;
        String command;
        int amount;

        public ActiveEvent(String name, int totalTime, BossBar bossBar, String keyName, String command, int amount) {
            this.name = name;
            this.totalTime = totalTime;
            this.timeLeft = totalTime;
            this.bossBar = bossBar;
            this.keyName = keyName;
            this.command = command;
            this.amount = amount;
        }
    }

    // Ta metoda przyda nam się, by gracz po wejściu na serwer widział aktualny bossbar
    public void showActiveBossBars(Player player) {
        for (ActiveEvent event : activeEvents.values()) {
            player.showBossBar(event.bossBar);
        }
    }
}