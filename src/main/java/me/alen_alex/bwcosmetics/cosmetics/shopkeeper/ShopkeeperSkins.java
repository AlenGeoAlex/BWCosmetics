package me.alen_alex.bwcosmetics.cosmetics.shopkeeper;


import me.alen_alex.bwcosmetics.BWCosmetics;
import me.alen_alex.bwcosmetics.utility.SkinType;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.SkinTrait;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ShopkeeperSkins {

    private String name;
    private SkinType skinType;
    private String skinTexture;
    private String skinSignature;
    private String entityName;
    private Material menuItem;
    private EntityType entityType;

    public ShopkeeperSkins(@NotNull String name,@NotNull SkinType skinType,@NotNull String skinTexture,@NotNull String skinSignature,@NotNull String menuItem) {
        this.name = name;
        this.skinType = skinType;
        this.skinTexture = skinTexture;
        this.skinSignature = skinSignature;
        if(Material.matchMaterial(menuItem) != null)
            this.menuItem = Material.getMaterial(menuItem);
        else {
            this.menuItem = Material.WOOL;
            BWCosmetics.getPlugin().getLogger().warning("Unable to match material "+menuItem+" for Skin "+name);
        }
    }

    public ShopkeeperSkins(@NotNull String name,@NotNull SkinType skinType,@NotNull String entityType,@NotNull String menuItem) {
        this.name = name;
        this.skinType = skinType;
        if(Material.matchMaterial(menuItem) != null)
            this.menuItem = Material.getMaterial(menuItem);
        else {
            this.menuItem = Material.WOOL;
            BWCosmetics.getPlugin().getLogger().warning("Unable to match material "+menuItem+" for Skin "+name);
        }
        this.entityName = entityType;
        if(EntityType.valueOf(entityName) == null){
            this.skinType = SkinType.DEFAULT;
            this.entityType = EntityType.VILLAGER;
            BWCosmetics.getPlugin().getLogger().warning("Unknown entity type for Shopkeeper skin "+name+". Forcing to default");
        }else
            this.entityType = EntityType.valueOf(entityName);
    }

    public String getName() {
        return name;
    }

    public SkinType getSkinType() {
        return skinType;
    }

    public String getSkinTexture() {
        return skinTexture;
    }

    public String getSkinSignature() {
        return skinSignature;
    }

    public Material getMenuItem() {
        return menuItem;
    }

    public String getEntityName() {
        return entityName;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public boolean isPlayerSkin(){
        return this.skinType == SkinType.PLAYERSKIN;
    }

    public boolean hasPermission(Player player){
        return player.hasPermission("bwc.shopkeeper."+name);
    }

}
