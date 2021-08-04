package it.smallcode.smallpets.core.utils;
/*

Class created by SmallCode
02.08.2021 21:50

*/

import it.smallcode.smallpets.core.pets.Texture;

import java.util.Map;
import java.util.TreeMap;

public class TextureUtils {

    public static String getTexture(Texture[] textures){
        Map<Integer, String> texturesMap = new TreeMap<>();
        for(Texture texture : textures){
            if(texture.areConditionsMet()){
                texturesMap.put(texture.getPriority(), texture.getTexture());
            }
        }
        if(texturesMap.isEmpty()){
           return null;
        }
        Map.Entry<Integer, String> entry = texturesMap.entrySet().iterator().next();
        return entry.getValue();
    }

}
