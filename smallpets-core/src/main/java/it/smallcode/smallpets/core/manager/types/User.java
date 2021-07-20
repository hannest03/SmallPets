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

    private String uuid;

    private Pet selected;
    private Settings settings = new Settings();

    private List<Pet> pets;

    /**
     *
     * Creates an user object
     *
     */

    public User(){

        this(null);

    }

    /**
     *
     * Creates an user object
     *
     * @param uuid - the uuid of the user
     */

    public User(String uuid){

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

    public User(String uuid, Map<String, Object> data){

        this.uuid = uuid;

        unserialize(data);

    }

    /**
     *
     * Returns the pet of the user.<br>
     * If the pet couldn't be found the method returns null;
     *
     * @param type - the type of the pet
     * @return - returns the pet
     */
    public Pet getPetFromNamespaceAndType(String namespace, String type){

        Optional<Pet> result = pets.stream().filter(p -> p.getNamespace().equalsIgnoreCase(namespace) && p.getId().equalsIgnoreCase(type)).findFirst();

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

                selected.spawn();

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

                selected.spawnToPlayer(player);

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

                selected.despawnFromPlayer(player);

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

        if(SmallPetsCommons.getSmallPetsCommons().isRequirePermission() && selected != null && !p.hasPermission("smallpets.allow." + selected.getId())) {

            p.sendMessage(SmallPetsCommons.getSmallPetsCommons().getPrefix() + SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getStringFormatted("noPerms"));

            return false;

        }

        if(!SmallPetsCommons.getSmallPetsCommons().isRequirePermission() && selected != null && (p.hasPermission("smallpets.forbid." + selected.getId()) && !p.isOp())) {

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
     *
     * Serializes the data of the user into a map so that it can be stored.
     *
     * @return the serialized data in a map
     */

    public Map<String, Object> serialize(){

        Map<String, Object> data = new HashMap<>();

        if(selected != null) {

            data.put("selected", selected.getUuid().toString());

        }else {

            data.put("selected", "null");

        }

        data.put("settings", settings.serialize());

        List<Map<String, Object>> petList = new LinkedList<>();

        for(Pet pet : pets){

            petList.add(serializePet(pet));

        }

        data.put("pets", petList);

        return data;

    }

    public void unserialize(Map<String, Object> data){

        if(data.get("settings") != null){

            settings.unserialize(memorySectionToMap((MemorySection) data.get("settings")));

        }

        this.pets = new ArrayList<>();

        List<Map<String, Object>> petDatas = (List<Map<String, Object>>) data.get("pets");

        for(Map<String, Object> petData : petDatas){

            Pet pet = unserializePet(petData);

            if(pet != null)
                pets.add(pet);

        }

        if(!(data.get("selected")).equals("null")) {
            try {
                UUID petUUID = UUID.fromString((String) data.get("selected"));
                this.selected = getPetFromUUID(petUUID);
            } catch (IllegalArgumentException ex) {
                Bukkit.getConsoleSender().sendMessage(SmallPetsCommons.getSmallPetsCommons().getPrefix() + "§cInvalid pet uuid found! " + data.get("selected"));
                Bukkit.getConsoleSender().sendMessage(SmallPetsCommons.getSmallPetsCommons().getPrefix() + "§cThis error can be ignored");
            }
        }

    }

    private Map<String, Object> memorySectionToMap(MemorySection memorySection){

        Map<String, Object> map = new HashMap<>();

        for(String key : memorySection.getKeys(true))
            map.put(key, memorySection.get(key));

        return map;

    }

    /**
     *
     * Serializes the data of the pet into a map so that it can be stored.
     *
     * @param pet - the pet
     * @return - the serialized data in a map
     */

    private Map<String, Object> serializePet(Pet pet){

        Map<String, Object> data = new HashMap<>();

        data.put("type", pet.getId());
        data.put("exp", String.valueOf(pet.getExp()));
        data.put("uuid", pet.getUuid().toString());

        return data;

    }

    /**
     *
     * Unserializes the data of a pet
     *
     * @param data - the data
     * @param petMapManager - the petMapManager with all the registered pettypes
     * @param uuid - the uuid of the player
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

        if(SmallPetsCommons.getSmallPetsCommons().getPetManager().getPet(type) != null){

            return PetFactory.createPet(type, petUUID, Bukkit.getPlayer(UUID.fromString(uuid)), exp);

        }

        return null;

    }

}
