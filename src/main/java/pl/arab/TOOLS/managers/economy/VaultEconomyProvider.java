package pl.arab.TOOLS.managers.economy;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import pl.arab.Main;
import pl.arab.TOOLS.data.User;

import java.util.Collections;
import java.util.List;

public class VaultEconomyProvider implements Economy {

    private final Main plugin;

    public VaultEconomyProvider(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean isEnabled() { return true; }

    @Override
    public String getName() { return "WiseMCEconomy"; }

    @Override
    public boolean hasBankSupport() { return false; }

    @Override
    public int fractionalDigits() { return 2; }

    @Override
    public String format(double amount) { return "$" + String.format("%.2f", amount); }

    @Override
    public String currencyNamePlural() { return "Dolarów"; }

    @Override
    public String currencyNameSingular() { return "Dolar"; }

    @Override
    public boolean hasAccount(OfflinePlayer player) { return true; } // Zakładamy, że każdy ma

    @Override
    public double getBalance(OfflinePlayer player) {
        User user = plugin.getUserManager().getUser(player.getUniqueId());
        return user != null ? user.getBalance() : 0.0;
        // W idealnym świecie zrobilibyśmy też pobieranie Offline z bazy
    }

    @Override
    public boolean has(OfflinePlayer player, double amount) {
        return getBalance(player) >= amount;
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer player, double amount) {
        if (amount < 0) return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Cannot withdraw negative amount");
        User user = plugin.getUserManager().getUser(player.getUniqueId());
        if (user == null) return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "User not online");

        if (user.getBalance() < amount) {
            return new EconomyResponse(0, user.getBalance(), EconomyResponse.ResponseType.FAILURE, "Insufficient funds");
        }

        user.removeBalance(amount);
        return new EconomyResponse(amount, user.getBalance(), EconomyResponse.ResponseType.SUCCESS, null);
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer player, double amount) {
        if (amount < 0) return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Cannot deposit negative amount");
        User user = plugin.getUserManager().getUser(player.getUniqueId());
        if (user == null) return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "User not online");

        user.addBalance(amount);
        return new EconomyResponse(amount, user.getBalance(), EconomyResponse.ResponseType.SUCCESS, null);
    }

    // --- Reszta wymaganych metod (puste lub delegujące, by interfejs się kompilował) ---
    @Override public boolean hasAccount(String playerName) { return true; }
    @Override public boolean hasAccount(String playerName, String worldName) { return hasAccount(playerName); }
    @Override public boolean hasAccount(OfflinePlayer player, String worldName) { return hasAccount(player); }
    @Override public double getBalance(String playerName) { return 0; }
    @Override public double getBalance(String playerName, String world) { return getBalance(playerName); }
    @Override public double getBalance(OfflinePlayer player, String world) { return getBalance(player); }
    @Override public boolean has(String playerName, double amount) { return false; }
    @Override public boolean has(String playerName, String worldName, double amount) { return false; }
    @Override public boolean has(OfflinePlayer player, String worldName, double amount) { return has(player, amount); }
    @Override public EconomyResponse withdrawPlayer(String playerName, double amount) { return null; }
    @Override public EconomyResponse withdrawPlayer(String playerName, String worldName, double amount) { return null; }
    @Override public EconomyResponse withdrawPlayer(OfflinePlayer player, String worldName, double amount) { return withdrawPlayer(player, amount); }
    @Override public EconomyResponse depositPlayer(String playerName, double amount) { return null; }
    @Override public EconomyResponse depositPlayer(String playerName, String worldName, double amount) { return null; }
    @Override public EconomyResponse depositPlayer(OfflinePlayer player, String worldName, double amount) { return depositPlayer(player, amount); }
    @Override public EconomyResponse createBank(String name, String player) { return null; }
    @Override public EconomyResponse createBank(String name, OfflinePlayer player) { return null; }
    @Override public EconomyResponse deleteBank(String name) { return null; }
    @Override public EconomyResponse bankBalance(String name) { return null; }
    @Override public EconomyResponse bankHas(String name, double amount) { return null; }
    @Override public EconomyResponse bankWithdraw(String name, double amount) { return null; }
    @Override public EconomyResponse bankDeposit(String name, double amount) { return null; }
    @Override public EconomyResponse isBankOwner(String name, String playerName) { return null; }
    @Override public EconomyResponse isBankOwner(String name, OfflinePlayer player) { return null; }
    @Override public EconomyResponse isBankMember(String name, String playerName) { return null; }
    @Override public EconomyResponse isBankMember(String name, OfflinePlayer player) { return null; }
    @Override public List<String> getBanks() { return Collections.emptyList(); }
    @Override public boolean createPlayerAccount(String playerName) { return false; }
    @Override public boolean createPlayerAccount(String playerName, String worldName) { return false; }
    @Override public boolean createPlayerAccount(OfflinePlayer player) { return false; }
    @Override public boolean createPlayerAccount(OfflinePlayer player, String worldName) { return false; }
}