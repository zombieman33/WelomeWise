package welcomewise.welcomewise.listeners;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import welcomewise.welcomewise.WelcomeWise;

import java.util.List;

public class WelcomeJoinCommands implements Listener {

    private WelcomeWise plugin;

    public WelcomeJoinCommands(WelcomeWise plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerDeathCommand(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        boolean ifCommand = plugin.getConfig().getBoolean("shouldUseCommand");
        List<String> commands = plugin.getConfig().getStringList("commands");

        if (ifCommand) {
            for (String command : commands) {
                String formattedCommand = command.replace("%player%", player.getName());
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), formattedCommand);
            }
        }
    }
}
