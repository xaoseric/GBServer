package com.Gbserver.variables;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class ChatWriter {


    public static void write(ChatWriterType cwt, String message) {

        Bukkit.broadcastMessage(getMessage(cwt, message));
    }

    public static String getMessage(ChatWriterType cwt, String message) {
        String output = ChatColor.BLUE + "";
        switch (cwt) {
            case ANNOUNCEMENT:
                output += "Announcement> " + ChatColor.YELLOW;
                break;
            case CHAT:
                output += "Chat> " + ChatColor.GRAY;
                break;
            case CONDITION:
                output += "Condition> " + ChatColor.GRAY;
                break;
            case ERROR:
                output += "Error> " + ChatColor.GRAY;
                break;
            case GAME:
                output += "Game> " + ChatColor.GRAY;
                break;
            case COMMAND:
                output += "Command> " + ChatColor.GRAY;
                break;
            case SERVER:
                output += "Server> " + ChatColor.YELLOW;
                break;
            case VOTE:
                output += "Vote> " + ChatColor.GRAY;
                break;
            case JOIN:
                output += ChatColor.DARK_AQUA + "Join> " + ChatColor.GRAY;
                break;
            case QUIT:
                output += ChatColor.DARK_AQUA + "Quit> " + ChatColor.GRAY;
                break;
            case HELP:
                output += ChatColor.LIGHT_PURPLE + "Help> " + ChatColor.WHITE;
                break;
            case TPA:
                output += ChatColor.GOLD + "TPA> " + ChatColor.AQUA;
                break;
            case HOME:
                output += ChatColor.GREEN + "HOME> " + ChatColor.GOLD;
                break;
            case DEATH:
                output += ChatColor.RED + "Death> " + ChatColor.DARK_RED;
                break;
            case WARP:
                output += ChatColor.GOLD + "Warp> " + ChatColor.AQUA;
                break;
            case EVENT:
                output += ChatColor.DARK_AQUA + "Event> " + ChatColor.GOLD;

        }
        output += message;
        return output;
    }

    public static void writeTo(CommandSender sender, ChatWriterType cwt, String message) {
        sender.sendMessage(getMessage(cwt, message));
    }
}
