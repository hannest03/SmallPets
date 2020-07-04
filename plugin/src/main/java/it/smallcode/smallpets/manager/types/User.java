package it.smallcode.smallpets.manager.types;
/*

Class created by SmallCode
03.07.2020 22:00

*/

import it.smallcode.smallpets.SmallPets;
import it.smallcode.smallpets.events.DespawnPetEvent;
import it.smallcode.smallpets.events.SpawnPetEvent;
import it.smallcode.smallpets.manager.PetMapManager;
import it.smallcode.smallpets.pets.Pet;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class User {

    private String uuid;

    private Pet selected;

    private List<Pet> pets;

    public User(){

        this.uuid = null;

        this.selected = null;

        this.pets = new ArrayList<>();

    }

    public User(String uuid){

        this.uuid = uuid;

        this.selected = null;

        this.pets = new ArrayList<>();

    }

    public User(String uuid, Map<String, Object> data, PetMapManager petMapManager){

        this.uuid = uuid;

        this.pets = new ArrayList<>();

        List<Map<String, Object>> petDatas = (List<Map<String, Object>>) data.get("pets");

        for(Map<String, Object> petData : petDatas){

            pets.add(unserializePet(petData, petMapManager, uuid));

        }

        this.selected = getPetFromType((String) data.get("selected"));

    }

    public Pet getPetFromType(String type){

        Optional<Pet> result = pets.stream().filter(p -> p.getName().equals(type)).findFirst();

        if(result.isPresent())
            return result.get();
        else
            return null;

    }

    public void spawnSelected(){

        if(selected != null){

            selected.setOwner(Bukkit.getPlayer(UUID.fromString(uuid)));

            SpawnPetEvent spawnPetEvent = new SpawnPetEvent(selected, Bukkit.getPlayer(UUID.fromString(uuid)));

            Bukkit.getPluginManager().callEvent(spawnPetEvent);

            if(!spawnPetEvent.isCancelled()) {

                selected.spawn(SmallPets.getInstance());

            }

        }

    }

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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    public Pet getSelected() {
        return selected;
    }

    public void setSelected(Pet selected) {

        despawnSelected();

        this.selected = selected;

        spawnSelected();

    }

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

    private Map<String, Object> serializePet(Pet pet){

        Map<String, Object> data = new HashMap<>();

        data.put("type", pet.getName());
        data.put("exp", pet.getXp());

        return data;

    }

    private Pet unserializePet(Map<String, Object> data, PetMapManager petMapManager, String uuid){

        String type = (String) data.get("type");

        int exp = (int) data.get("exp");

        if(petMapManager.getPetMap().containsKey(type)){

            try {

                Constructor constructor = petMapManager.getPetMap().get(type).getConstructor(Player.class, Integer.class);

                return (Pet) constructor.newInstance(Bukkit.getPlayer(UUID.fromString(uuid)), exp);

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
