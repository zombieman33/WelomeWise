package welcomewise.welcomewise.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import welcomewise.welcomewise.WelcomeWise;
import welcomewise.welcomewise.utils.ItemUtil;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class WelcomeCommand implements CommandExecutor, TabCompleter {
    private final WelcomeWise plugin;

    MiniMessage miniMessage = MiniMessage.miniMessage();
    LegacyComponentSerializer legacyColors = LegacyComponentSerializer.legacyAmpersand();
    Component helpMessage = miniMessage.deserialize("""
                    <#33FB13><bold>Welcome Wise </#33FB13>
    
                    <green>/ws help
                    /ws reload
                    /ws reset
                    /ws message <message>
                    /ws broadcastMessage <message>
                    /ws firstMessage <message>
            """);

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

        if (args.length >= 1) {
            if (args[0].equalsIgnoreCase("help")) {
                if (player.hasPermission("welcomewise.command.help")) {
                    player.sendMessage(helpMessage);
                } else {
                    player.sendMessage(ChatColor.RED + "You don't have permission to run this command");
                }
            } else if (args[0].equalsIgnoreCase("reload")) {
                if (player.hasPermission("welcomewise.command.reload")) {
                    try {
                        plugin.reloadConfig();
                        player.sendMessage(ChatColor.GREEN + "Config reloaded successfully!");
                    } catch (Exception e) {
                        player.sendMessage(ChatColor.RED + "An error occurred while reloading the plugin: " + e.getMessage());
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "You don't have permission to run this command");
                }
            } else if (args[0].equalsIgnoreCase("message")) {
                String message = args[1];
                if (player.hasPermission("welcomewise.command.message")) {
                    plugin.getConfig().set("message", message);
                    plugin.saveConfig();
                    player.sendMessage("You set the new join message to " + ItemUtil.color(message));
                } else {
                    player.sendMessage(ChatColor.RED + "You don't have permission to run this command.");
                }
            } else if (args[0].equalsIgnoreCase("broadcastMessage")) {
                String broadcastMessage = args[1].replace("%player%", player.getName() + " <this will be changed to the players name>");
                if (player.hasPermission("welcomewise.command.broadcastmessage")) {
                    plugin.getConfig().set("broadcast", broadcastMessage);
                    plugin.saveConfig();
                    player.sendMessage("You set the new join broadcast message to " + ItemUtil.color(broadcastMessage));
                } else {
                    player.sendMessage(ChatColor.RED + "You don't have permission to run this command.");
                }

            } else if (args[0].equalsIgnoreCase("firstMessage")) {
                String message = args[1];
                if (player.hasPermission("welcomewise.command.firstmessage")) {
                    plugin.getConfig().set("firstMessage", message);
                    plugin.saveConfig();
                    player.sendMessage("You set the new join first message to " + ItemUtil.color(message));
                } else {
                    player.sendMessage(ChatColor.RED + "You don't have permission to run this command.");
                }
            } else if (args[0].equalsIgnoreCase("reset")) {
                if (player.hasPermission("welcomewise.command.reset")) {
                    plugin.saveResource("config.yml", true);
                    player.sendMessage(ChatColor.GREEN + "You reset the config file");
                } else {
                    player.sendMessage(ChatColor.RED + "You don't have permission to run this command.");
                }
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        Player player = (Player) sender;

        if (args.length == 1) {
            if (player.hasPermission("welcomewise.command.help")) {
                completions.add("help");
            }
            if (player.hasPermission("welcomewise.command.reload")) {
                completions.add("reload");
            }
            if (player.hasPermission("welcomewise.command.message")) {
                completions.add("message");
            }
            if (player.hasPermission("welcomewise.command.broadcastmessage")) {
                completions.add("broadcastMessage");
            }
            if (player.hasPermission("welcomewise.command.firstmessage")) {
                completions.add("firstMessage");
            }
            if (player.hasPermission("welcomewise.command.reset")) {
                completions.add("reset");
            }
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("message")) {
                if (player.hasPermission("welcomewise.command.message")) {
                    completions.add("<message>");
                }
            } else if (args[0].equalsIgnoreCase("firstMessage")) {
                if (player.hasPermission("welcomewise.command.ifmessage")) {
                    completions.add("<message>");
                }
            } else if (args[0].equalsIgnoreCase("broadcastMessage")) {
                if (player.hasPermission("welcomewise.command.broadcastmessage")) {
                    completions.add("<message>");
                }
            }
        }
        return completions;
    }
}
