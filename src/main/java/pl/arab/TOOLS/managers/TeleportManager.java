package pl.arab.TOOLS.managers;

import org.bukkit.entity.Player;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class TeleportManager {

    // Kto do kogo wysłał prośbę. Key: Target (Odbiorca), Value: Set<Sender> (Nadawcy)
    private final Map<UUID, Set<UUID>> tpaRequests = new ConcurrentHashMap<>();

    public void addRequest(Player sender, Player target) {
        tpaRequests.computeIfAbsent(target.getUniqueId(), k -> ConcurrentHashMap.newKeySet()).add(sender.getUniqueId());
    }

    public boolean hasRequestFrom(Player target, Player sender) {
        Set<UUID> requests = tpaRequests.get(target.getUniqueId());
        return requests != null && requests.contains(sender.getUniqueId());
    }

    public boolean hasAnyRequests(Player target) {
        Set<UUID> requests = tpaRequests.get(target.getUniqueId());
        return requests != null && !requests.isEmpty();
    }

    public void removeRequest(Player target, Player sender) {
        Set<UUID> requests = tpaRequests.get(target.getUniqueId());
        if (requests != null) {
            requests.remove(sender.getUniqueId());
        }
    }

    public Set<UUID> getAllRequests(Player target) {
        return tpaRequests.getOrDefault(target.getUniqueId(), Collections.emptySet());
    }

    public void clearAllRequests(Player target) {
        tpaRequests.remove(target.getUniqueId());
    }
}