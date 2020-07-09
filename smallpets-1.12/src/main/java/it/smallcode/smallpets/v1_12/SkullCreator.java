package it.smallcode.smallpets.v1_12;
/*

Class created by SmallCode
09.07.2020 20:24

*/

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.UUID;

public class SkullCreator {

    /**
     *
     * Returns a player head with custom texture
     *
     * @param texture - the new texture
     * @return the player head with custom texture
     */
    public static ItemStack getSkull(String texture){

        ItemStack head = new ItemStack(397, 1, (short) 3);

        SkullMeta skullMeta = (SkullMeta) head.getItemMeta();

        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), null);

        gameProfile.getProperties().put("textures", new Property("textures", texture));

        try {

            Field profileField = skullMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(skullMeta, gameProfile);

        }catch (IllegalArgumentException|NoSuchFieldException|SecurityException | IllegalAccessException ex) {

            ex.printStackTrace();

        }

        head.setItemMeta(skullMeta);

        return head;

    }

}
