package it.smallcode.smallpets.core.pets;
/*

Class created by SmallCode
22.06.2021 20:01

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.abilities.Ability;
import it.smallcode.smallpets.core.abilities.AbilityType;
import it.smallcode.smallpets.core.abilities.eventsystem.AbilityEventBus;
import it.smallcode.smallpets.core.events.PetLevelUpEvent;
import it.smallcode.smallpets.core.pets.entityHandler.EntityHandler;
import it.smallcode.smallpets.core.pets.logic.Logic;
import it.smallcode.smallpets.core.pets.progressbar.Progressbar;
import it.smallcode.smallpets.core.text.CenteredText;
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

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
public class Pet {

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
    private Progressbar progressbar;

    public Pet(){
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
        }, 5);
        logic.start(this);
    }

    public void spawnToPlayer(Player p){
        entityHandler.spawnToPlayer(getItem(), p);
        Bukkit.getScheduler()
                .scheduleAsyncDelayedTask(
                        SmallPetsCommons.getSmallPetsCommons().getJavaPlugin(),
                        () -> setCustomName(getCustomName()),
                        5);
    }

    public void despawnFromPlayer(Player p){
        entityHandler.despawnFromPlayer(p);
    }

    public void destroy(){
        setActivated(false);
        logic.stop();
        entityHandler.destroy();
    }

    public void teleport(Location loc){
        setLocation(loc);
        entityHandler.teleport(location);
    }

    protected void updateTexture(){
        setTextureValue("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGI3ZjY2M2Q2NWNkZWQ3YmQzNjUxYmRkZDZkYjU0NjM2MGRkNzczYWJiZGFmNDhiODNhZWUwOGUxY2JlMTQifX19");
    }

    public void giveExp(long exp){
        int level = getLevel();
        if(exp >= 0) {
            if (level != 100)
                this.exp += exp;
        }else{
            this.exp += exp;
            if(this.exp < 0)
                this.exp = 0;
        }
        if(level != getLevel()){
            //LEVEL UP
            AbilityEventBus.post(new it.smallcode.smallpets.core.abilities.eventsystem.events.PetLevelUpEvent(SmallPetsCommons.getSmallPetsCommons().getUserManager().getUser(getOwner().getUniqueId().toString()), this, level));
            Bukkit.getPluginManager().callEvent(new PetLevelUpEvent(this));

            if(isActivated()) {
                setCustomName(getCustomName());
                logic.update();
            }

        }
    }

    public void registerRecipe(){

    }

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

            String loreString = SmallPetsCommons.getSmallPetsCommons().getPetLore().stream().collect(Collectors.joining("\n"));

            if(getPetType() != null) {
                String petType = getPetType().getName(SmallPetsCommons.getSmallPetsCommons().getLanguageManager());
                loreString = loreString.replaceAll("%pet_type%", petType);

            }

            String abilityStatLore = "";
            String abilityLore = "";
            for(int i = 0; i < abilities.size(); i++) {
                Ability ability = abilities.get(i);
                if (ability.getAbilityType() == AbilityType.STAT) {
                    abilityStatLore += ability.getAbilityTooltip(this).stream().collect(Collectors.joining("\n"));
                    if(i != abilities.size() -1)
                        abilityStatLore += "\n";
                }
                if (ability.getAbilityType() == AbilityType.ABILITY) {
                    if(i != 0)
                        abilityLore += "\n";
                    abilityLore += ability.getAbilityTooltip(this).stream().collect(Collectors.joining("\n"));
                    if(i != abilities.size() -1)
                        abilityStatLore += "\n";
                }
            }
            loreString = loreString.replaceAll("%abilities%", abilityStatLore + abilityLore);

            int maxLength = 0;

            for(String s : loreString.split("\n")){

                if(ChatColor.stripColor(s).length() > maxLength){

                    maxLength = ChatColor.stripColor(s).length();

                }

            }

            List<String> progressBar = progressbar.generateFullProgressbar(getExp(), getLevel());
            List<String> progressBarList = new LinkedList<>();
            for(String s : progressBar) {
                progressBarList.add(CenteredText.sendCenteredMessage(s, maxLength));
            }

            String progressbarFinished = progressBarList.stream().collect(Collectors.joining("\n"));
            loreString = loreString.replaceAll("%progress_bar%", progressbarFinished);
            loreString = ChatColor.translateAlternateColorCodes('&', loreString);

            List<String> lore = new LinkedList<>(Arrays.asList(loreString.split("\n")));
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

    public Ability getAbility(String id){

        Optional<Ability> optAbility = abilities.stream().filter(ability -> ability.getID().equals(id)).findFirst();

        if(optAbility.isPresent())
            return optAbility.get();

        return null;

    }

}
