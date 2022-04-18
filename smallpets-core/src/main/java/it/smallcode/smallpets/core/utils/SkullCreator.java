package it.smallcode.smallpets.core.utils;
/*

Class created by SmallCode
24.10.2020 20:37

*/

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.logger.Logger;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Field;
import java.util.UUID;

public abstract class SkullCreator {

    private final Logger logger = SmallPetsCommons.getSmallPetsCommons().getLogger();

    public abstract ItemStack createHeadItem(String texture);

    protected GameProfile createProfileWithTexture(String texture){

        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), null);

        PropertyMap propertyMap = gameProfile.getProperties();
        propertyMap.put("textures", new Property("textures", texture));

        return gameProfile;

    }

    protected void setHeadTexture(ItemStack itemStack, String texture){

        if(itemStack != null){

            ItemMeta itemMeta = itemStack.getItemMeta();

            try {

                Field field = itemMeta.getClass().getDeclaredField("profile");

                if (!field.isAccessible()) {

                    field.setAccessible(true);

                }

                try {

                    field.set(itemMeta, createProfileWithTexture(texture));

                } finally {

                    if (!field.isAccessible()) {
                        field.setAccessible(false);
                    }

                }

            }catch(Exception ex){

                logger.error("Error while setting head texture!");

            }

            itemStack.setItemMeta(itemMeta);

        }

    }

}
