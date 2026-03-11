package pl.arab.COMMANDMAPUSE;

import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.SimplePluginManager;
import pl.arab.Main;
import pl.arab.TOOLS.commands.tools.*;

import java.lang.reflect.Field;
import java.util.stream.Stream;

public class CommandMap {
    private final Main plugin;
    private SimpleCommandMap scm;

    public CommandMap(Main plugin) {
        this.plugin = plugin;
        this.setupSimpleCommandMap();
        Stream.of(
                new AdminChatCommand(),
                new BroadcastCommand(),
                new KoszCommand(),
                new KodCommand(),
                new ChatCommand()
        ).forEach(this::registerCommands);
    }

    private void registerCommands(CommandUse cmd) {
        this.scm.register(this.plugin.getDescription().getName(), cmd);
    }

    private void setupSimpleCommandMap() {
        SimplePluginManager spm = (SimplePluginManager) this.plugin.getServer().getPluginManager();
        try {
            Field f = SimplePluginManager.class.getDeclaredField("commandMap");
            f.setAccessible(true);
            this.scm = (SimpleCommandMap) f.get(spm);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}