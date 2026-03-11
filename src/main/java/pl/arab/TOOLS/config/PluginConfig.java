package pl.arab.TOOLS.config;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Names(strategy = NameStrategy.IDENTITY, modifier = NameModifier.TO_LOWER_CASE)
public class PluginConfig extends OkaeriConfig {

    // --- BAZA DANYCH (SQL) ---
    public String dbHost = "localhost";
    public int dbPort = 3306;
    public String dbDatabase = "wisemc";
    public String dbUser = "root";
    public String dbPassword = "password";
    public boolean useMySQL = false; // Jeśli false - użyje lokalnego SQLite (plik database.db), jeśli true - MySQL

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

    // KeyAll Event
    public boolean keyAllEventEnabled = true;
    public Map<String, String> keyAllKeys = Map.of(
            "SKARBÓW", "Klucze Skarbów|fastcase rozdaj skarbow all {AMOUNT}",
            "RZADKA", "Klucze Rzadkie|fastcase rozdaj rzadka all {AMOUNT}",
            "ANARCHICZNA", "Klucze Anarchiczne|fastcase rozdaj anarchiczna all {AMOUNT}",
            "EVENTOWA", "Klucze Eventowe|fastcase rozdaj eventowa all {AMOUNT}",
            "KOSTIUMÓW", "Klucze Kostiumów|fastcase rozdaj kostiumów all {AMOUNT}",
            "AFK", "Klucze Afk|fastcase rozdaj afk all {AMOUNT}",
            "ZWIERZAKÓW", "Klucze Zwierzaków|fastcase rozdaj zwierzaków all {AMOUNT}"
    );
    public String keyAllBossBarTitle = "<#97CFF5>☀ <dark_gray>⁑ <white>Za <#97CFF5>{TIME} <white>cały serwer otrzyma <#97CFF5><b>{KEY} x{AMOUNT}</b>!";
    public String keyAllBossBarColor = "BLUE";
    public String keyAllBossBarStyle = "NOTCHED_20";

    public String msgKeyAllStarted = "<green>Uruchomiono event <dark_green>{EVENT} <green>na <dark_green>{TIME} <green>minut!";
    public String msgKeyAllStopped = "<green>Zatrzymano event <dark_green>{EVENT}";
    public String msgKeyAllAlreadyActive = "<red>Ten event jest już <dark_red>aktywny!";
    public String msgKeyAllNotFound = "<red>Nie znaleziono eventu o nazwie <dark_red>{EVENT}!";
    public String msgKeyAllUsage = "<red>Poprawne użycie: <dark_red>/autoevent start/stop [event] [czas w min] [Skrzynia] [Ilość]";

    // --- TELEPORTACJA TPA ---
    public String msgTpAcceptUsage = "&cPoprawne użycie: &4/tpaccept [gracz/*]";
    public String msgNoRequests = "&cNie masz żadnych próśb o &4teleportację!";
    public String msgTpAcceptedTarget = "&aGracz &2{PLAYER} &azaakceptował twoją prośbę o &2teleportację!";
    public String msgTpAcceptedSender = "&aZaakceptowano teleportację gracza &2{PLAYER}";
    public String msgRequestSent = "&aWysłano prośbę o teleportację do gracza &2{PLAYER}";
    public List<String> requestReceived = Arrays.asList(
            "<white>Gracz <#97CFF5>{PLAYER} <white>chce się do ciebie <#97CFF5>przeteleportować!",
            "<white>Wpisz <#97CFF5>/tpaccept {PLAYER} <white>aby zaakceptować",
            "<white>Wpisz <#97CFF5>/tpaccept * <white>aby zaakceptować wszystkich"
    );
    public String msgAlreadySent = "&cWysłałeś już prośbę o teleportację do tego &4gracza!";
    public String msgTpSelf = "&cNie możesz teleportować się sam do siebie!";
    public String msgTpaUsage = "&cPoprawne użycie: &4/tpa [gracz]";

    // --- TELEPORTACJA ADMIN ---
    public String msgTphereUsage = "&cPoprawne użycie: &4/s [gracz]";
    public String msgTphereSuccess = "&aPrzeteleportowano do siebie gracza &2{PLAYER}";
    public String msgTpUsage = "&cPoprawne użycie: &4/tp [gracz]";
    public String msgTpSuccess = "&aPrzeteleportowano do gracza &2{PLAYER}";

