package it.smallcode.smallpets.core.sort;
/*

Class created by SmallCode
19.06.2021 23:51

*/

import it.smallcode.smallpets.core.pets.Pet;

import java.util.Comparator;
import java.util.List;

public class LevelLowestSorter implements Sorter {

    @Override
    public List<Pet> sort(List<Pet> pets) {
        pets.sort(new Comparator<Pet>() {
            @Override
            public int compare(Pet o1, Pet o2) {
                if(o1.getLevel() > o2.getLevel()){
                    return 1;
                }
                if(o1.getLevel() < o2.getLevel()){
                    return -1;
                }
                return o1.getName().compareTo(o2.getName());
            }
        });
        return pets;
    }
}
