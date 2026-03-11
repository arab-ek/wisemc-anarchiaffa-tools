// --- TeleportCommand.java ---
package pl.arab.TOOLS.commands.tools;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.arab.COMMANDMAPUSE.CommandUse;
import pl.arab.TOOLS.utils.AdventureUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TeleportCommand extends CommandUse {

    public TeleportCommand() {
        super("tp", Arrays.asList("teleport"));
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if (!sender.hasPermission("wisemc.tp")) {
            sender.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgNoPermission.replace("{PERM}", "wisemc.tp")));
            return;
        }

        if (!(sender instanceof Player player)) return;

        if (args.length != 1) {
            player.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgTpUsage));
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            player.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgPlayerOffline));
            return;
        }

        player.teleportAsync(target.getLocation());
        player.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgTpSuccess.replace("{PLAYER}", target.getName())));
    }

    @Override
    public List<String> tab(Player p, String[] args) {
        return null;
    }
}