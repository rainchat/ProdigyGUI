package fr.cocoraid.prodigygui.resourse.command.subcommands;


import fr.cocoraid.prodigygui.resourse.threedimensionalgui.ThreeDimensionGUI;
import fr.cocoraid.prodigygui.resourse.threedimensionalgui.ThreeDimensionalMenu;
import fr.cocoraid.prodigygui.utils.general.Command;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class OpenCommand extends Command {

    public OpenCommand() {
        super("open", "open <menu> <player>");
    }

    @Override
    public boolean run(Player player, String[] args) {
        if (args.length == 3) {
            ThreeDimensionalMenu menu = checkConditions(player, args);
            if (menu == null) return false;

            new ThreeDimensionGUI(Bukkit.getPlayer(args[2]), menu)
                    .openGui();
        }


        //prodigygui open <menu> <playername> <yawRotation>
        else if (args.length == 4) {

            ThreeDimensionalMenu menu = checkConditions(player, args);
            if (menu == null) return false;

            try {
                Double.valueOf(args[3]);
            } catch (Exception e) {
                player.sendMessage("§cThe yaw rotation " + args[3] + " must be integer !");
                player.sendMessage("§c/prodigygui open <menu> <playername> <yawRotation>");
                return false;
            }

            Player p = Bukkit.getPlayer(args[2]);
            new ThreeDimensionGUI(p, menu)
                    .setRotation(Float.valueOf(args[3]))
                    .openGui();


        } else if (args.length == 7) {
            if (args[0].equalsIgnoreCase("open")) {

                ThreeDimensionalMenu menu = checkConditions(player, args);
                if (menu == null) return false;

                try {
                    Double.valueOf(args[2]);
                } catch (Exception e) {
                    player.sendMessage("§cThe yaw rotation " + args[3] + " must be integer !");
                    player.sendMessage("§c/prodigygui open <menu> <playername> <yawRotation> <x> <y> <z> ");
                    return false;
                }

                try {
                    Double.valueOf(args[4]);
                    Double.valueOf(args[5]);
                    Double.valueOf(args[6]);
                } catch (Exception e) {
                    player.sendMessage("§cThe y,y,z positions " + args[1] + " must be integer !");
                    player.sendMessage("§c/prodigygui open <menu> <playername> <yawRotation> <x> <y> <z>");
                    return false;
                }


                new ThreeDimensionGUI(Bukkit.getPlayer(args[2]), menu)
                        .setRotation(Float.valueOf(args[3]))
                        .setCenter(Double.valueOf(args[4]), Double.valueOf(args[5]), Double.valueOf(args[6]))
                        .openGui();

            }
        }

        return true;
    }

    private ThreeDimensionalMenu checkConditions(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (!p.hasPermission("prodigygui.other.open")) {
                p.sendMessage("language.no_permission");
                return null;
            }
        }

        if (Bukkit.getPlayer(args[2]) == null || (Bukkit.getPlayer(args[2]) != null && !Bukkit.getPlayer(args[2]).isOnline())) {
            sender.sendMessage("Player " + args[2] + " is not online");
            return null;
        }


        ThreeDimensionalMenu menu = ThreeDimensionalMenu.getMenus().stream()
                .filter(m -> m.getFileName().replace(".yml", "").equalsIgnoreCase(args[1])).findAny()
                .orElseGet(() -> null);
        if (menu == null) {
            sender.sendMessage("§cMenu " + args[1] + " could not be found !");
            return null;
        } else return menu;
    }

    @Override
    public List<String> tabRun(Player player, String[] args) {
        if (args.length == 2) {
            List<String> list = new ArrayList<>();
            for (ThreeDimensionalMenu menu : ThreeDimensionalMenu.getMenus()) {
                list.add(menu.getFileName().replace(".yml", ""));
            }
            return list;
        }

        return null;

    }
}
