package it.smallcode.smallpets.core.manager.types;
/*

Class created by SmallCode
03.07.2020 22:00

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.abilities.eventsystem.AbilityEventBus;
import it.smallcode.smallpets.core.abilities.eventsystem.events.PetDeselectEvent;
import it.smallcode.smallpets.core.abilities.eventsystem.events.PetSelectEvent;
import it.smallcode.smallpets.core.events.DespawnPetEvent;
import it.smallcode.smallpets.core.events.SpawnPetEvent;
import it.smallcode.smallpets.core.factory.PetFactory;
import it.smallcode.smallpets.core.languages.LanguageManager;
import it.smallcode.smallpets.core.manager.PetMapManager;
import it.smallcode.smallpets.core.pets.Pet;
import org.bukkit.Bukkit;
import org.bukkit.configuration.MemorySection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

/**
 *
 * In the user object all the data of a player is being stored
 *
 */
public class User {

    private JavaPlugin plugin;

    private String uuid;

    private Pet selected;
    private Settings settings = new Settings();

    private List<Pet> pets;

    /**
     *
     * Creates an user object
     *
     */

    public User(JavaPlugin plugin){

        this(null, plugin);

    }

    /**
     *
     * Creates an user object
     *
     * @param uuid - the uuid of the user
     */

    public User(String uuid, JavaPlugin plugin){

        this.plugin = plugin;

        this.uuid = uuid;

        this.selected = null;

        this.pets = new ArrayList<>();

    }

    /**
     *
     * Creates an user object
     *
     * @param uuid - the uuid of the user
     * @param data - a map with the data of the user
     * @param petMapManager - the petMapManager with all the registered pets
     * @oaram plugin - the instance of the plugin
     * @param useProtocolLib - boolean if ProtocolLib is being used
     */

    public User(String uuid, Map<String, Object> data, PetMapManager petMapManager, JavaPlugin plugin, boolean useProtocolLib, LanguageManager languageManager){

        this.plugin = plugin;

        this.uuid = uuid;

    }

    /**
     *
     * Returns the pet of the user.<br>
     * If the pet couldn't be found the method returns null;
     *
     * @param type - the type of the pet
     * @return - returns the pet
     */
    public Pet getPetFromType(String type){

        Optional<Pet> result = pets.stream().filter(p -> p.getID().equalsIgnoreCase(type)).findFirst();

        if(result != null)
            if(result.isPresent())
                return result.get();

        return null;

    }

    /**
     *
     * Returns the pet of the user.<br>
     * If the pet couldn't be found the method returns null;
     *
     * @param uuid - the uuid of the pet
     * @return - returns the pet
     */
    public Pet getPetFromUUID(UUID uuid){

        Optional<Pet> result = pets.stream().filter(p -> p.getUuid().equals(uuid)).findFirst();

        if(result != null)
            if(result.isPresent())
                return result.get();

        return null;

    }

    /**
     *
     * Spawns the selected pet if one was selected.
     *
     */

    public void spawnSelected(){

        if(selected != null){

            selected.setOwner(Bukkit.getPlayer(UUID.fromString(uuid)));

            Player p = Bukkit.getPlayer(UUID.fromString(uuid));

            SpawnPetEvent spawnPetEvent = new SpawnPetEvent(selected, p);

            Bukkit.getPluginManager().callEvent(spawnPetEvent);

            if(!spawnPetEvent.isCancelled()) {

                selected.spawn(plugin);

            }

        }

    }

    /**
     *
     * Spawns the selected pet if one was selected.
     *
     */

    public void spawnSelected(Player player){

        if(selected != null){

            selected.setOwner(Bukkit.getPlayer(UUID.fromString(uuid)));

            SpawnPetEvent spawnPetEvent = new SpawnPetEvent(selected, Bukkit.getPlayer(UUID.fromString(uuid)));

            Bukkit.getPluginManager().callEvent(spawnPetEvent);

            if(!spawnPetEvent.isCancelled()) {

                selected.spawnToPlayer(player, plugin);

            }

        }

    }

    /**
     *
     * Despawn the selected pet if one was selected.
     *
     */

