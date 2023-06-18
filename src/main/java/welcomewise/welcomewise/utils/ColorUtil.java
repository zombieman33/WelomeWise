package welcomewise.welcomewise.utils;

import com.tcoded.legacycolorcodeparser.LegacyColorCodeParser;
import org.bukkit.ChatColor;
import welcomewise.welcomewise.WelcomeWise;

public class ColorUtil {

    public ColorUtil(WelcomeWise plugin) {
    }

    public static String color(String string) {
        string = LegacyColorCodeParser.convertHexToLegacy('&', string);
        string = ChatColor.translateAlternateColorCodes('&', string);
        return string;
    }
}