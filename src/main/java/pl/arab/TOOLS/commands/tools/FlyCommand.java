// --- FlyCommand.java ---
package pl.arab.TOOLS.commands.tools;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.arab.COMMANDMAPUSE.CommandUse;
import pl.arab.TOOLS.utils.AdventureUtil;

import java.util.Collections;
import java.util.List;

public class FlyCommand extends CommandUse {

    public FlyCommand() {
        super("fly", Collections.singletonList("latanie"));
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if (!sender.hasPermission("wisemc.fly")) {
            sender.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgNoPermission.replace("{PERM}", "wisemc.fly")));
            return;
        }

        if (!(sender instanceof Player player)) return;

        if (player.getAllowFlight()) {
            player.setAllowFlight(false);
            player.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgFlyDisabled));
        } else {
            player.setAllowFlight(true);
            player.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgFlyEnabled));
        }
    }

    @Override
    public List<String> tab(Player p, String[] args) {
        return Collections.emptyList();
    }
}