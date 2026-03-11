// --- MsgCommand.java ---
package pl.arab.TOOLS.commands.msg;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.arab.COMMANDMAPUSE.CommandUse;
import pl.arab.Main;
import pl.arab.TOOLS.utils.AdventureUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MsgCommand extends CommandUse {

    public MsgCommand() {
        super("msg", java.util.Arrays.asList("m", "tell", "whisper", "w"));
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) return;

        if (args.length < 2) {
            player.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgMsgUsage));
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            player.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgPlayerOffline));
            return;
        }

        if (player.equals(target)) {
            player.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgSelfMsg));
            return;
        }

        String message = String.join(" ", java.util.Arrays.copyOfRange(args, 1, args.length));
        sendPrivateMessage(player, target, message);
    }

    public static void sendPrivateMessage(Player sender, Player target, String message) {
        Main.getInstance().getMessageManager().setLastMessaged(sender.getUniqueId(), target.getUniqueId());

        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("SENDER", sender.getName());
        placeholders.put("RECEIVER", target.getName());
        placeholders.put("MESSAGE", message);

        String format = Main.getInstance().getPluginConfig().formatMsg;
        sender.sendMessage(AdventureUtil.miniMessage(format, placeholders));
        target.sendMessage(AdventureUtil.miniMessage(format, placeholders));
    }

    @Override
    public List<String> tab(Player p, String[] args) {
        return null;
    }
}