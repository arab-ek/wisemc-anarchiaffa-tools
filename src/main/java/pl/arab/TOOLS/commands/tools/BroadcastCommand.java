package pl.arab.TOOLS.commands.tools;

import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.arab.COMMANDMAPUSE.CommandUse;
import pl.arab.TOOLS.utils.AdventureUtil;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BroadcastCommand extends CommandUse {

    public BroadcastCommand() {
        super("broadcast", Arrays.asList("bc", "alert", "ogloszenie"));
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if (!sender.hasPermission("wisemc.broadcast")) {
            sender.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgNoPermission.replace("{PERM}", "wisemc.broadcast")));
            return;
        }

        if (args.length == 0) {
            sender.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgBroadcastUsage));
            return;
        }

        String message = String.join(" ", args);
        var titleComp = AdventureUtil.miniMessage(getPlugin().getPluginConfig().broadcastTitle, Collections.emptyMap());
        var subtitleComp = AdventureUtil.miniMessage(message, Collections.emptyMap());

        Title.Times times = Title.Times.times(Duration.ofSeconds(1), Duration.ofSeconds(4), Duration.ofSeconds(1));
        Title finalTitle = Title.title(titleComp, subtitleComp, times);

        for (Player online : Bukkit.getOnlinePlayers()) {
            online.showTitle(finalTitle);
        }
    }

    @Override
    public List<String> tab(Player p, String[] args) {
        return Collections.emptyList();
    }
}