    // --- SPAWN ---
    public String msgSpawnTeleport = "&aPomyślnie przeteleportowano na spawn!";
    public String msgSetSpawn = "&aPomyślnie ustawiono nową lokalizację spawnu!";
    public org.bukkit.Location spawnLocation = null;

    // --- FLY & SPEED ---
    public String msgSpeedUsage = "&cPoprawne użycie: &4/speed [1-10]";
    public String msgSpeedRange = "&cWartość musi być z zakresu &41 do 10";
    public String msgFlySpeedSet = "&aUstawiono prędkość latania na &2{SPEED}";
    public String msgWalkSpeedSet = "&aUstawiono prędkość chodzenia na &2{SPEED}";
    public String msgFlyEnabled = "&aLatanie zostało &2włączone!";
    public String msgFlyDisabled = "&cLatanie zostało &4wyłączone!";

    // --- GAMEMODE ---
    public String msgGamemodeUsage = "&cPoprawne użycie: &4/gamemode [0/1/2/3] [gracz]";
    public String msgGamemodeChangedSelf = "&aZmieniono twój tryb gry na &2{MODE}";
    public String msgGamemodeChangedOther = "&aZmieniono tryb gry gracza &2{PLAYER} &ana &2{MODE}";

    // --- WIADOMOŚCI (MSG) ---
    public String msgMsgUsage = "&cPoprawne użycie: &4/msg [gracz] [wiadomość]";
    public String msgReplyUsage = "&cPoprawne użycie: &4/r [wiadomość]";
    public String msgNoReply = "&cZ nikim ostatnio &4nie pisałeś!";
    public String msgSelfMsg = "&cCzemu piszesz sam &4ze sobą?";
    public String formatMsg = "<dark_gray>(<#97CFF5>{SENDER} <dark_gray>→ <#49B5FF>{RECEIVER}<dark_gray>)\n<dark_gray>» <white>{MESSAGE}";

    // --- HELPOP ---
    public String msgHelpopUsage = "&cPoprawne użycie: &4/helpop [treść]";
    public String msgHelpopCooldown = "&cZwolnij wysyłanie wiadomości na &4helpopie!";
    public String msgHelpopSent = "&aWiadomość do administracji &2została wysłana!";
    public String helpopReceivedTitle = "<#97CFF5><b>Helpop</b>\n<white>{MESSAGE}";
    public List<String> helpopReceived = java.util.Arrays.asList(
            " <dark_gray>▷ <#97CFF5><b>Helpop <dark_gray>(<white>{PLAYER}<dark_gray>)",
            " <dark_gray>- <red>{MESSAGE}"
    );

    // --- EKONOMIA (PAY) ---
    public double payTaxPercent = 10.0; // Podatek 10%
    public double payMinAmount = 100.0;
    public String msgPayUsage = "&cPoprawne użycie: &4/pay [gracz] [ilość]";
    public String msgPaySelf = "&cAle co ty robisz?";
    public String msgPayMin = "&cNie możesz przelać mniej niż &4$100";
    public String msgNoMoney = "&cNie posiadasz tyle &4pieniędzy!";
    public String msgPaySent = "&aPrzelano &2${AMOUNT} &adla gracza &2{PLAYER} &8(&cPodatek: ${TAX}&8)";
    public String msgPayReceived = "&aOtrzymałeś &2${AMOUNT} &aod gracza &2{PLAYER}";

    // --- REPAIR & INVSEE ---
    public String msgRepairSuccess = "&aPrzedmiot został &2naprawiony!";
    public String msgRepairAllSuccess = "&aPrzedmioty zostały &2naprawione!";
    public String msgRepairError = "&cNie możesz tego &4naprawić!";
    public String msgInvseeUsage = "&cPoprawne użycie: &4/invsee [gracz]";

    // --- EKONOMIA ADMIN (/eco) ---
    public String msgEcoUsage = "&cPoprawne użycie: &4/eco [give/take/set] [gracz] [kwota]";
    public String msgEcoGive = "&aDodano &2${AMOUNT} &ado konta gracza &2{PLAYER}";
    public String msgEcoTake = "&aZabrano &2${AMOUNT} &az konta gracza &2{PLAYER}";
    public String msgEcoSet = "&aUstawiono stan konta gracza &2{PLAYER} &ana &2${AMOUNT}";
}