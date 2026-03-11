package pl.arab.TOOLS.commands.tools;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.arab.COMMANDMAPUSE.CommandUse;
import pl.arab.TOOLS.utils.AdventureUtil;

import java.util.*;

public class VanishCommand extends CommandUse {

    public static final Set<UUID> vanished = new HashSet<>();

    public VanishCommand() {
        super("vanish", Arrays.asList("v"));
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if (!sender.hasPermission("wisemc.vanish")) {
            sender.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgNoPermission.replace("{PERM}", "wisemc.vanish")));
            return;
        }

        if (!(sender instanceof Player player)) return;

        if (vanished.contains(player.getUniqueId())) {
            vanished.remove(player.getUniqueId());
            for (Player target : Bukkit.getOnlinePlayers()) {
                target.showPlayer(getPlugin(), player);
            }
            player.sendMessage(AdventureUtil.miniMessage(getPlugin().getPluginConfig().msgVanishDisabled, Collections.emptyMap()));
        } else {
            vanished.add(player.getUniqueId());
            for (Player target : Bukkit.getOnlinePlayers()) {
                if (!target.hasPermission("wisemc.vanish.see")) {
                    target.hidePlayer(getPlugin(), player);
                }
            }
            player.sendMessage(AdventureUtil.miniMessage(getPlugin().getPluginConfig().msgVanishEnabled, Collections.emptyMap()));
        }
    }

    @Override
    public List<String> tab(Player p, String[] args) {
        return Collections.emptyList();
    }
}