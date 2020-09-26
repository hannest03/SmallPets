package it.smallcode.smallpets.v1_15.abilities.listener;
/*

Class created by SmallCode
26.09.2020 17:55

*/

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class DamageAbilityListenerTest implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e){

        e.getPlayer().sendMessage("Damage Ability Listener!");

    }

}
