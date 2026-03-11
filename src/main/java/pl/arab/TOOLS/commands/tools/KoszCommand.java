package pl.arab.TOOLS.commands.tools;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import pl.arab.COMMANDMAPUSE.CommandUse;
import pl.arab.TOOLS.utils.AdventureUtil;

import java.util.Collections;
import java.util.List;

public class KoszCommand extends CommandUse {

    public KoszCommand() {
        super("kosz", Collections.singletonList("smietnik"));
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgMustBePlayer));
            return;
        }

        Inventory trashGui = Bukkit.createInventory(null, 54, AdventureUtil.miniMessage("<b><dark_gray>Kosz", Collections.emptyMap()));
        player.openInventory(trashGui);
    }

    @Override
    public List<String> tab(Player p, String[] args) {
        return Collections.emptyList();
    }
}