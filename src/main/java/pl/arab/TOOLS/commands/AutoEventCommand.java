package pl.arab.TOOLS.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.arab.COMMANDMAPUSE.CommandUse;
import pl.arab.Main;
import pl.arab.TOOLS.utils.AdventureUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AutoEventCommand extends CommandUse {

    public AutoEventCommand() {
        super("autoevent", Arrays.asList("keyall", "aevent"));
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if (!sender.hasPermission("wisemc.autoevent")) {
            sender.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgNoPermission.replace("{PERM}", "wisemc.autoevent")));
            return;
        }

        if (!getPlugin().getPluginConfig().keyAllEventEnabled) {
            sender.sendMessage(AdventureUtil.miniMessage("<red>Moduł AutoEvent jest wyłączony w configu!", null));
            return;
        }

        // /autoevent start/stop [event] [czas w min] [Skrzynia] [Ilość]
        if (args.length < 2) {
            sender.sendMessage(AdventureUtil.miniMessage(getPlugin().getPluginConfig().msgKeyAllUsage, null));
            return;
        }

        String action = args[0].toLowerCase();
        String eventName = args[1];

        if (action.equals("stop")) {
            if (Main.getInstance().getAutoEventManager().stopEvent(eventName)) {
                sender.sendMessage(AdventureUtil.miniMessage(getPlugin().getPluginConfig().msgKeyAllStopped
                        .replace("{EVENT}", eventName), null));
            } else {
                sender.sendMessage(AdventureUtil.miniMessage(getPlugin().getPluginConfig().msgKeyAllNotFound
                        .replace("{EVENT}", eventName), null));
            }
            return;
        }

        if (action.equals("start")) {
            if (args.length < 5) {
                sender.sendMessage(AdventureUtil.miniMessage(getPlugin().getPluginConfig().msgKeyAllUsage, null));
                return;
            }

            if (Main.getInstance().getAutoEventManager().isEventActive(eventName)) {
                sender.sendMessage(AdventureUtil.miniMessage(getPlugin().getPluginConfig().msgKeyAllAlreadyActive, null));
                return;
            }

            int time;
            int amount;
            try {
                time = Integer.parseInt(args[2]);
                amount = Integer.parseInt(args[4]);
            } catch (NumberFormatException e) {
                sender.sendMessage(AdventureUtil.miniMessage("<red>Czas oraz ilość muszą być liczbami!", null));
                return;
            }

            String keyType = args[3].toUpperCase();
            if (!getPlugin().getPluginConfig().keyAllKeys.containsKey(keyType)) {
                sender.sendMessage(AdventureUtil.miniMessage("<red>Taki klucz nie istnieje w configu!", null));
                return;
            }

            Main.getInstance().getAutoEventManager().startEvent(eventName, time, keyType, amount);
            sender.sendMessage(AdventureUtil.miniMessage(getPlugin().getPluginConfig().msgKeyAllStarted
                    .replace("{EVENT}", eventName)
                    .replace("{TIME}", String.valueOf(time)), null));
            return;
        }

        sender.sendMessage(AdventureUtil.miniMessage(getPlugin().getPluginConfig().msgKeyAllUsage, null));
    }

    @Override
    public List<String> tab(Player p, String[] args) {
        if (!p.hasPermission("wisemc.autoevent")) return Collections.emptyList();

        if (args.length == 1) {
            return Arrays.asList("start", "stop");
        } else if (args.length == 2) {
            return Collections.singletonList("KEY_ALL");
        } else if (args.length == 3 && args[0].equalsIgnoreCase("start")) {
            return Arrays.asList("5", "10", "15", "30", "60");
        } else if (args.length == 4 && args[0].equalsIgnoreCase("start")) {
            return new ArrayList<>(getPlugin().getPluginConfig().keyAllKeys.keySet());
        } else if (args.length == 5 && args[0].equalsIgnoreCase("start")) {
            return Arrays.asList("1", "2", "3", "5", "10");
        }
        return Collections.emptyList();
    }
}