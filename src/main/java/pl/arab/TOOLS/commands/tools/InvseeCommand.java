// --- InvseeCommand.java ---
package pl.arab.TOOLS.commands.tools;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.arab.COMMANDMAPUSE.CommandUse;
import pl.arab.TOOLS.utils.AdventureUtil;

import java.util.Collections;
import java.util.List;

public class InvseeCommand extends CommandUse {

    public InvseeCommand() {
        super("invsee", Collections.emptyList());
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if (!sender.hasPermission("wisemc.invsee")) {
            sender.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgNoPermission.replace("{PERM}", "wisemc.invsee")));
            return;
        }

        if (!(sender instanceof Player player)) return;

        if (args.length != 1) {
            player.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgInvseeUsage));
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            player.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgPlayerOffline));
            return;
        }

        player.openInventory(target.getInventory());
    }

    @Override
    public List<String> tab(Player p, String[] args) {
        return null;
    }
}