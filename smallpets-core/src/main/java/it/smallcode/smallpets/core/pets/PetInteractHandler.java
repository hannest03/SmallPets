package it.smallcode.smallpets.core.pets;
/*

Class created by SmallCode
20.08.2021 20:54

*/

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.manager.types.User;
import it.smallcode.smallpets.core.signgui.SignGUI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public class PetInteractHandler implements Listener {

    public static void setup(){
        if(SmallPetsCommons.getSmallPetsCommons().isUseProtocollib()){
            ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(SmallPetsCommons.getSmallPetsCommons().getJavaPlugin(), PacketType.Play.Client.USE_ENTITY) {
                @Override
                public void onPacketReceiving(PacketEvent event) {
                    int entityId = event.getPacket().getIntegers().readSafely(0);
                    Player p = event.getPlayer();
                    User user = SmallPetsCommons.getSmallPetsCommons().getUserManager().getUser(p.getUniqueId().toString());
                    if(user != null){
                        Optional<Pet> optPet = user.getPets().stream().filter(pet -> pet.isEntity(entityId)).findFirst();
                        if(optPet.isPresent()){
                            interactPet(p, optPet.get());
                        }
                    }
                }
            });
        }else{
            Bukkit.getPluginManager().registerEvents(new PetInteractHandler(), SmallPetsCommons.getSmallPetsCommons().getJavaPlugin());
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractAtEntityEvent e){
        int entityId = e.getRightClicked().getEntityId();
        Player p = e.getPlayer();
        User user = SmallPetsCommons.getSmallPetsCommons().getUserManager().getUser(p.getUniqueId().toString());
        if(user != null){
            Optional<Pet> optPet = user.getPets().stream().filter(pet -> pet.isEntity(entityId)).findFirst();
            if(optPet.isPresent()){
                interactPet(p, optPet.get());
            }
        }
    }

    private static void interactPet(Player p, Pet pet){
        if(p.getItemInHand().getType() == Material.NAME_TAG){
            Bukkit.getScheduler().scheduleSyncDelayedTask(SmallPetsCommons.getSmallPetsCommons().getJavaPlugin(), () -> {
                SignGUI signGUI = new SignGUI.Builder(SmallPetsCommons.getSmallPetsCommons().getJavaPlugin())
                        .setPlayer(p)
                        .setLines(new String[4])
                        .setOnSignClosed(lines -> {
                            String petName = Arrays.stream(lines).map(String::trim).collect(Collectors.joining());
                            if(petName.isEmpty())
                                return;
                            if(petName.equals(pet.getRawName()))
                                return;
                            pet.setName(petName);
                            String msg = SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getStringFormatted("petRename");
                            msg = msg.replaceAll("%pet_name%", petName);
                            p.sendMessage(SmallPetsCommons.getSmallPetsCommons().getPrefix() + msg);
                            int amount = p.getItemInHand().getAmount() -1;
                            if(amount == 0){
                                p.setItemInHand(null);
                            }else{
                                p.getItemInHand().setAmount(amount);
                            }
                        }).create();
                signGUI.open();
            }, 1L);
        }
    }

}
