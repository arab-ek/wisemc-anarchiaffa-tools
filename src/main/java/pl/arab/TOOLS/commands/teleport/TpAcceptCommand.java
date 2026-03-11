package pl.arab.TOOLS.commands.teleport;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.arab.COMMANDMAPUSE.CommandUse;
import pl.arab.Main;
import pl.arab.TOOLS.utils.AdventureUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class TpAcceptCommand extends CommandUse {

    public TpAcceptCommand() {
        super("tpaccept", Arrays.asList("tpyes"));
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgMustBePlayer));
            return;
        }

        if (!Main.getInstance().getTeleportManager().hasAnyRequests(player)) {
            player.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgNoRequests));
            return;
        }

        if (args.length != 1) {
            player.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgTpAcceptUsage));
            return;
        }

        if (args[0].equals("*")) {
            for (UUID uuid : Main.getInstance().getTeleportManager().getAllRequests(player)) {
                Player requester = Bukkit.getPlayer(uuid);
                if (requester != null) {
                    requester.teleportAsync(player.getLocation());
                    requester.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgTpAcceptedTarget.replace("{PLAYER}", player.getName())));
                }
            }
            player.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgTpAcceptedSender.replace("{PLAYER}", "wszystkich")));
            Main.getInstance().getTeleportManager().clearAllRequests(player);
            return;
        }

        Player targetRequester = Bukkit.getPlayer(args[0]);
        if (targetRequester == null) {
            player.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgPlayerOffline));
            return;
        }

        if (!Main.getInstance().getTeleportManager().hasRequestFrom(player, targetRequester)) {
            player.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgNoRequests));
            return;
        }

        targetRequester.teleportAsync(player.getLocation());
        targetRequester.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgTpAcceptedTarget.replace("{PLAYER}", player.getName())));
        player.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgTpAcceptedSender.replace("{PLAYER}", targetRequester.getName())));

        Main.getInstance().getTeleportManager().removeRequest(player, targetRequester);
    }

    @Override
    public List<String> tab(Player p, String[] args) {
        if (args.length == 1) {
            return Arrays.asList("*"); // Pokazuje opcję do akceptacji wszystkich
        }
        return Collections.emptyList();
    }
}