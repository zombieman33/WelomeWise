package welcomewise.welcomewise.utils;

import com.tcoded.legacycolorcodeparser.LegacyColorCodeParser;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Nullable;
import welcomewise.welcomewise.WelcomeWise;

import java.util.ArrayList;
import java.util.List;

public class ItemUtil {

    public ItemUtil(WelcomeWise plugin) {
    }

    public static void makeItemGlow(ItemStack events) {
        events.editMeta(ItemMeta.class, meta -> {
            meta.addEnchant(Enchantment.DURABILITY, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
            meta.addItemFlags(ItemFlag.HIDE_DYE);
            meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        });
    }

    public static void makeItemHideAll(ItemStack events) {
        events.editMeta(ItemMeta.class, meta -> {
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
            meta.addItemFlags(ItemFlag.HIDE_DYE);
            meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        });

        // 1.19 ONLY
        try {
            events.editMeta(ItemMeta.class, meta -> {
                meta.addItemFlags(ItemFlag.valueOf("HIDE_ITEM_SPECIFICS"));
            });
        } catch (IllegalArgumentException ignored) {}
    }

    public static ItemStack makePunishItem(Material material, String title, String description) {
        ItemStack item = new ItemStack(material);
        MiniMessage minimessage = MiniMessage.miniMessage();

        List<String> loreBuilder = new ArrayList<>();
        loreBuilder.add("<reset> ");

        List<String> lines = makeLoreFromString(description, 30);
        loreBuilder.addAll(lines);

        // Rest
        loreBuilder.add("<reset> ");

        makeItem(item, "<reset>%s".formatted(title), loreBuilder.toArray(String[]::new));
        return item;
    }

    public static List<String> makeLoreFromString(String description, int maxLineLength) {
        MiniMessage minimessage = MiniMessage.miniMessage();
        List<String> lines = new ArrayList<>();

        // DESC
        StringBuilder strb = new StringBuilder();
        String[] descParts = description.split(" ");
        strb.append("<reset><gray>");
        for (int i = 0; i < descParts.length; i++) {
            String currWord = descParts[i];
            String fullLineNoTags = minimessage.stripTags(strb.toString());
            String currWordNoTags = minimessage.stripTags(currWord);
            if (fullLineNoTags.length() + currWordNoTags.length() >= maxLineLength) {
                String fullLine = strb.toString();
                lines.add(fullLine);
                strb = new StringBuilder();
                strb.append("<reset><gray>");
            }
            strb.append(currWord);
            strb.append(" ");
        }

        lines.add(strb.toString());
        return lines;
    }

    public static ItemStack makeItem(Material material, String name, @Nullable List<String> lore) {
        ItemStack item = new ItemStack(material);
        String[] loreArr = lore == null ? null : lore.toArray(String[]::new);
        return makeItem(item, name, loreArr);
    }

    public static ItemStack makeItem(Material material, String name, @Nullable String... lore) {
        ItemStack item = new ItemStack(material);
        return makeItem(item, name, lore);
    }

    public static ItemStack makeItem(ItemStack item, String name, @Nullable List<String> lore) {
        String[] loreArr = lore == null ? null : lore.toArray(String[]::new);
        return  makeItem(item, name, loreArr);
    }

    public static ItemStack makeItem(ItemStack item, String name, @Nullable String... lore) {
        ItemMeta meta = item.getItemMeta();
        MiniMessage miniMessage = MiniMessage.miniMessage();
        meta.displayName(miniMessage.deserialize(name).style(style -> style.decoration(TextDecoration.ITALIC, false)));

        if (lore != null) {
            List<Component> loreComponents = new ArrayList<>();
            for (String line : lore) {
                loreComponents.add(miniMessage.deserialize(line).style(style -> style.decoration(TextDecoration.ITALIC, false)));
            }
            meta.lore(loreComponents);
        }

        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack makeCustomItem(ItemStack item, String name, List<String> lore, @Nullable String... rarity) {
        ItemMeta meta = item.getItemMeta();
        MiniMessage miniMessage = MiniMessage.miniMessage();
        meta.displayName(miniMessage.deserialize(name).style(style -> style.decoration(TextDecoration.ITALIC, false)));

        List<Component> loreComponents = new ArrayList<>();
        if (!lore.isEmpty()) {
            for (String line : lore) {
                loreComponents.add(miniMessage.deserialize(line).style(style -> style.decoration(TextDecoration.ITALIC, false)));
            }
        }
        if (rarity != null) {
            for (String line : rarity) {
                loreComponents.add(miniMessage.deserialize(line).style(style -> style.decoration(TextDecoration.ITALIC, false)));
            }
        }

        meta.lore(loreComponents);
        item.setItemMeta(meta);
        return item;
    }
    public static ItemStack makeGuiItem(ItemStack item, String name, List<String> lore) {
        ItemMeta meta = item.getItemMeta();
        MiniMessage miniMessage = MiniMessage.miniMessage();
        meta.displayName(miniMessage.deserialize(name).style(style -> style.decoration(TextDecoration.ITALIC, false)));

        List<Component> loreComponents = new ArrayList<>();
        if (!lore.isEmpty()) {
            for (String line : lore) {
                loreComponents.add(miniMessage.deserialize(line).style(style -> style.decoration(TextDecoration.ITALIC, false)));
            }
        }

        meta.lore(loreComponents);
        item.setItemMeta(meta);
        return item;
    }
    public static String color(String string) {
        string = LegacyColorCodeParser.convertHexToLegacy('&', string);
        string = ChatColor.translateAlternateColorCodes('&', string);
        return string;
    }
}