// --- GamemodeCommand.java ---
package pl.arab.TOOLS.commands.tools;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.arab.COMMANDMAPUSE.CommandUse;
import pl.arab.TOOLS.utils.AdventureUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GamemodeCommand extends CommandUse {

    public GamemodeCommand() {
        super("gamemode", Arrays.asList("gm"));
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if (!sender.hasPermission("wisemc.gamemode")) {
            sender.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgNoPermission.replace("{PERM}", "wisemc.gamemode")));
            return;
        }

        if (args.length == 0 || args.length > 2) {
            sender.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgGamemodeUsage));
            return;
        }

        Player target = (args.length == 2) ? Bukkit.getPlayer(args[1]) : (sender instanceof Player ? (Player) sender : null);

        if (target == null) {
            sender.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgPlayerOffline));
            return;
        }

        GameMode gm;
        switch (args[0].toLowerCase()) {
            case "0": case "survival": gm = GameMode.SURVIVAL; break;
            case "1": case "creative": gm = GameMode.CREATIVE; break;
            case "2": case "adventure": gm = GameMode.ADVENTURE; break;
            case "3": case "spectator": gm = GameMode.SPECTATOR; break;
            default:
                sender.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgGamemodeUsage));
                return;
        }

        target.setGameMode(gm);

        if (target.equals(sender)) {
            sender.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgGamemodeChangedSelf.replace("{MODE}", gm.name())));
        } else {
            sender.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgGamemodeChangedOther.replace("{PLAYER}", target.getName()).replace("{MODE}", gm.name())));
        }
    }

    @Override
    public List<String> tab(Player p, String[] args) {
        if (args.length == 1) return Arrays.asList("0", "1", "2", "3", "survival", "creative", "spectator");
        return null;
    }
}