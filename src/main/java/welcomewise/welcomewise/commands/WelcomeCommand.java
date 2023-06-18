package welcomewise.welcomewise.commands;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import welcomewise.welcomewise.WelcomeWise;
import welcomewise.welcomewise.utils.ColorUtil;

import java.util.ArrayList;
import java.util.List;

public class WelcomeCommand implements CommandExecutor, TabCompleter {
    private final WelcomeWise plugin;
    public WelcomeCommand(WelcomeWise plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be run by a player.");
            return true;
        }

        Player player = (Player) sender;
        String pName = player.getName();

        if (player.hasPermission("welcomewise.command.welcome")) {
            String newPlayerName = plugin.getConfig().getString("welcomeCommand.joinedPlayer.player");
            Player newPlayer = Bukkit.getPlayerExact(newPlayerName);

            if (newPlayer != null) {
                String message = plugin.getConfig().getString("welcomeCommand.joinedPlayer.format");

                List<String> commandToRun = plugin.getConfig().getStringList("welcomeCommand.joinedPlayer.command");
                Server server = Bukkit.getServer();

                for (String commands : commandToRun) {
                    commands = commands
                            .replace("%joined-player%", newPlayerName)
                            .replace("%player%", pName)
                            .replace("/", "");
                    server.dispatchCommand(server.getConsoleSender(), commands);
                }

                message = message
                        .replace("%joined-player%", newPlayerName)
                        .replace("%player%", pName)
                        .replace("%online-players%", String.valueOf(plugin.getServer().getOnlinePlayers()));

                newPlayer.sendMessage(ColorUtil.color(message));
                if (newPlayer != player) {
                    player.sendMessage(ChatColor.GREEN + "You welcomed " + newPlayerName);
                }
            } else {
                String noNewPlayerErrorMessage = plugin.getConfig().getString("welcomeCommand.joinedPlayer.noNewPlayerErrorMessage").replace("%joined-player%", newPlayerName).replace("%player%", pName);
                player.sendMessage(ColorUtil.color(noNewPlayerErrorMessage));
                return true;
            }
        } else {
            player.sendMessage(ChatColor.RED + "You don't have permission to run this command.");
        }
        return true;
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        completions.add("");

        return completions;
    }
}
