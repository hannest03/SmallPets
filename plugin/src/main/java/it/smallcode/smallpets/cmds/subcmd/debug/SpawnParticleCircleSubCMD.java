package it.smallcode.smallpets.cmds.subcmd.debug;
/*

Class created by SmallCode
07.08.2021 20:07

*/

import it.smallcode.smallpets.cmds.SubCommand;
import it.smallcode.smallpets.cmds.SubCommandType;
import it.smallcode.smallpets.core.utils.CircleUtils;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class SpawnParticleCircleSubCMD extends SubCommand {

    public SpawnParticleCircleSubCMD(String name, String permission) {
        super(name, permission, SubCommandType.DEBUG);
    }

    @Override
    protected void handleCommand(CommandSender s, String[] args) {
        if(s instanceof Player){
            Location location = ((Player) s).getLocation();
            location.setY(location.getY() + 0.25);
            CircleUtils.drawCircle(location, 2, 0.5, Particle.FLAME);
        }
    }
}
