package it.smallcode.smallpets.core.sort;
/*

Class created by SmallCode
19.06.2021 23:52

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.pets.Pet;

import java.util.Comparator;
import java.util.List;

public class TypeSorter implements Sorter{

    @Override
    public List<Pet> sort(List<Pet> pets) {
        pets.sort(new Comparator<Pet>() {
            @Override
            public int compare(Pet o1, Pet o2) {
                String typeName1 = o1.getPetType().getName(SmallPetsCommons.getSmallPetsCommons().getLanguageManager());
                String typeName2 = o2.getPetType().getName(SmallPetsCommons.getSmallPetsCommons().getLanguageManager());

                int ret = typeName1.compareTo(typeName2);

                if(ret != 0)
                    return ret;

                return o1.getID().compareTo(o2.getID());
            }
        });
        return pets;
    }
}
