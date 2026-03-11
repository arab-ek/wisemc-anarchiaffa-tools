package pl.arab.TOOLS.managers;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class MessageManager {
    private final Map<UUID, UUID> lastMessaged = new ConcurrentHashMap<>();

    public void setLastMessaged(UUID sender, UUID target) {
        lastMessaged.put(sender, target);
        lastMessaged.put(target, sender); // Żeby druga osoba mogła też odpisać
    }

    public UUID getLastMessaged(UUID sender) {
        return lastMessaged.get(sender);
    }
}