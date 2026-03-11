// --- ReplyCommand.java ---
package pl.arab.TOOLS.commands.msg;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.arab.COMMANDMAPUSE.CommandUse;
import pl.arab.Main;
import pl.arab.TOOLS.utils.AdventureUtil;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class ReplyCommand extends CommandUse {

    public ReplyCommand() {
        super("reply", java.util.Arrays.asList("r"));
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) return;

        if (args.length < 1) {
            player.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgReplyUsage));
            return;
        }

        UUID targetUUID = Main.getInstance().getMessageManager().getLastMessaged(player.getUniqueId());
        if (targetUUID == null) {
            player.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgNoReply));
            return;
        }

        Player target = Bukkit.getPlayer(targetUUID);
        if (target == null) {
            player.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgPlayerOffline));
            return;
        }

        String message = String.join(" ", args);
        MsgCommand.sendPrivateMessage(player, target, message);
    }

    @Override
    public List<String> tab(Player p, String[] args) {
        return Collections.emptyList();
    }
}