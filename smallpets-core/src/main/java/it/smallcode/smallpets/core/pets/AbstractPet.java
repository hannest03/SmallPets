package it.smallcode.smallpets.core.pets;
/*

Class created by SmallCode
22.06.2021 20:01

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.abilities.Ability;
import it.smallcode.smallpets.core.abilities.AbilityType;
import it.smallcode.smallpets.core.pets.entity.EntityHandler;
import it.smallcode.smallpets.core.pets.logic.Logic;
import it.smallcode.smallpets.core.utils.LevelColorUtils;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public abstract class AbstractPet {

    private String id;
    private UUID uuid;

    private PetType petType;
    private long exp;
    private boolean activated;

    private Player owner;
    private Location location;

    private Particle particle = Particle.VILLAGER_HAPPY;
    private String textureValue;
    private List<Ability> abilities = new LinkedList<>();

    private EntityHandler entityHandler;
    private Logic logic;

    public AbstractPet(){
        updateTexture();
    }

    public void spawn(){
        setActivated(true);
        this.location = owner.getLocation().clone();
        location.setX(location.getX() - 1);
        location.setY(location.getY() + 0.75);
        setLocation(location);
        entityHandler.spawn(location, getItem());
        Bukkit.getScheduler().scheduleAsyncDelayedTask(SmallPetsCommons.getSmallPetsCommons().getJavaPlugin(), new Runnable() {
            @Override
            public void run() {
                setCustomName(getCustomName());
            }
        }, 1);
        logic.start(this);
    }

    public void teleport(Location loc){
        setLocation(loc);
        entityHandler.teleport(location);
    }

    public void destroy(){
        setActivated(false);
        logic.stop();
        entityHandler.destroy();
    }

    protected abstract void updateTexture();

    public abstract void giveExp(long exp);

    public boolean hasAbility(String abilityID){
        return abilities.stream().filter(ability -> ability.getID().equals(abilityID)).findFirst().isPresent();
    }

    public ItemStack getItem(){
        updateTexture();

        ItemStack skull = SmallPetsCommons.getSmallPetsCommons().getSkullCreator().createHeadItem(textureValue);

        ItemMeta skullMeta = skull.getItemMeta();

        skullMeta.setDisplayName(getId());

        skull.setItemMeta(skullMeta);

        return skull;
    }

    public ItemStack getDisplayItem() {

        ItemStack itemStack = getItem();

        if(itemStack != null) {

            ItemMeta itemMeta = itemStack.getItemMeta();

            itemMeta.setDisplayName(getCustomNameWithoutPlayerName());

            ArrayList<String> lore = new ArrayList();

            if(getPetType() != null) {

                lore.add("§8" + getPetType().getName(SmallPetsCommons.getSmallPetsCommons().getLanguageManager()));

            }

            lore.add("");

            abilities.stream().forEach(ability -> {

                if(ability.getAbilityType() == AbilityType.STAT){
/*
                    ability.getAbilityTooltip(this).stream().forEach(line ->{

                        lore.add(line);

                    });
*/
                }

            });

            lore.add("");

            abilities.stream().forEach(ability -> {

                if(ability.getAbilityType() == AbilityType.ABILITY){
/*
                    ability.getAbilityTooltip(this).stream().forEach(line ->{

                        lore.add(line);

                    });
*/
                    lore.add("");

                }

            });

            int maxLength = 0;

            for(String loreString : lore){

                if(ChatColor.stripColor(loreString).length() > maxLength){

                    maxLength = ChatColor.stripColor(loreString).length();

                }

            }

            /*

            String progressBar = CenteredText.sendCenteredMessage(generateFinishedProgressbar(), maxLength);

            if(getLevel() != 100) {

                lore.add("  " + CenteredText.sendCenteredMessage(getLevelColor() + getLevel(), ChatColor.stripColor(progressBar).length()));
                lore.add(progressBar);

                String expB = getLevelColor() + (getXp() - getExpForLevel(getLevel())) + "§8/" + getLevelColor() + (getExpForNextLevel() - getExpForLevel(getLevel()));

                lore.add("  " + CenteredText.sendCenteredMessage(expB, ChatColor.stripColor(progressBar).length()));

            }else{

                lore.add("§8" + getLanguageManager().getLanguage().getStringFormatted("maxLevel"));

            }
             */

            itemMeta.setLore(lore);

            itemStack.setItemMeta(itemMeta);

            itemStack = SmallPetsCommons.getSmallPetsCommons().getINBTTagEditor().addNBTTag(itemStack, "pet", getId());

            if(getUuid() != null)
                itemStack = SmallPetsCommons.getSmallPetsCommons().getINBTTagEditor().addNBTTag(itemStack, "pet.uuid", getUuid().toString());

        }

        return itemStack;

    }

    public ItemStack getUnlockItem() {

        ItemStack item = getDisplayItem();

        ItemMeta itemMeta = item.getItemMeta();

        List<String> lore = itemMeta.getLore();

        lore.add("");
        lore.add("§6RIGHT CLICK TO UNLOCK");

        itemMeta.setLore(lore);

        item.setItemMeta(itemMeta);

        item = SmallPetsCommons.getSmallPetsCommons().getINBTTagEditor().addNBTTag(item, "petExp", String.valueOf(getExp()));

        return item;

    }

    public String getName(){
        return SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getStringFormatted("pet." + getId());
    }

    public void setCustomName(String name){
        entityHandler.setCustomName(name);
    }

    public String getCustomName(){

        String name = "";

        if(owner.getName().endsWith("s")){
            name = SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getStringFormatted("petNameFormatTwoS");
        }else {
            name = SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getStringFormatted("petNameFormat");
        }

        name = name.replaceAll("%level%", LevelColorUtils.getLevelColor(getLevel()) + String.valueOf(getLevel()));
        name = name.replaceAll("%player_name%", owner.getName());

        name += getName();

        return name;

    }

    public String getCustomNameWithoutPlayerName(){

        String name = SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getStringFormatted("petNameWithoutOwner");

        name = name.replaceAll("%level%", LevelColorUtils.getLevelColor(getLevel()) + getLevel());

        String petName = getName();

        petName = petName.substring(0, 1).toUpperCase() + petName.substring(1);

        name += "§6" + petName;

        return name;

    }

    public int getLevel(){
        return SmallPetsCommons.getSmallPetsCommons().getLevelingFormula().getLevel(getExp());
    }

    public long getExpForNextLevel(){
        return SmallPetsCommons.getSmallPetsCommons().getLevelingFormula().getExpForLevel(getLevel() +1);
    }

}
