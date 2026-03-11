// --- HelpopCommand.java ---
package pl.arab.TOOLS.commands.tools;

import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.arab.COMMANDMAPUSE.CommandUse;
import pl.arab.TOOLS.utils.AdventureUtil;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HelpopCommand extends CommandUse {

    public HelpopCommand() {
        super("helpop", Collections.emptyList());
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) return;

        if (args.length == 0) {
            player.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgHelpopUsage));
            return;
        }

        String message = String.join(" ", args);
        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("PLAYER", player.getName());
        placeholders.put("MESSAGE", message);

        player.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgHelpopSent));

        String titleStr = getPlugin().getPluginConfig().helpopReceivedTitle;
        String[] titleParts = titleStr.split("\n");
        Title title = Title.title(
                AdventureUtil.miniMessage(titleParts.length > 0 ? titleParts[0] : "", placeholders),
                AdventureUtil.miniMessage(titleParts.length > 1 ? titleParts[1] : "", placeholders),
                Title.Times.times(Duration.ofMillis(500), Duration.ofSeconds(3), Duration.ofMillis(500))
        );

        for (Player admin : Bukkit.getOnlinePlayers()) {
            if (admin.hasPermission("wisemc.helpop.receive")) {
                admin.showTitle(title);
                for (String line : getPlugin().getPluginConfig().helpopReceived) {
                    admin.sendMessage(AdventureUtil.miniMessage(line, placeholders));
                }
            }
        }
    }

    @Override
    public List<String> tab(Player p, String[] args) {
        return Collections.emptyList();
    }
}