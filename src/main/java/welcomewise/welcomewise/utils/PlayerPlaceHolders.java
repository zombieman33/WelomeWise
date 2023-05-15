package welcomewise.welcomewise.utils;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import welcomewise.welcomewise.WelcomeWise;

public class PlayerPlaceHolders extends PlaceholderExpansion {

    private final WelcomeWise plugin;

    public PlayerPlaceHolders(WelcomeWise plugin) {
        this.plugin = plugin;
    }
    @Override
    public String getIdentifier() {
        return "player";
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
            if (identifier.equals("player")) {
                return player.getName();
            }
        }
        return null;
    }
}

