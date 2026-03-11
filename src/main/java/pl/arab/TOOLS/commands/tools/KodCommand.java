package pl.arab.TOOLS.commands.tools;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.arab.COMMANDMAPUSE.CommandUse;
import pl.arab.TOOLS.utils.AdventureUtil;

import java.util.Collections;
import java.util.List;

public class KodCommand extends CommandUse {

    public KodCommand() {
        super("kod", Collections.singletonList("kody"));
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(AdventureUtil.translate(getPlugin().getPluginConfig().msgMustBePlayer));
            return;
        }

        if (args.length != 1) {
            player.sendMessage(AdventureUtil.miniMessage(getPlugin().getPluginConfig().msgCodeUsage, Collections.emptyMap()));
            return;
        }

        String inputCode = args[0].toLowerCase();

        player.sendMessage(AdventureUtil.miniMessage(getPlugin().getPluginConfig().msgCodeSuccess.replace("{CODE}", inputCode), Collections.emptyMap()));
    }

    @Override
    public List<String> tab(Player p, String[] args) {
        return Collections.emptyList();
    }
}