package fr.cocoraid.prodigygui.resourse.command.subcommands;


import fr.cocoraid.prodigygui.resourse.command.ProdigyGuiCommand;
import fr.cocoraid.prodigygui.utils.general.Command;
import org.bukkit.entity.Player;

import java.util.List;

public class HelpCommand extends Command {

    private final ProdigyGuiCommand prodigyGuiCommand;

    public HelpCommand(ProdigyGuiCommand prodigyGuiCommand) {
        super("help", "help");
        this.prodigyGuiCommand = prodigyGuiCommand;
    }

    @Override
    public boolean run(Player player, String[] args) {

        player.sendMessage("§c/prodigygui open <menu> <playername> <yawRotation> <x> <y> <z>");
        player.sendMessage("§c/prodigygui open <menu> <playername> <yawRotation>");
        player.sendMessage("§c/prodigygui open <menu> <playername>");

        return true;
    }

    @Override
    public List<String> tabRun(Player player, String[] args) {
        return null;
    }
}
