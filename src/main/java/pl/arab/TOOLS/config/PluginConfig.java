package pl.arab.TOOLS.config;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;

@Names(strategy = NameStrategy.IDENTITY, modifier = NameModifier.TO_LOWER_CASE)
public class PluginConfig extends OkaeriConfig {

    // Chat
    public boolean chatEnabled = true;
    public int chatCooldownSeconds = 5;
    public String msgChatDisabled = "<red>Chat jest aktualnie <dark_red>wyłączony!";
    public String msgChatCooldown = "<red>Kolejną wiadomość wyślesz za <dark_red>{TIME}<red>sek!";
    public String msgChatUsage = "<red>Poprawne użycie: <dark_red>/chat [on/off/clear/cooldown]";

    // AdminChat
    public String msgAdminChatPrefix = "<b><#EC8C8C>ᴀᴅᴍɪɴᴄʜᴀᴛ</gradient></b> <dark_gray>♯ ";
    public String msgAdminChatUsage = "&cPoprawne użycie: &4/ac [wiadomość]";

    // Kody
    public String msgCodeUsage = "<red>Musisz wpisać kod który chcesz <dark_red>użyć!";
    public String msgCodeNotExists = "<red>Ten kod nie <dark_red>istnieje!";
    public String msgCodeAlreadyUsed = "<red>Wykorzystałeś już ten <dark_red>kod!";
    public String msgCodeRequiredTime = "<red>By użyć tego kodu potrzebujesz grać jeszcze <dark_red>{TIME}";
    public String msgCodeSuccess = "<green>Pomyślnie użyto kodu <dark_green>{CODE}!";

    // Broadcast
    public String msgBroadcastUsage = "&cPoprawne użycie: &4/alert [wiadomość]";
    public String broadcastTitle = "<b><gradient:#EC6FA3:#F297BD:#EC6FA3>ᴡɪѕᴇ</gradient><white>ᴍᴄ.ᴘʟ</b>";

    // Vanish
    public String msgVanishEnabled = "<green>Vanish został <dark_green>włączony!";
    public String msgVanishDisabled = "<red>Vanish został <dark_red>wyłączony!";

    // Inne
    public String msgNoPermission = "&cNie posiadasz permisji &4{PERM}";
    public String msgPlayerOffline = "&cTen gracz jest aktualnie &4offline!";
    public String msgMustBePlayer = "&cMusisz być graczem, aby użyć tej komendy!";
}