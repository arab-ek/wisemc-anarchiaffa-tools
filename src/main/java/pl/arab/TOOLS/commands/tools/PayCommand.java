// --- PayCommand.java ---
package pl.arab.TOOLS.commands.tools;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.arab.COMMANDMAPUSE.CommandUse;
import pl.arab.Main;
import pl.arab.TOOLS.utils.AdventureUtil;
import net.milkbowl.vault.economy.Economy;

import java.util.Collections;
import java.util.List;

public class PayCommand extends CommandUse {

    public PayCommand() {
        super("pay", java.util.Arrays.asList("przelej"));
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) return;

        if (args.length != 2) {
            player.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgPayUsage));
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            player.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgPlayerOffline));
            return;
        }

        if (player.equals(target)) {
            player.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgPaySelf));
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(args[1]);
        } catch (NumberFormatException e) {
            player.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgPayUsage));
            return;
        }

        double minAmount = getPlugin().getPluginConfig().payMinAmount;
        if (amount < minAmount) {
            player.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgPayMin));
            return;
        }

        Economy econ = Main.getInstance().getEconomy();
        if (!econ.has(player, amount)) {
            player.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgNoMoney));
            return;
        }

        double taxPercent = getPlugin().getPluginConfig().payTaxPercent;
        double tax = amount * (taxPercent / 100.0);
        double finalAmount = amount - tax;

        econ.withdrawPlayer(player, amount);
        econ.depositPlayer(target, finalAmount);

        player.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgPaySent
                .replace("{AMOUNT}", String.format("%.2f", amount))
                .replace("{PLAYER}", target.getName())
                .replace("{TAX}", String.format("%.2f", tax))));

        target.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgPayReceived
                .replace("{AMOUNT}", String.format("%.2f", finalAmount))
                .replace("{PLAYER}", player.getName())));
    }

    @Override
    public List<String> tab(Player p, String[] args) {
        return null;
    }
}