package it.smallcode.smallpets.core.utils;
/*

Class created by SmallCode
24.10.2020 20:37

*/

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Field;
import java.util.UUID;

public abstract class SkullCreator {

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

                Bukkit.getConsoleSender().sendMessage("Error!");

            }

            itemStack.setItemMeta(itemMeta);

        }

    }

}