    public void despawnSelected(Player player){

        if(selected != null){

            selected.setOwner(Bukkit.getPlayer(UUID.fromString(uuid)));

            DespawnPetEvent despawnPetEvent = new DespawnPetEvent(selected, Bukkit.getPlayer(UUID.fromString(uuid)));

            Bukkit.getPluginManager().callEvent(despawnPetEvent);

            if(!despawnPetEvent.isCancelled()) {

                selected.despawnFromPlayer(player, plugin);

            }

        }

    }

    /**
     *
     * Despawns the selected pet if one was selected.
     *
     */

    public void despawnSelected(){

        if(selected != null){

            selected.setOwner(Bukkit.getPlayer(UUID.fromString(uuid)));

            DespawnPetEvent despawnPetEvent = new DespawnPetEvent(selected, Bukkit.getPlayer(UUID.fromString(uuid)));

            Bukkit.getPluginManager().callEvent(despawnPetEvent);

            if(!despawnPetEvent.isCancelled()) {

                selected.destroy();

            }

        }

    }

    /**
     *
     * Returns the uuid of the User
     *
     * @return the uuid
     */

    public String getUuid() {
        return uuid;
    }

    /**
     *
     * Sets the uuid of the user
     *
     * @param uuid - the new uuid
     */

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     *
     * Returns a list with all the pets of the user
     *
     * @return the list with the pets
     */

    public List<Pet> getPets() {
        return pets;
    }

    /**
     *
     * Sets the list of pets of the user
     *
     * @param pets - the new pets list
     */

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    /**
     *
     * Returns the selected pet if one was selected,<br>
     * else the method will return <b>null</b>;
     *
     * @return the selected pet
     */

    public Pet getSelected() {
        return selected;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    /**
     *
     * Sets the selected pet, despawns the old pet and spawns the new selected pet.
     *
     * @param selected - the new selected pet
     */

    public boolean setSelected(Pet selected) {

        Player p = Bukkit.getPlayer(UUID.fromString(getUuid()));

        if(p == null || !p.isOnline())
            return false;

        if(SmallPetsCommons.getSmallPetsCommons().isRequirePermission() && selected != null && !p.hasPermission("smallpets.allow." + selected.getID())) {

            p.sendMessage(SmallPetsCommons.getSmallPetsCommons().getPrefix() + SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getStringFormatted("noPerms"));

            return false;

        }

        if(!SmallPetsCommons.getSmallPetsCommons().isRequirePermission() && selected != null && (p.hasPermission("smallpets.forbid." + selected.getID()) && !p.isOp())) {

            p.sendMessage(SmallPetsCommons.getSmallPetsCommons().getPrefix() + SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getStringFormatted("noPerms"));

            return false;

        }

        despawnSelected();

        if(this.selected != null) {

            PetDeselectEvent petDeselectEvent = new PetDeselectEvent(this.selected, Bukkit.getPlayer(UUID.fromString(getUuid())));
            AbilityEventBus.post(petDeselectEvent);

        }

        this.selected = selected;

        if(this.selected != null) {

            PetSelectEvent petSelectEvent = new PetSelectEvent(this.selected, Bukkit.getPlayer(UUID.fromString(getUuid())));
            AbilityEventBus.post(petSelectEvent);

        }

        spawnSelected();

        return true;

    }

    /**
     * Sets the selected pet without any other action
     *
     * @param selected - the new selected pet
     * @return
     */
    public void setSelectedSafe(Pet selected){
        this.selected = selected;
    }

    /**
     *
     * Unserializes the data of a pet
     *
     * @param data - the data
     * @return - the unserialized pet
     */

    private Pet unserializePet(Map<String, Object> data){

        String type = (String) data.get("type");

        UUID petUUID;
        if(data.get("uuid") != null)
            petUUID = UUID.fromString((String) data.get("uuid"));
        else
            petUUID = UUID.randomUUID();

        long exp = Long.valueOf((String) data.get("exp"));

        if(SmallPetsCommons.getSmallPetsCommons().getPetMapManager().getPetMap().containsKey(type)){

            return PetFactory.createPet(type, petUUID, Bukkit.getPlayer(UUID.fromString(uuid)), exp, SmallPetsCommons.getSmallPetsCommons().isUseProtocollib());

        }

        return null;

    }

}
