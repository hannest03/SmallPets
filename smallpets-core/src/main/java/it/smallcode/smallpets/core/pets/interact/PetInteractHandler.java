package it.smallcode.smallpets.core.pets.interact;
/*

Class created by SmallCode
20.08.2021 20:54

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.pets.Pet;
import it.smallcode.smallpets.core.signgui.SignGUI;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class PetInteractHandler implements Listener {

    public static void setup(){
        final Consumer<InteractEvent> consumer = interactEvent -> {
            if(interactEvent == null) return;
            interactPet(interactEvent.p, interactEvent.pet);
        };

        if(SmallPetsCommons.getSmallPetsCommons().isUseProtocollib()){
            new ProtocolLibInteractHandler().setup(consumer);
        }else{
            new BukkitInteractHandler().setup(consumer);
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

    @AllArgsConstructor
    @NoArgsConstructor
    public static class InteractEvent{
        public Player p;
        public Pet pet;
    }

    public interface Handler{
        void setup(Consumer<InteractEvent> consumer);
    }

}
