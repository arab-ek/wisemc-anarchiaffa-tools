// --- BalCommand.java ---
package pl.arab.TOOLS.commands.tools;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.arab.COMMANDMAPUSE.CommandUse;
import pl.arab.Main;
import pl.arab.TOOLS.utils.AdventureUtil;

import java.util.Collections;
import java.util.List;

public class BalCommand extends CommandUse {

    public BalCommand() {
        super("bal", java.util.Arrays.asList("balance", "kasa", "stanikonta"));
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) return;

        Player target = player;
        if (args.length == 1 && player.hasPermission("wisemc.bal.other")) {
            target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                player.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgPlayerOffline));
                return;
            }
        }

        double balance = Main.getInstance().getEconomy().getBalance(target);
        String msg = target.equals(player)
                ? "&aTwój stan konta wynosi: &2$" + String.format("%.2f", balance)
                : "&aStan konta gracza &2" + target.getName() + " &awynosi: &2$" + String.format("%.2f", balance);

        player.sendMessage(AdventureUtil.translate(msg));
    }

    @Override
    public List<String> tab(Player p, String[] args) {
        return null;
    }
}