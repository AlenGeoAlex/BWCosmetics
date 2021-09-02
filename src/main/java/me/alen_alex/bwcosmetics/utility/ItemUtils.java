package me.alen_alex.bwcosmetics.utility;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.Abhigya.core.util.xseries.XMaterial;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.*;

public class ItemUtils {

    public ItemStack getCustomTextureHead(String texture, boolean base64) {
        ItemStack head = XMaterial.PLAYER_HEAD.parseItem();
        SkullMeta meta = (SkullMeta) Objects.requireNonNull(head).getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), "");
        profile.getProperties().put("textures", new Property("textures", base64 ? texture : new String(Base64.getEncoder().encode(String.format("{\"textures\": {\"SKIN\": {\"url\": \"https://textures.minecraft.net/texture/%s\"}}}", texture).getBytes()))));

        Field profileField;
        try {
            profileField = Objects.requireNonNull(meta).getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(meta, profile);
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }
        head.setItemMeta(meta);
        return head;
    }

    public ItemStack getItem(Material materialType, String displayName, List<String> lore){
        ItemStack stack = new ItemStack(materialType);
        ItemMeta stackMeta = stack.getItemMeta();
        stackMeta.setDisplayName(displayName);;
        stackMeta.setLore(new ArrayList<>(lore));
        stack.setItemMeta(stackMeta);
        return stack;
    }

    public ItemStack getItem(Material materialType, String displayName){
        ItemStack stack = new ItemStack(materialType);
        ItemMeta stackMeta = stack.getItemMeta();
        stackMeta.setDisplayName(displayName);;
        stack.setItemMeta(stackMeta);
        return stack;
    }

    public ItemStack getItem(Material materialType){
        return new ItemStack(materialType);
    }

}
