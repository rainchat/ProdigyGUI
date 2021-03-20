package fr.cocoraid.prodigygui.resourse.threedimensionalgui.itemdata;

import fr.cocoraid.prodigygui.utils.SkullCreator;
import fr.cocoraid.prodigygui.utils.UtilItem;
import fr.cocoraid.prodigygui.utils.VersionChecker;
import fr.cocoraid.prodigygui.utils.general.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemData {

    /**
     * Required
     */

    private String displayname;


    private ItemStack displayItem;


    /**
     * Optionals
     */
    private List<String> command;
    private int price;
    private String permission;
    private String nopermissionmessage;
    private SoundData soundData;
    private ParticleData particleData;
    private String lore;
    private int rotation;


    public ItemData(String displayname, Material ID) {
        this.displayname = Color.parseHexString(displayname);
        this.displayItem = new ItemStack(ID);
    }

    public ItemData(String displayname, String skulltexture) {
        this.displayname = Color.parseHexString(displayname);
        if (VersionChecker.isHigherOrEqualThan(VersionChecker.v1_16_R1)) {
            this.displayItem = UtilItem.skullTextured(skulltexture);
        } else
            this.displayItem = SkullCreator.itemFromBase64(skulltexture);
    }

    public void setCommand(String command) {
        if (command == null)
            this.command = null;
        else
            this.command = new ArrayList<>(Arrays.asList(command.split("; ")));
    }

    public void setCustomModelDate(Integer date) {
        ItemMeta itemMeta = displayItem.getItemMeta();
        itemMeta.setCustomModelData(date);
        displayItem.setItemMeta(itemMeta);
    }


    public void setPrice(int price) {
        this.price = price;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public void setNopermissionmessage(String nopermissionmessage) {
        this.nopermissionmessage = Color.parseHexString(nopermissionmessage);
    }

    public void setSoundData(SoundData soundData) {
        this.soundData = soundData;
    }

    public void setLore(String lore) {
        this.lore = Color.parseHexString(lore);
    }


    public String getDisplayname() {
        return displayname;
    }

    public List<String> getCommands() {
        return command;
    }

    public int getPrice() {
        return price;
    }


    public String getNopermissionmessage() {
        return nopermissionmessage;
    }

    public String getPermission() {
        return permission;
    }


    public String getLore() {
        return lore;
    }

    public void setParticleData(ParticleData particleData) {
        this.particleData = particleData;
    }

    public ParticleData getParticleData() {
        return particleData;
    }

    public ItemStack getDisplayItem() {
        return displayItem;
    }

    public SoundData getSoundData() {
        return soundData;
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }
}
