package fr.cocoraid.prodigygui;

import fr.cocoraid.prodigygui.filemanager.config.CoreConfig;
import fr.cocoraid.prodigygui.filemanager.language.Language;
import fr.cocoraid.prodigygui.filemanager.language.LanguageLoader;
import fr.cocoraid.prodigygui.filemanager.loader.CommandListener;
import fr.cocoraid.prodigygui.filemanager.loader.FileLoader;
import fr.cocoraid.prodigygui.hooks.EconomyBridge;
import fr.cocoraid.prodigygui.hooks.PlaceholderAPIBridge;
import fr.cocoraid.prodigygui.protocol.InteractableItemProtocol;
import fr.cocoraid.prodigygui.resourse.command.ProdigyGuiCommand;
import fr.cocoraid.prodigygui.resourse.command.subcommands.HelpCommand;
import fr.cocoraid.prodigygui.resourse.command.subcommands.OpenCommand;
import fr.cocoraid.prodigygui.resourse.event.BreakBlockEvent;
import fr.cocoraid.prodigygui.resourse.event.ItemInteractEvent;
import fr.cocoraid.prodigygui.resourse.event.JoinQuitEvent;
import fr.cocoraid.prodigygui.resourse.task.ThreeDimensionalGUITask;
import fr.cocoraid.prodigygui.resourse.threedimensionalgui.ThreeDimensionalMenu;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ProdigyGUI extends JavaPlugin {

    /**
     * TODO: make thing buyable
     */


    private Language language;
    private CoreConfig config;

    private static ProdigyGUI instance;


    @Override
    public void onEnable() {
        instance = this;
        ConsoleCommandSender c = Bukkit.getServer().getConsoleSender();

        loadConfiguration();

        if (!EconomyBridge.setupEconomy()) {
            getLogger().warning("Vault with a compatible economy plugin was not found! Icons with a PRICE or commands that give money will not work.");
        }

        PlaceholderAPIBridge placeholderAPIBridge = new PlaceholderAPIBridge();
        placeholderAPIBridge.setupPlugin();
        if (placeholderAPIBridge.hasValidPlugin()) {
            getLogger().info("Hooked PlaceholderAPI");
        }

        new LanguageLoader(this);


        if (!LanguageLoader.getLanguages().containsKey(config.language.toLowerCase())) {
            c.sendMessage("§c Language not found ! Please check your language folder");
        } else
            language = LanguageLoader.getLanguage(config.language.toLowerCase());
        c.sendMessage(ChatColor.GREEN + "Language: " + (language == null ? "english" : config.language.toLowerCase()));
        if (language == null)
            language = LanguageLoader.getLanguage("english");


        new FileLoader(this);
        new InteractableItemProtocol(this);
        new ThreeDimensionalGUITask(this);
        Bukkit.getPluginManager().registerEvents(new CommandListener(), this);
        Bukkit.getPluginManager().registerEvents(new JoinQuitEvent(), this);
        Bukkit.getPluginManager().registerEvents(new BreakBlockEvent(), this);
        Bukkit.getPluginManager().registerEvents(new ItemInteractEvent(), this);

        ProdigyGuiCommand prodigyGuiCommand = new ProdigyGuiCommand(this);
        prodigyGuiCommand.initialise(
                new OpenCommand(),
                new HelpCommand(prodigyGuiCommand)

        );

        getLogger().info("Registered " + prodigyGuiCommand.getCommands().size() + " sub-command(s).");

        Objects.requireNonNull(getCommand(prodigyGuiCommand.toString())).setExecutor(prodigyGuiCommand);

    }

    @Override
    public void onDisable() {
        ProdigyGUIPlayer.getProdigyPlayers().values().stream().filter(pp -> pp.getThreeDimensionGUI() != null && pp.getThreeDimensionGUI().isSpawned()).forEach(pp -> {
            pp.getThreeDimensionGUI().closeGui();
        });
        try {
            config.save();
        } catch (final InvalidConfigurationException ex) {
            ex.printStackTrace();
            getLogger().log(Level.SEVERE, "Oooops ! Something went wrong while saving the configuration !");
        }
    }

    private void loadConfiguration() {
        final Logger logger = getLogger();
        try {
            config = new CoreConfig(new File(this.getDataFolder(), "configuration.yml"));
            config.load();
        } catch (final InvalidConfigurationException ex) {
            ex.printStackTrace();
            logger.log(Level.SEVERE, "Oooops ! Something went wrong while loading the configuration !");
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }


    private ThreeDimensionalMenu checkConditions(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (!p.hasPermission("prodigygui.other.open")) {
                p.sendMessage(language.no_permission);
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

    public CoreConfig getConfiguration() {
        return config;
    }

    public Language getLanguage() {
        return language;
    }

    public static ProdigyGUI getInstance() {
        return instance;
    }
}
