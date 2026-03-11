package pl.arab.TOOLS.commands.teleport;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.arab.COMMANDMAPUSE.CommandUse;
import pl.arab.Main;
import pl.arab.TOOLS.utils.AdventureUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TpaCommand extends CommandUse {

    public TpaCommand() {
        super("tpa", Collections.emptyList());
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgMustBePlayer));
            return;
        }

        if (args.length != 1) {
            player.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgTpaUsage));
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            player.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgPlayerOffline));
            return;
        }

        if (player.equals(target)) {
            player.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgTpSelf));
            return;
        }

        if (Main.getInstance().getTeleportManager().hasRequestFrom(target, player)) {
            player.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgAlreadySent));
            return;
        }

        Main.getInstance().getTeleportManager().addRequest(player, target);

        player.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgRequestSent.replace("{PLAYER}", target.getName())));

        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("PLAYER", player.getName());

        for (String line : getPlugin().getPluginConfig().requestReceived) {
            target.sendMessage(AdventureUtil.miniMessage(line, placeholders));
        }
    }

    @Override
    public List<String> tab(Player p, String[] args) {
        return null; // Domyślnie zwraca listę graczy online
    }
}