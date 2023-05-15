package welcomewise.welcomewise.utils;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import welcomewise.welcomewise.WelcomeWise;

public class PlayersOnlinePlaceHolders extends PlaceholderExpansion {

    private final WelcomeWise plugin;

    public PlayersOnlinePlaceHolders(WelcomeWise plugin) {
        this.plugin = plugin;
    }
    @Override
    public String getIdentifier() {
        return "online-players";
    }

    @Override
    public String getAuthor() {
        return "zombieman";
    }


    @Override
    public String getVersion() {
        return this.plugin.getDescription().getVersion();
    }

    @Override
    public String onRequest(OfflinePlayer player, String identifier) {
        if (player instanceof Player) {
            if (identifier.equals("online-players")) {
                return String.valueOf(plugin.getServer().getOnlinePlayers());
            }
        }
        return null;
    }
}


