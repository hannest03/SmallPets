package it.smallcode.smallpets.core.pets.interact;
/*

Class created by SmallCode
14.03.2022 17:12

*/

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.manager.types.User;
import it.smallcode.smallpets.core.pets.Pet;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.function.Consumer;

public class ProtocolLibInteractHandler implements PetInteractHandler.Handler {
    @Override
    public void setup(Consumer<PetInteractHandler.InteractEvent> consumer) {
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(SmallPetsCommons.getSmallPetsCommons().getJavaPlugin(), PacketType.Play.Client.USE_ENTITY) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                int entityId = event.getPacket().getIntegers().readSafely(0);
                Player p = event.getPlayer();
                User user = SmallPetsCommons.getSmallPetsCommons().getUserManager().getUser(p.getUniqueId().toString());
                if(user != null){
                    Optional<Pet> optPet = user.getPets().stream().filter(pet -> pet.isEntity(entityId)).findFirst();
                    if(optPet.isPresent()){
                        consumer.accept(new PetInteractHandler.InteractEvent(p, optPet.get()));
                    }
                }
            }
        });
    }
}
