// --- SetSpawnCommand.java ---
package pl.arab.TOOLS.commands.spawn;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.arab.COMMANDMAPUSE.CommandUse;
import pl.arab.TOOLS.utils.AdventureUtil;

import java.util.Collections;
import java.util.List;

public class SetSpawnCommand extends CommandUse {

    public SetSpawnCommand() {
        super("setspawn", Collections.emptyList());
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if (!sender.hasPermission("wisemc.setspawn")) {
            sender.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgNoPermission.replace("{PERM}", "wisemc.setspawn")));
            return;
        }

        if (!(sender instanceof Player player)) return;

        getPlugin().getPluginConfig().spawnLocation = player.getLocation();
        getPlugin().getPluginConfig().save();

        player.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgSetSpawn));
    }

    @Override
    public List<String> tab(Player p, String[] args) {
        return Collections.emptyList();
    }
}