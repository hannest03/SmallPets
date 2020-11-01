package it.smallcode.smallpets.core.abilities.eventsystem.events;
/*

Class created by SmallCode
01.11.2020 18:05

*/

import it.smallcode.smallpets.core.abilities.eventsystem.AbilityEvent;
import it.smallcode.smallpets.core.manager.types.User;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;

public class ExitBiomeEvent extends AbilityEvent {

    private Player player;
    private Biome prevBiome;

    public ExitBiomeEvent(User user, Player player, Biome prevBiome) {

        super(user);

        this.player = player;
        this.prevBiome = prevBiome;

    }

    public Player getPlayer() {
        return player;
    }

    public Biome getPrevBiome() {
        return prevBiome;
    }
}
