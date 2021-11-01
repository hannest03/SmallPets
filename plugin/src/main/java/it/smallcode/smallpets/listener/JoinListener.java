package it.smallcode.smallpets.listener;
/*

Class created by SmallCode
04.07.2020 10:15

*/

import it.smallcode.smallpets.SmallPets;
import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.abilities.eventsystem.AbilityEventBus;
import it.smallcode.smallpets.core.abilities.eventsystem.events.JoinEvent;
import it.smallcode.smallpets.core.manager.PetManager;
import it.smallcode.smallpets.core.manager.UserManager;
import it.smallcode.smallpets.core.manager.types.User;
import it.smallcode.smallpets.core.version.VersionChecker;
import it.smallcode.smallpets.core.worldguard.SmallFlags;
import it.smallcode.smallpets.core.worldguard.WorldGuardImp;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    private UserManager userManager;
    private PetManager petManager;

    public JoinListener(UserManager userManager, PetManager petManager){

        this.userManager = userManager;
        this.petManager = petManager;

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){

        Bukkit.getScheduler().scheduleAsyncDelayedTask(SmallPetsCommons.getSmallPetsCommons().getJavaPlugin(), new Runnable() {
            @Override
            public void run() {
                if(e.getPlayer().hasPermission("smallpets.updateNotification")) {
                    if (VersionChecker.checkVersion(SmallPetsCommons.getSmallPetsCommons().getJavaPlugin().getDescription().getVersion())) {
                        e.getPlayer().sendMessage(SmallPetsCommons.getSmallPetsCommons().getPrefix() + "A new update is available!");
                        e.getPlayer().sendMessage(SmallPetsCommons.getSmallPetsCommons().getPrefix() + "https://www.spigotmc.org/resources/smallpets.81184/");
                    }
                }
            }
        });

        Bukkit.getScheduler().scheduleAsyncDelayedTask(SmallPets.getInstance(), new Runnable() {
            @Override
            public void run() {

                userManager.loadUser(e.getPlayer().getUniqueId().toString());

                Bukkit.getScheduler().scheduleSyncDelayedTask(SmallPets.getInstance(), new Runnable() {
                    @Override
                    public void run() {

                        User user = userManager.getUser(e.getPlayer().getUniqueId().toString());
                        if(SmallPetsCommons.getSmallPetsCommons().getDisabledWorlds().contains(e.getPlayer().getLocation().getWorld().getName())){
                            user.getSelected().destroy();
                            user.setSelected(null);
                            e.getPlayer().sendMessage(SmallPetsCommons.getSmallPetsCommons().getPrefix() + SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getStringFormatted("pets_disabled_world"));
                        }

                        if(!SmallPetsCommons.getSmallPetsCommons().isUseWorldGuard() ||
                                (SmallPetsCommons.getSmallPetsCommons().isUseWorldGuard() && WorldGuardImp.checkStateFlag(e.getPlayer(), SmallFlags.SHOW_PETS))){
                            user.spawnSelected();
                        }

                        if(user.getSelected() != null){

                            AbilityEventBus.post(new JoinEvent(user));

                        }

                        for(Player all : Bukkit.getOnlinePlayers()){

                            if(all != e.getPlayer()) {

                                if (all.getWorld().getName().equals(e.getPlayer().getWorld().getName())) {

                                   user = userManager.getUser(all.getUniqueId().toString());

                                    if (user != null && user.getSelected() != null) {

                                        user.getSelected().spawnToPlayer(e.getPlayer());

                                    }

                                }

                            }

                        }

                    }
                });

            }
        });

    }

}
