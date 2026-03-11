package pl.arab.TOOLS.managers.database;

import org.bukkit.entity.Player;
import pl.arab.TOOLS.data.User;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class UserManager {
    private final Map<UUID, User> users = new ConcurrentHashMap<>();

    public User getUser(UUID uuid) {
        return users.get(uuid);
    }

    public User getUser(Player player) {
        return users.get(player.getUniqueId());
    }

    public void addUser(User user) {
        users.put(user.getUuid(), user);
    }

    public void removeUser(UUID uuid) {
        users.remove(uuid);
    }

    public Map<UUID, User> getUsers() {
        return users;
    }
}