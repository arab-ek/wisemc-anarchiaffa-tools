package pl.arab.TOOLS.data;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class User {
    private final UUID uuid;
    private String name;
    private double balance;
    private long playtime; // w sekundach
    private final Set<String> usedCodes;

    public User(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
        this.balance = 0.0;
        this.playtime = 0;
        this.usedCodes = new HashSet<>();
    }

    public UUID getUuid() { return uuid; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }
    public void addBalance(double amount) { this.balance += amount; }
    public void removeBalance(double amount) { this.balance -= amount; }

    public long getPlaytime() { return playtime; }
    public void addPlaytime(long seconds) { this.playtime += seconds; }

    public Set<String> getUsedCodes() { return usedCodes; }
    public boolean hasUsedCode(String code) { return usedCodes.contains(code.toLowerCase()); }
    public void addUsedCode(String code) { usedCodes.add(code.toLowerCase()); }
}