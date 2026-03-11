package pl.arab.COMMANDMAPUSE;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.entity.Player;
import pl.arab.Main;

import java.util.ArrayList;
import java.util.List;

public abstract class CommandUse extends Command implements PluginIdentifiableCommand {
    public Main plugin = Main.getInstance();

    public CommandUse(String name, List<String> aliases) {
        super(name);
        if (aliases != null) {
            this.setAliases(aliases);
        }
    }

    public Main getPlugin() {
        return this.plugin;
    }

    public abstract void run(CommandSender sender, String[] args);

    public abstract List<String> tab(Player p, String[] args);

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] arguments) {
        this.run(sender, arguments);
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) return new ArrayList<>();
        List<String> completions = this.tab((Player)sender, args);
        if (completions == null) {
            return new ArrayList<>();
        }
        return completions;
    }
}