package pl.arab.TOOLS.commands.tools;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.arab.COMMANDMAPUSE.CommandUse;
import pl.arab.TOOLS.utils.AdventureUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ChatCommand extends CommandUse {

    public ChatCommand() {
        super("chat", Arrays.asList("czat"));
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if (!sender.hasPermission("wisemc.chat.admin")) {
            sender.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgNoPermission.replace("{PERM}", "wisemc.chat.admin")));
            return;
        }

        if (args.length == 0) {
            sender.sendMessage(AdventureUtil.miniMessage(getPlugin().getPluginConfig().msgChatUsage, null));
            return;
        }

        switch (args[0].toLowerCase()) {
            case "on":
                getPlugin().getPluginConfig().chatEnabled = true;
                Bukkit.broadcast(AdventureUtil.miniMessage("<green>Czat został włączony przez administrację!", null));
                break;
            case "off":
                getPlugin().getPluginConfig().chatEnabled = false;
                Bukkit.broadcast(AdventureUtil.miniMessage("<red>Czat został wyłączony przez administrację!", null));
                break;
            case "clear":
            case "cc":
                for (int i = 0; i < 100; i++) {
                    Bukkit.broadcast(AdventureUtil.miniMessage(" ", null));
                }
                Bukkit.broadcast(AdventureUtil.miniMessage("<green>Czat został wyczyszczony!", null));
                break;
            default:
                sender.sendMessage(AdventureUtil.miniMessage(getPlugin().getPluginConfig().msgChatUsage, null));
                break;
        }
    }

    @Override
    public List<String> tab(Player p, String[] args) {
        if (args.length == 1) {
            return Arrays.asList("on", "off", "clear");
        }
        return Collections.emptyList();
    }
}