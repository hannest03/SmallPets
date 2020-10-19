package it.smallcode.smallpets.core.abilities.eventsystem.events;
/*

Class created by SmallCode
19.10.2020 20:47

*/

import it.smallcode.smallpets.core.abilities.eventsystem.AbilityEvent;
import it.smallcode.smallpets.core.manager.types.User;
import org.bukkit.entity.Player;

public class EnterWaterEvent extends AbilityEvent {

    private Player player;

    public EnterWaterEvent(User user, Player player) {
        super(user); this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
