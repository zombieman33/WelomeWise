package welcomewise.welcomewise.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import welcomewise.welcomewise.WelcomeWise;

public class SaveJoinPlayerInConfig implements Listener {
    private WelcomeWise plugin;
    public SaveJoinPlayerInConfig(WelcomeWise plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String pName = player.getName();
        boolean hasPlayedBefore = player.hasPlayedBefore();

        if (!hasPlayedBefore) {
            plugin.getConfig().set("welcomeCommand.joinedPlayer.player", pName);
        }
        plugin.saveConfig();
    }
}
