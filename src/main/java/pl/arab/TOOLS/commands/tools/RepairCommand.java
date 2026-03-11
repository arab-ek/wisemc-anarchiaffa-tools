// --- RepairCommand.java ---
package pl.arab.TOOLS.commands.tools;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import pl.arab.COMMANDMAPUSE.CommandUse;
import pl.arab.TOOLS.utils.AdventureUtil;

import java.util.Collections;
import java.util.List;

public class RepairCommand extends CommandUse {

    public RepairCommand() {
        super("repair", Collections.singletonList("napraw"));
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if (!sender.hasPermission("wisemc.repair")) {
            sender.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgNoPermission.replace("{PERM}", "wisemc.repair")));
            return;
        }

        if (!(sender instanceof Player player)) return;

        if (args.length == 1 && args[0].equalsIgnoreCase("all")) {
            if (!sender.hasPermission("wisemc.repair.all")) {
                sender.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgNoPermission.replace("{PERM}", "wisemc.repair.all")));
                return;
            }

            for (ItemStack item : player.getInventory().getContents()) {
                repairItem(item);
            }
            player.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgRepairAllSuccess));
            return;
        }

        ItemStack item = player.getInventory().getItemInMainHand();
        if (repairItem(item)) {
            player.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgRepairSuccess));
        } else {
            player.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgRepairError));
        }
    }

    private boolean repairItem(ItemStack item) {
        if (item == null || item.getType() == Material.AIR) return false;
        ItemMeta meta = item.getItemMeta();
        if (meta instanceof Damageable damageable) {
            if (damageable.getDamage() == 0) return false;
            damageable.setDamage(0);
            item.setItemMeta(meta);
            return true;
        }
        return false;
    }

    @Override
    public List<String> tab(Player p, String[] args) {
        if (args.length == 1 && p.hasPermission("wisemc.repair.all")) {
            return Collections.singletonList("all");
        }
        return Collections.emptyList();
    }
}