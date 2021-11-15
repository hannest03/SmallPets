package it.smallcode.smallpets.core.pets.interact;
/*

Class created by SmallCode
20.09.2021 14:29

*/

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.manager.types.User;
import it.smallcode.smallpets.core.pets.Pet;
import it.smallcode.smallpets.core.pets.PetInteractHandler;
import org.bukkit.entity.Player;

import java.util.Optional;

public class InteractSetupProtocolLib implements PetInteractHandler.Setup {
    @Override
    public void setup() {
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(SmallPetsCommons.getSmallPetsCommons().getJavaPlugin(), PacketType.Play.Client.USE_ENTITY) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                int entityId = event.getPacket().getIntegers().readSafely(0);
                Player p = event.getPlayer();
                User user = SmallPetsCommons.getSmallPetsCommons().getUserManager().getUser(p.getUniqueId().toString());
                if(user != null){
                    Optional<Pet> optPet = user.getPets().stream().filter(pet -> pet.isEntity(entityId)).findFirst();
                    if(optPet.isPresent()){
                        PetInteractHandler.interactPet(p, optPet.get());
                    }
                }
            }
        });
    }
}
