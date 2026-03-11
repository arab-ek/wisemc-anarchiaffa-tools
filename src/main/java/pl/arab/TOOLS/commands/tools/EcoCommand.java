package pl.arab.TOOLS.commands.tools;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.arab.COMMANDMAPUSE.CommandUse;
import pl.arab.Main;
import pl.arab.TOOLS.utils.AdventureUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EcoCommand extends CommandUse {

    public EcoCommand() {
        super("eco", Arrays.asList("ekonomia", "economy"));
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if (!sender.hasPermission("wisemc.eco.admin")) {
            sender.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgNoPermission.replace("{PERM}", "wisemc.eco.admin")));
            return;
        }

        if (args.length < 3) {
            sender.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgEcoUsage));
            return;
        }

        String action = args[0].toLowerCase();
        Player target = Bukkit.getPlayer(args[1]);

        if (target == null) {
            sender.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgPlayerOffline));
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(args[2]);
        } catch (NumberFormatException e) {
            sender.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgEcoUsage));
            return;
        }

        Economy econ = Main.getInstance().getEconomy();
        if (econ == null) {
            sender.sendMessage(AdventureUtil.miniMessage("<red>Błąd: Vault nie został poprawnie załadowany!", null));
            return;
        }

        switch (action) {
            case "give":
            case "add":
                econ.depositPlayer(target, amount);
                sender.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgEcoGive
                        .replace("{AMOUNT}", String.format("%.2f", amount))
                        .replace("{PLAYER}", target.getName())));
                break;
            case "take":
            case "remove":
                econ.withdrawPlayer(target, amount);
                sender.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgEcoTake
                        .replace("{AMOUNT}", String.format("%.2f", amount))
                        .replace("{PLAYER}", target.getName())));
                break;
            case "set":
                double currentBal = econ.getBalance(target);
                econ.withdrawPlayer(target, currentBal); // Zerujemy
                econ.depositPlayer(target, amount);      // Ustawiamy nową
                sender.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgEcoSet
                        .replace("{AMOUNT}", String.format("%.2f", amount))
                        .replace("{PLAYER}", target.getName())));
                break;
            default:
                sender.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgEcoUsage));
                break;
        }
    }

    @Override
    public List<String> tab(Player p, String[] args) {
        if (!p.hasPermission("wisemc.eco.admin")) return Collections.emptyList();

        if (args.length == 1) {
            return Arrays.asList("give", "take", "set");
        } else if (args.length == 2) {
            return null; // Zwróci listę graczy
        } else if (args.length == 3) {
            return Arrays.asList("100", "1000", "10000");
        }
        return Collections.emptyList();
    }
}