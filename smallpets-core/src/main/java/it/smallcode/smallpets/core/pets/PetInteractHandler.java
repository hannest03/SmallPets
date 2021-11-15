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
import it.smallcode.smallpets.core.pets.interact.InteractSetupBukkit;
import it.smallcode.smallpets.core.pets.interact.InteractSetupProtocolLib;
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
            new InteractSetupProtocolLib().setup();
        }else{
            new InteractSetupBukkit().setup();
        }
    }

    public static void interactPet(Player p, Pet pet){
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

    public interface Setup{
        void setup();
    }

}
