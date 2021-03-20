package fr.cocoraid.prodigygui.resourse.command;


import fr.cocoraid.prodigygui.ProdigyGUI;
import fr.cocoraid.prodigygui.resourse.command.subcommands.HelpCommand;
import fr.cocoraid.prodigygui.utils.general.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.*;

public class ProdigyGuiCommand extends Command implements TabCompleter {

    private final ProdigyGUI prodigyGUI;
    private Set<Command> commands;

    public ProdigyGuiCommand(ProdigyGUI prodigyGUI) {
        super("prodigygui", "");
        this.prodigyGUI = prodigyGUI;
        this.commands = new HashSet<>();
    }

    public boolean run(Player player, String[] args) throws IOException {


        if (args.length > 0) {
            for (Command command : commands) {
                if (args[0].equalsIgnoreCase(command.toString())) {
                    if (player.hasPermission(toString() + ".command." + command.toString())) {
                        command.run(player, args);
                    } else {
                        player.sendMessage(ProdigyGUI.getInstance().getLanguage().no_permission);
                    }
                    break;
                }
            }
        } else {
            if (player.hasPermission(toString() + ".command." + "help")) {
                return new HelpCommand(this).run(player, args);
            } else {
                player.sendMessage(ProdigyGUI.getInstance().getLanguage().no_permission);
            }
        }

        return false;
    }

    @Override
    public List<String> tabRun(Player player, String[] args) {
        return null;
    }

    public void initialise(Command... commands) {
        this.commands.addAll(Arrays.asList(commands));
    }

    public Set<Command> getCommands() {
        return Collections.unmodifiableSet(commands);
    }


    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] strings) {
        List<String> list = new ArrayList<>();
        if (!(commandSender instanceof Player)) {
            return null;
        }
        Player player = (Player) commandSender;
        if (strings.length == 1) {
            for (Command subCommand : commands) {
                if (commandSender.hasPermission(toString() + ".command." + subCommand.toString())) {
                    list.add(subCommand.toString());
                }
            }
        } else {
            for (Command subCommand : commands) {
                if (subCommand.toString().equalsIgnoreCase(strings[0])) {
                    return subCommand.tabRun(player, strings);
                }
            }
        }
        return list;
    }
}