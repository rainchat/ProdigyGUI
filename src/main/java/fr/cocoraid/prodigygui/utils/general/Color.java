package fr.cocoraid.prodigygui.utils.general;

import net.md_5.bungee.api.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Color {
    private static final Pattern HEX_PATTERN = Pattern.compile("<(#[a-fA-F0-9]{6})>");


    public static boolean serverSupportsHex() {
        try {
            ChatColor.of(java.awt.Color.BLACK);
            return true;
        } catch (NoSuchMethodError ignore) {
            return false;
        }
    }

    public static String parseHexString(String text, Pattern hexPattern) {
        if (text == null) {
            return "";
        }
        Matcher hexColorMatcher = hexPattern.matcher(text);

        if (serverSupportsHex()) {
            while (hexColorMatcher.find()) {
                String hex = hexColorMatcher.group(1);
                ChatColor color = ChatColor.of(hex);


                String before = text.substring(0, hexColorMatcher.start());
                String after = text.substring(hexColorMatcher.end());
                text = before + color + after;
                hexColorMatcher = hexPattern.matcher(text);

            }
        }

        return org.bukkit.ChatColor.translateAlternateColorCodes('&', text);
    }

    public static String parseHexString(String text) {
        return parseHexString(text, Color.HEX_PATTERN);
    }
}
