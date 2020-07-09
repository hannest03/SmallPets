package it.smallcode.smallpets.v1_15.listener;
/*

Class created by SmallCode
03.07.2020 18:58

*/

import it.smallcode.smallpets.manager.UserManager;
import it.smallcode.smallpets.manager.types.User;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

    private UserManager userManager;

    private String prefix;

    public InventoryClickListener(UserManager userManager, String prefix){

        this.userManager = userManager;

        this.prefix = prefix;

    }

    @EventHandler
    public void onClick(InventoryClickEvent e){

        if(e.getView().getTitle().equals("§eSmallPets")){

            e.setCancelled(true);

            if(e.getCurrentItem() != null && e.getCurrentItem().getItemMeta() != null && e.getCurrentItem().getItemMeta().getDisplayName() != null) {

                if (e.getCurrentItem().getType() != Material.BLACK_STAINED_GLASS_PANE) {

                    Player p = (Player) e.getWhoClicked();

                    String[] nameSplit = e.getCurrentItem().getItemMeta().getDisplayName().split(" ");

                    String type = nameSplit[nameSplit.length - 1];

                    User user = userManager.getUser(p.getUniqueId().toString());

                    if(user != null) {

                        if (user.getSelected() != null && user.getSelected().getName().equals(type)) {

                            user.setSelected(null);

                            e.getWhoClicked().sendMessage(prefix + "Your pet was despawned");

                        } else {

                            user.setSelected(user.getPetFromType(type));

                            e.getWhoClicked().sendMessage(prefix + "Your pet was summoned");

                        }

                    }

                    p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_BURP, 1,1 );

                    p.closeInventory();

                }

            }

        }

    }

}
