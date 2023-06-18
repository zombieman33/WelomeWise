package welcomewise.welcomewise;

import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import welcomewise.welcomewise.commands.WelcomeCommand;
import welcomewise.welcomewise.commands.WelcomeWiseCommand;
import welcomewise.welcomewise.listeners.SaveJoinPlayerInConfig;
import welcomewise.welcomewise.utils.PlayerPlaceHolders;
import welcomewise.welcomewise.utils.PlayersOnlinePlaceHolders;
import welcomewise.welcomewise.listeners.WelcomeJoinCommands;
import welcomewise.welcomewise.listeners.WelcomeJoinMessage;

import java.io.File;

public final class WelcomeWise extends JavaPlugin {

    private PlayerPlaceHolders playerNamePlaceHolders;
    private PlayersOnlinePlaceHolders onlinePlayers;

    @Override
    public void onEnable() {
        // Plugin startup logic

        // Configs
        File configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            getLogger().info("Config file not found, creating...");
            saveResource("config.yml", false);
        }

        // Apis
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
            getLogger().warning("Could not find PlaceholderAPI! This plugin is required.");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        // Placeholders
//        playerNamePlaceHolders.register();
//        onlinePlayers.register();

        // Commands
        WelcomeWiseCommand welcomeWiseCommand = new WelcomeWiseCommand(this);
        PluginCommand mainCmd = getCommand("welcomewise");
        if (mainCmd != null) mainCmd.setExecutor(welcomeWiseCommand);

        WelcomeCommand welcomeCommand = new WelcomeCommand(this);
        PluginCommand welcomeCmd = getCommand("welcome");
        if (welcomeCmd != null) welcomeCmd.setExecutor(welcomeCommand);

        // Listeners
        new WelcomeJoinMessage(this);
        new WelcomeJoinCommands(this);
        new SaveJoinPlayerInConfig(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
//        playerNamePlaceHolders.unregister();
//        onlinePlayers.unregister();
    }
}
