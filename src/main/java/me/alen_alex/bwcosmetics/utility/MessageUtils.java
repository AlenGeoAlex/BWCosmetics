package me.alen_alex.bwcosmetics.utility;

import me.alen_alex.bwcosmetics.BWCosmetics;
import net.md_5.bungee.api.chat.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MessageUtils {
    public static String parseColor(String input) {
        if(input == null)
            return null;
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    public static void sendMessage(Player player, String message, boolean showPrefix) {
        if(message == null)
            return;
        if (showPrefix)
            player.sendMessage(BWCosmetics.getPlugin().getConfiguration().getPrefixMain() + parseColor(message));
        else
            player.sendMessage(parseColor(message));
    }

    public static void sendMessage(CommandSender sender, String message, boolean showPrefix) {
        if(message == null)
            return;
        if (showPrefix)
            sender.sendMessage(BWCosmetics.getPlugin().getConfiguration().getPrefixMain() + parseColor(message));
        else
            sender.sendMessage(parseColor(message));
    }

    public static void sendMessageNoPrefix(Player player, String message) {
        if(message == null)
            return;
        player.sendMessage(parseColor(message));
    }

    public static void sendMessageNoPrefix(CommandSender sender, String message) {
        if(message == null)
            return;
        sender.sendMessage(message);
    }

    public static void broadcastMessageNoPrefix(String message) {
        if(message == null)
            return;
        Bukkit.getServer().broadcastMessage(parseColor(message));
    }


    public static void broadcastMessage(String message) {
        if(message == null)
            return;
        Bukkit.getServer().broadcastMessage(BWCosmetics.getPlugin().getConfiguration().getPrefixMain() + parseColor(message));
    }

    public static void sendJSONSuggestMessage(Player player, String Message, String SuggestionCommand, String HoverText, boolean ShowPrefix) {
        if(Message == null)
            return;
        TextComponent tc = new TextComponent();
        if (ShowPrefix)
            tc.setText(BWCosmetics.getPlugin().getConfiguration().getPrefixMain() + parseColor(Message));
        else
            tc.setText(parseColor(Message));
        tc.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, SuggestionCommand));
        tc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder(parseColor(HoverText)).create())));
        player.spigot().sendMessage((BaseComponent) tc);
    }

    public static void sendJSONSuggestMessage(CommandSender sender, String Message, String SuggestionCommand, String HoverText, boolean ShowPrefix) {
        if(Message == null)
            return;
        if(!(sender instanceof Player))
            return;
        Player player = (Player)sender;
        TextComponent tc = new TextComponent();
        if (ShowPrefix)
            tc.setText(BWCosmetics.getPlugin().getConfiguration().getPrefixMain() + parseColor(Message));
        else
            tc.setText(parseColor(Message));
        tc.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, SuggestionCommand));
        tc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder(parseColor(HoverText)).create())));
        player.spigot().sendMessage((BaseComponent) tc);
    }

    public static void sendJSONExecuteCommand(Player player, String Message, String RunCommand, String HoverText, boolean ShowPrefix) {
        if(Message == null)
            return;
        TextComponent tc = new TextComponent();
        if (ShowPrefix)
            tc.setText(BWCosmetics.getPlugin().getConfiguration().getPrefixMain() + parseColor(Message));
        else
            tc.setText(parseColor(Message));
        tc.setText(BWCosmetics.getPlugin().getConfiguration().getPrefixMain() + parseColor(Message));
        tc.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, RunCommand));
        tc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder(parseColor(HoverText)).create())));
        player.spigot().sendMessage(((BaseComponent) tc));
    }

    public static void sendJSONLink(Player player, String Message, String redirectTo, String HoverText) {
        if(Message == null)
            return;
        TextComponent tc = new TextComponent();
        tc.setText(parseColor(Message));
        tc.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, redirectTo));
        tc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder(parseColor(HoverText)).create())));
        player.spigot().sendMessage(((BaseComponent) tc));
    }

    public static void sendJSONExecuteCommand(CommandSender sender, String Message, String RunCommand, String HoverText, boolean ShowPrefix) {
        if(Message == null)
            return;
        if(!(sender instanceof Player))
            return;
        Player player = (Player)sender;
        TextComponent tc = new TextComponent();
        if (ShowPrefix)
            tc.setText(BWCosmetics.getPlugin().getConfiguration().getPrefixMain() + parseColor(Message));
        else
            tc.setText(parseColor(Message));
        tc.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, RunCommand));
        tc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder(parseColor(HoverText)).create())));
        player.spigot().sendMessage(((BaseComponent) tc));
    }

    public static void sendJSONHoverMessage(Player player, String Message, String HoverText, boolean ShowPrefix) {
        if(Message == null)
            return;
        TextComponent tc = new TextComponent();
        if (ShowPrefix)
            tc.setText(BWCosmetics.getPlugin().getConfiguration().getPrefixMain() + parseColor(Message));
        else
            tc.setText(parseColor(Message));
        tc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder(parseColor(HoverText)).create())));
        player.spigot().sendMessage((BaseComponent) tc);
    }

    public static void sendBroadcastMessage(String message, boolean showPrefix){
        if(message == null)
            return;
        if(showPrefix)
            Bukkit.getServer().broadcastMessage(BWCosmetics.getPlugin().getConfiguration()+parseColor(message));
        else
            Bukkit.getServer().broadcastMessage(parseColor(message));
    }


}
