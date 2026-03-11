// --- SpawnCommand.java ---
package pl.arab.TOOLS.commands.spawn;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.arab.COMMANDMAPUSE.CommandUse;
import pl.arab.TOOLS.utils.AdventureUtil;

import java.util.Collections;
import java.util.List;

public class SpawnCommand extends CommandUse {

    public SpawnCommand() {
        super("spawn", Collections.emptyList());
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) return;

        if (getPlugin().getPluginConfig().spawnLocation == null) {
            player.sendMessage(AdventureUtil.miniMessage("<red>Spawn nie został jeszcze ustawiony!", Collections.emptyMap()));
            return;
        }

        // Możesz tutaj dodać licznik czasu (delay) z BukkitRunnable, jeśli tego oczekujesz. Poniżej natychmiastowy TP:
        player.teleportAsync(getPlugin().getPluginConfig().spawnLocation);
        player.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgSpawnTeleport));
    }

    @Override
    public List<String> tab(Player p, String[] args) {
        return Collections.emptyList();
    }
}