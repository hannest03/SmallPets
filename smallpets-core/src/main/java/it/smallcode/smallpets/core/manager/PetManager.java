package it.smallcode.smallpets.core.manager;
/*

Class created by SmallCode
20.07.2021 12:11

*/

import it.smallcode.smallpets.core.factory.PetFactory;
import it.smallcode.smallpets.core.pets.Pet;
import lombok.Data;
import lombok.Getter;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

public class PetManager {

    @Getter
    protected Map<NamespaceKey, Object> petMap = new HashMap<>();

    public void registerPetClasses(){}

    public void loadPets(){
        registerPetClasses();
    }

    public void registerCraftingRecipe(){
        petMap.keySet().iterator().forEachRemaining(namespaceKey -> {

        });
    }

    private void registerRecipe(String namespace, String type){
        Pet pet = PetFactory.createPet(namespace, type, null, null, 0L);
        pet.registerRecipe();
    }

    public void registerPet(String namespace, String id, Class clazz){
        try {
            Constructor constructor = clazz.getConstructor();
            Pet pet = (Pet) constructor.newInstance();
            pet.setId(id);
            pet.setNamespace(namespace);

            NamespaceKey namespaceKey = new NamespaceKey(namespace, id);
            petMap.put(namespaceKey, clazz);
            registerRecipe(namespace, id);
        } catch (NoSuchMethodException ex) {
            ex.printStackTrace();
        } catch (InvocationTargetException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }
    }

    public Object getPet(String namespace, String id){
        if(namespace == null)
            return getPet(id);

        Object pet = petMap.get(new NamespaceKey(namespace, id));
        return pet;
    }

    public Object getPet(String id){
        Optional<NamespaceKey> optNamespaceKey = petMap.keySet()
                .stream()
                .filter(namespaceKey -> namespaceKey.getId().equals(id))
                .findFirst();
        if(!optNamespaceKey.isPresent())
            return null;

        NamespaceKey namespaceKey = optNamespaceKey.get();
        return petMap.get(namespaceKey);
    }

    public List<String> getPetKeys(){
        List<String> keys = petMap.keySet()
                                .stream()
                                .map(e -> e.getNamespace() + ":" + e.getId())
                                .collect(Collectors.toList());
        return keys;
    }

    @Data
    public static class NamespaceKey{
        private String namespace;
        private String id;
        public NamespaceKey(String namespace, String id){
            this.namespace = namespace;
            this.id = id;
        }
    }

}
