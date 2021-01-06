package it.smallcode.smallpets.v1_15;
/*

Class created by SmallCode
02.01.2021 22:27

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.utils.IMetaDataUtils;
import org.bukkit.block.Block;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import java.util.List;

public class MetaDataUtils1_15 implements IMetaDataUtils {


    @Override
    public void setMetaData(Block block, String name, String value) {

        if(block != null)
            block.setMetadata(name, new FixedMetadataValue(SmallPetsCommons.getSmallPetsCommons().getJavaPlugin(), value));

    }

    @Override
    public void removeMetaData(Block block, String name) {

        if(block != null){

            block.removeMetadata(name, SmallPetsCommons.getSmallPetsCommons().getJavaPlugin());

        }

    }

    @Override
    public String getMetaData(Block block, String name) {

        if(block != null) {

            List<MetadataValue> metadataValues = block.getMetadata(name);

            if(!metadataValues.isEmpty())
                if(metadataValues.get(0) != null)
                    return metadataValues.get(0).asString();

        }

        return null;

    }
}
