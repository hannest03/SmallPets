package it.smallcode.smallpets.v1_15.abilities.standard;
/*

Class created by SmallCode
02.11.2020 23:14

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.abilities.Ability;
import it.smallcode.smallpets.core.abilities.AbilityType;
import it.smallcode.smallpets.core.abilities.eventsystem.AbilityEventHandler;
import it.smallcode.smallpets.core.abilities.eventsystem.events.ShootBowEvent;
import it.smallcode.smallpets.core.manager.types.User;
import it.smallcode.smallpets.core.pets.Pet;
import it.smallcode.smallpets.core.utils.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class UnbreakableBowAbility extends Ability {

    public UnbreakableBowAbility(){

        super(AbilityType.ABILITY);

    }

    @AbilityEventHandler
    public void onShoot(ShootBowEvent e){

        if(e.getAttacker() instanceof Player){

            Player p = (Player) e.getAttacker();

            User user = SmallPetsCommons.getSmallPetsCommons().getUserManager().getUser(p.getUniqueId().toString());

            if(user != null && user.getSelected() != null && user.getSelected().hasAbility(getID())){

                preventDurabilityChange(e.getBow());

            }

        }

    }

    protected void preventDurabilityChange(ItemStack itemStack){

        if(itemStack.getItemMeta() != null){

            ItemMeta itemMeta = itemStack.getItemMeta();

            if(itemMeta instanceof Damageable) {

                if (((Damageable) itemMeta).hasDamage()) {

                    ((Damageable) itemMeta).setDamage(((Damageable) itemMeta).getDamage() - 1);

                }

                debug("onshoot restored damage");

            }

            itemStack.setItemMeta(itemMeta);

        }

    }

    @Override
    public List<String> getAbilityTooltip(Pet pet) {

        List<String> lore = new ArrayList<>();

        lore.add("§6" + SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage()
                .getStringFormatted("ability." + getID() + ".name"));

        String description = "§7" + SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage()
                .getStringFormatted("ability." + getID() + ".description");

        description = StringUtils.addLineBreaks(description, 30);

        for(String s : description.split("\n"))
            lore.add("§7" + s);

        lore.add("");

        return lore;

    }

}
