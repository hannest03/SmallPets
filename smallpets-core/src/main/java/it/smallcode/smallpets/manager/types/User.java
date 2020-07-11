package it.smallcode.smallpets.manager.types;
/*

Class created by SmallCode
03.07.2020 22:00

*/

import it.smallcode.smallpets.events.DespawnPetEvent;
import it.smallcode.smallpets.events.SpawnPetEvent;
import it.smallcode.smallpets.manager.PetMapManager;
import it.smallcode.smallpets.pets.Pet;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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

    public User(String uuid, Map<String, Object> data, PetMapManager petMapManager, JavaPlugin plugin, boolean useProtocolLib){

        this.plugin = plugin;

        this.uuid = uuid;

        this.pets = new ArrayList<>();

        List<Map<String, Object>> petDatas = (List<Map<String, Object>>) data.get("pets");

        for(Map<String, Object> petData : petDatas){

            pets.add(unserializePet(petData, petMapManager, uuid, useProtocolLib));

        }

        this.selected = getPetFromType((String) data.get("selected"));

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

        Optional<Pet> result = pets.stream().filter(p -> p.getName().equals(type)).findFirst();

        if(result.isPresent())
            return result.get();
        else
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

            SpawnPetEvent spawnPetEvent = new SpawnPetEvent(selected, Bukkit.getPlayer(UUID.fromString(uuid)));

            Bukkit.getPluginManager().callEvent(spawnPetEvent);

            if(!spawnPetEvent.isCancelled()) {

                selected.spawn(plugin);

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

    /**
     *
     * Sets the selected pet, despawns the old pet and spawns the new selected pet.
     *
     * @param selected - the new selected pet
     */

    public void setSelected(Pet selected) {

        despawnSelected();

        this.selected = selected;

        spawnSelected();

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

            data.put("selected", selected.getName());

        }else{

            data.put("selected", "null");

        }

        List<Map<String, Object>> petList = new LinkedList<>();

        for(Pet pet : pets){

            petList.add(serializePet(pet));

        }

        data.put("pets", petList);

        return data;

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

        data.put("type", pet.getName());
        data.put("exp", String.valueOf(pet.getXp()));

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

    private Pet unserializePet(Map<String, Object> data, PetMapManager petMapManager, String uuid, boolean useProtocolLib){

        String type = (String) data.get("type");

        long exp = Long.valueOf((String) data.get("exp"));

        if(petMapManager.getPetMap().containsKey(type)){

            try {

                Constructor constructor = petMapManager.getPetMap().get(type).getConstructor(Player.class, Long.class, Boolean.class);

                return (Pet) constructor.newInstance(Bukkit.getPlayer(UUID.fromString(uuid)), exp, useProtocolLib);

            } catch (NoSuchMethodException ex) {

                ex.printStackTrace();

            } catch (IllegalAccessException ex) {

                ex.printStackTrace();

            } catch (InstantiationException ex) {

                ex.printStackTrace();

            } catch (InvocationTargetException ex) {

                ex.printStackTrace();

            }

        }

        return null;

    }

}
