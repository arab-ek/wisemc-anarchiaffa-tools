package pl.arab.TOOLS.commands.tools;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.arab.COMMANDMAPUSE.CommandUse;
import pl.arab.TOOLS.utils.AdventureUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AdminChatCommand extends CommandUse {

    public AdminChatCommand() {
        super("adminchat", Arrays.asList("ac", "a"));
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if (!sender.hasPermission("wisemc.adminchat")) {
            sender.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgNoPermission.replace("{PERM}", "wisemc.adminchat")));
            return;
        }

        if (args.length == 0) {
            sender.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgAdminChatUsage));
            return;
        }

        String message = String.join(" ", args);
        String format = getPlugin().getPluginConfig().msgAdminChatPrefix + "<white>" + sender.getName() + " <dark_gray>» <white>" + message;

        Bukkit.getOnlinePlayers().stream()
                .filter(p -> p.hasPermission("wisemc.adminchat"))
                .forEach(p -> p.sendMessage(AdventureUtil.miniMessage(format, Collections.emptyMap())));

        Bukkit.getConsoleSender().sendMessage(AdventureUtil.miniMessage(format, Collections.emptyMap()));
    }

    @Override
    public List<String> tab(Player p, String[] args) {
        return Collections.emptyList();
    }
}