package it.smallcode.smallpets.v1_12.abilities;
/*

Class created by SmallCode
03.11.2020 18:17

*/

import org.bukkit.inventory.ItemStack;

public class UnbreakableBowAbility extends it.smallcode.smallpets.v1_15.abilities.standard.UnbreakableBowAbility {

    @Override
    protected void preventDurabilityChange(ItemStack itemStack) {

        if(itemStack != null){

            if(itemStack.getDurability() < itemStack.getType().getMaxDurability()){

                itemStack.setDurability((short) (itemStack.getDurability()+1));

                debug("onshoot restored damage");

            }

        }

    }
}
