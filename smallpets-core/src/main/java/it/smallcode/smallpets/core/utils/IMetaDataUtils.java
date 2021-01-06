package it.smallcode.smallpets.core.utils;
/*

Class created by SmallCode
02.01.2021 22:24

*/

import org.bukkit.block.Block;

public interface IMetaDataUtils {

    void setMetaData(Block block, String name, String value);

    void removeMetaData(Block block, String name);

    String getMetaData(Block block, String name);

}
