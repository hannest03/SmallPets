package it.smallcode.smallpets.core.abilities.eventsystem.events;
/*

Class created by SmallCode
01.11.2020 17:59

*/

import it.smallcode.smallpets.core.abilities.eventsystem.AbilityEvent;
import it.smallcode.smallpets.core.manager.types.User;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;

public class EnterBiomeEvent extends AbilityEvent {

    private Player player;
    private Biome biome;

    public EnterBiomeEvent(User user, Player player, Biome biome) {

        super(user);

        this.player = player;
        this.biome = biome;

    }

    public Player getPlayer() {
        return player;
    }

    public Biome getBiome() {
        return biome;
    }
}
