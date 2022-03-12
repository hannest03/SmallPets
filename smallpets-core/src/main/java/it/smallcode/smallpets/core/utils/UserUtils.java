package it.smallcode.smallpets.core.utils;
/*

Class created by SmallCode
09.02.2022 22:08

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.database.dao.PetDAO;
import it.smallcode.smallpets.core.database.dto.PetDTO;
import it.smallcode.smallpets.core.database.dto.SettingsDTO;
import it.smallcode.smallpets.core.database.dto.UserDTO;
import it.smallcode.smallpets.core.factory.PetFactory;
import it.smallcode.smallpets.core.manager.types.Settings;
import it.smallcode.smallpets.core.manager.types.Sort;
import it.smallcode.smallpets.core.manager.types.User;
import it.smallcode.smallpets.core.pets.Pet;
import org.bukkit.Bukkit;

import java.util.*;

public class UserUtils {

    public static User dtoToUser(UserDTO userDTO, SettingsDTO[] settingsDTO, PetDTO[] petDTOs){
        User user = new User(userDTO.getUid(), SmallPetsCommons.getSmallPetsCommons().getJavaPlugin());
        Settings settings = dtoToSettings(settingsDTO);

        user.setSettings(settings);

        user.setPets(dtoToPets(petDTOs));

        if(userDTO.getPselected() != null) user.setSelectedSafe(user.getPetFromUUID(UUID.fromString(userDTO.getPselected())));

        return user;
    }

    public static UserDTO userToDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setUid(user.getUuid());
        if(user.getSelected() != null) userDTO.setPselected(user.getSelected().getUuid().toString());

        return userDTO;
    }

    public static SettingsDTO[] settingsToDTO(String uid, Settings settings){
       return new SettingsDTO[]{
               createSettingDTO(uid, "showPets", settings.isShowPets() ? "true" : "false"),
               createSettingDTO(uid, "sort", settings.getSort().toString())
       };
    }

    private static SettingsDTO createSettingDTO(String uid, String name, String value){
        SettingsDTO settingsDTO = new SettingsDTO();
        settingsDTO.setUid(uid);
        settingsDTO.setSname(name);
        settingsDTO.setSvalue(value);
        return settingsDTO;
    }

    private static Settings dtoToSettings(SettingsDTO[] settingsDTOs){
        Settings settings = new Settings();

        for(SettingsDTO settingsDTO : settingsDTOs){
            switch (settingsDTO.getSname()){
                case "showPet": {
                    settings.setShowPets(Boolean.parseBoolean(settingsDTO.getSvalue()));
                    break;
                }
                case "sort": {
                    settings.setSort(Sort.valueOf(settingsDTO.getSvalue()));
                    break;
                }
            }
        }

        return settings;
    }

    private static List<Pet> dtoToPets(PetDTO[] petDTOs){
        List<Pet> pets = new LinkedList<>();

        for(PetDTO petDTO : petDTOs){
            Pet pet = PetFactory.createPet(
                    petDTO.getPtype(),
                    UUID.fromString(petDTO.getPid()),
                    Bukkit.getPlayer(UUID.fromString(petDTO.getUid())),
                    petDTO.getPexp(),
                    SmallPetsCommons.getSmallPetsCommons().isUseProtocollib()
            );
            pets.add(pet);
        }
        return pets;
    }

    public static PetDTO[] petsToDTO(List<Pet> pets, String uuid){
        List<PetDTO> petDTOs = new LinkedList<>();
        for(Pet pet : pets){
            PetDTO petDTO = new PetDTO();
            petDTO.setPid(pet.getUuid().toString());
            petDTO.setPtype(pet.getID());
            petDTO.setPexp(pet.getXp());
            petDTO.setUid(uuid);

            petDTOs.add(petDTO);
        }
        return petDTOs.toArray(new PetDTO[0]);
    }

    public static PetDTO petToDTO(Pet pet){
        PetDTO[] pets = petsToDTO(new LinkedList<>(Collections.singletonList(pet)), pet.getOwner().getUniqueId().toString());
        return pets[0];
    }

}
