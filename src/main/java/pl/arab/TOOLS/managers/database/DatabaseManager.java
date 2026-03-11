package pl.arab.TOOLS.managers.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.Bukkit;
import pl.arab.Main;
import pl.arab.TOOLS.data.User;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.UUID;

public class DatabaseManager {

    private HikariDataSource dataSource;
    private final Main plugin;

    public DatabaseManager(Main plugin) {
        this.plugin = plugin;
        connect();
        createTables();
    }

    private void connect() {
        HikariConfig config = new HikariConfig();

        if (plugin.getPluginConfig().useMySQL) {
            config.setJdbcUrl("jdbc:mysql://" + plugin.getPluginConfig().dbHost + ":" + plugin.getPluginConfig().dbPort + "/" + plugin.getPluginConfig().dbDatabase + "?useSSL=false");
            config.setUsername(plugin.getPluginConfig().dbUser);
            config.setPassword(plugin.getPluginConfig().dbPassword);
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        } else {
            File dbFile = new File(plugin.getDataFolder(), "database.db");
            config.setJdbcUrl("jdbc:sqlite:" + dbFile.getAbsolutePath());
            config.setDriverClassName("org.sqlite.JDBC");
        }

        config.setMaximumPoolSize(10);
        dataSource = new HikariDataSource(config);
    }

    private void createTables() {
        String query = "CREATE TABLE IF NOT EXISTS wisemc_users (" +
                "uuid VARCHAR(36) PRIMARY KEY, " +
                "name VARCHAR(16), " +
                "balance DOUBLE, " +
                "playtime BIGINT, " +
                "used_codes TEXT" +
                ");";
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadUser(UUID uuid, String name) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            User user = new User(uuid, name);
            try (Connection conn = dataSource.getConnection();
                 PreparedStatement ps = conn.prepareStatement("SELECT * FROM wisemc_users WHERE uuid=?")) {
                ps.setString(1, uuid.toString());
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    user.setName(rs.getString("name"));
                    user.setBalance(rs.getDouble("balance"));
                    user.addPlaytime(rs.getLong("playtime"));

                    String codes = rs.getString("used_codes");
                    if (codes != null && !codes.isEmpty()) {
                        Arrays.stream(codes.split(",")).forEach(user::addUsedCode);
                    }
                } else {
                    // Startowy hajs dla nowych graczy (opcjonalnie)
                    user.setBalance(500.0);
                    saveUser(user); // Od razu zapisujemy do bazy
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            // Zapis do pamięci RAM po załadowaniu
            plugin.getUserManager().addUser(user);
        });
    }

    public void saveUser(User user) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            String codes = String.join(",", user.getUsedCodes());
            String query = "REPLACE INTO wisemc_users (uuid, name, balance, playtime, used_codes) VALUES (?, ?, ?, ?, ?)";

            // W SQLite używamy REPLACE INTO, w MySQL lepiej INSERT INTO ... ON DUPLICATE KEY UPDATE
            if (plugin.getPluginConfig().useMySQL) {
                query = "INSERT INTO wisemc_users (uuid, name, balance, playtime, used_codes) VALUES (?, ?, ?, ?, ?) " +
                        "ON DUPLICATE KEY UPDATE name=?, balance=?, playtime=?, used_codes=?";
            }

            try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setString(1, user.getUuid().toString());
                ps.setString(2, user.getName());
                ps.setDouble(3, user.getBalance());
                ps.setLong(4, user.getPlaytime());
                ps.setString(5, codes);

                if (plugin.getPluginConfig().useMySQL) {
                    ps.setString(6, user.getName());
                    ps.setDouble(7, user.getBalance());
                    ps.setLong(8, user.getPlaytime());
                    ps.setString(9, codes);
                }
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public void close() {
        if (dataSource != null) {
            dataSource.close();
        }
    }
}