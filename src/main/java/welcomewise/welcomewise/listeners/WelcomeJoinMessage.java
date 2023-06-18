package welcomewise.welcomewise.listeners;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import welcomewise.welcomewise.WelcomeWise;
import welcomewise.welcomewise.utils.ColorUtil;

public class WelcomeJoinMessage implements Listener {

    private WelcomeWise plugin;

    public WelcomeJoinMessage(WelcomeWise plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String pName = player.getName();
        boolean defaultJoinMessage = plugin.getConfig().getBoolean("defaultJoinMessage");

        // First join config
        boolean shouldGetAFirstMessage = plugin.getConfig().getBoolean("shouldSendFirstMessage");
        String firstMessageConfig = plugin.getConfig().getString("firstMessage");

        String firstMessage = firstMessageConfig
                .replace("%player%", pName)
                .replace("%online-players%", String.valueOf(plugin.getServer().getOnlinePlayers()));

        firstMessage = PlaceholderAPI.setPlaceholders(null, firstMessage);

        // Join Config
        boolean shouldGetAMessage = plugin.getConfig().getBoolean("shouldSendMessage");
        String messageConfig = plugin.getConfig().getString("message");

        String message = messageConfig
                .replace("%player%", pName)
                .replace("%online-players%", String.valueOf(Bukkit.getServer().getOnlinePlayers().size()));

        message = PlaceholderAPI.setPlaceholders(null, message);

        // Broadcast config
        boolean ifBroadcast = plugin.getConfig().getBoolean("shouldSendBroadcast");
        boolean onlyBroadcastOnFirstJoin = plugin.getConfig().getBoolean("firstJoinBroadcast");
        String broadcastConfig = plugin.getConfig().getString("broadcast");

        String broadcast = broadcastConfig
                .replace("%player%", pName)
                .replace("%online-players%", String.valueOf(plugin.getServer().getOnlinePlayers()));

        broadcast = PlaceholderAPI.setPlaceholders(null, broadcast);

        if (!defaultJoinMessage) {
            event.setJoinMessage(null);
        }

        boolean isFirstJoin = !player.hasPlayedBefore();
        String colorBroadcast = ColorUtil.color(broadcast);

        if (isFirstJoin) {
            if (shouldGetAFirstMessage) {
                // First message
                player.sendMessage(ColorUtil.color(firstMessage));
            }
            if (onlyBroadcastOnFirstJoin && ifBroadcast) {
                for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                    p.sendMessage(colorBroadcast);
                }
            }
        } else {
            if (shouldGetAMessage) {
                // Message
                player.sendMessage(ColorUtil.color(message));
            }
            if (ifBroadcast) {
                for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                    p.sendMessage(colorBroadcast);
                }
            }
        }
    }
}