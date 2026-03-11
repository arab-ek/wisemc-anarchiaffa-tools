// --- TeleportHereCommand.java ---
package pl.arab.TOOLS.commands.tools;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.arab.COMMANDMAPUSE.CommandUse;
import pl.arab.TOOLS.utils.AdventureUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TeleportHereCommand extends CommandUse {

    public TeleportHereCommand() {
        super("s", Arrays.asList("tphere", "stp"));
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if (!sender.hasPermission("wisemc.s")) {
            sender.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgNoPermission.replace("{PERM}", "wisemc.s")));
            return;
        }

        if (!(sender instanceof Player player)) return;

        if (args.length != 1) {
            player.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgTphereUsage));
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            player.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgPlayerOffline));
            return;
        }

        target.teleportAsync(player.getLocation());
        player.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgTphereSuccess.replace("{PLAYER}", target.getName())));
    }

    @Override
    public List<String> tab(Player p, String[] args) {
        return null;
    }
}