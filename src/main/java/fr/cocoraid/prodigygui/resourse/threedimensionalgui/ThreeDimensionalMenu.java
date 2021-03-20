package fr.cocoraid.prodigygui.resourse.threedimensionalgui;

import fr.cocoraid.prodigygui.resourse.threedimensionalgui.itemdata.ItemData;
import fr.cocoraid.prodigygui.resourse.threedimensionalgui.itemdata.ParticleData;
import fr.cocoraid.prodigygui.resourse.threedimensionalgui.itemdata.SoundData;
import fr.cocoraid.prodigygui.utils.general.Color;
import org.bukkit.Material;

import java.util.LinkedList;
import java.util.List;

public class ThreeDimensionalMenu {

    private static LinkedList<ThreeDimensionalMenu> menus = new LinkedList<>();

    private LinkedList<ItemData> itemDataList;
    private LinkedList<ItemData> barItemsList;

    //required
    private double radius;
    private String title;
    private final String fileName;

    //optionals
    private String previousMenu;
    private String openItemName;
    private int angle_step = 35;
    private SoundData defaultClickSound;
    private SoundData defaultChangeSound;
    private ParticleData defaultClickParticle;
    private String permission;

    //TODO: maybe change to a list
    private List<String> commands;
    private Material openItem;

    public ThreeDimensionalMenu(String filename) {
        this.fileName = filename;
    }

    public ThreeDimensionalMenu setRadius(double radius) {
        this.radius = radius;
        return this;
    }

    public ThreeDimensionalMenu setAngleStep(int angle_step) {
        this.angle_step = angle_step;
        return this;
    }

    public ThreeDimensionalMenu setTitle(String title) {
        this.title = Color.parseHexString(title);
        return this;
    }


    public ThreeDimensionalMenu setOpenItem(Material openItem) {
        this.openItem = openItem;
        return this;
    }


    public ThreeDimensionalMenu setCommand(List<String> commands) {
        this.commands = commands;
        return this;
    }

    public ThreeDimensionalMenu setOpenItemName(String openItemName) {
        this.openItemName = openItemName;
        return this;
    }

    public ThreeDimensionalMenu getPreviousMenu() {
        if (previousMenu == null) return null;
        return getMenus().stream().filter(m -> m.getFileName().equalsIgnoreCase(previousMenu)).findAny().orElseGet(() -> null);
    }

    public void setPreviousMenu(String previousMenu) {
        this.previousMenu = previousMenu;
    }

    public double getRadius() {
        return radius;
    }

    public String getTitle() {
        return title;
    }

    public Material getOpenItem() {
        return openItem;
    }

    public List<String> getCommands() {
        return commands;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }

    public String getOpenItemName() {
        return openItemName;
    }

    public LinkedList<ItemData> getItemDataList() {
        return itemDataList;
    }

    public String getFileName() {
        return fileName;
    }


    public void setItemDataList(LinkedList<ItemData> itemDataList) {
        this.itemDataList = itemDataList;
    }

    public void setBarItemsList(LinkedList<ItemData> barItemsList) {
        this.barItemsList = barItemsList;
    }

    public LinkedList<ItemData> getBarItemsList() {
        return barItemsList;
    }

    public static List<ThreeDimensionalMenu> getMenus() {
        return menus;
    }

    public static ThreeDimensionalMenu getMenu(String menuName) {
        return getMenus().stream().filter(m -> m.getFileName().equalsIgnoreCase(menuName)).findAny().get();
    }

    public int getAngleStep() {
        return angle_step;
    }

    public ThreeDimensionalMenu setDefaultClickParticle(ParticleData defaultClickParticle) {
        this.defaultClickParticle = defaultClickParticle;
        return this;
    }

    public ThreeDimensionalMenu setDefaultClickSound(SoundData defaultClickSound) {
        this.defaultClickSound = defaultClickSound;
        return this;
    }

    public void setDefaultChangeSound(SoundData defaultChangeSound) {
        this.defaultChangeSound = defaultChangeSound;
    }

    public SoundData getDefaultChangeSound() {
        return defaultChangeSound;
    }

    public ParticleData getDefaultClickParticle() {
        return defaultClickParticle;
    }

    public SoundData getDefaultClickSound() {
        return defaultClickSound;
    }
}
