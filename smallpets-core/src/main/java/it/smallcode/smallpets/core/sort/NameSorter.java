package it.smallcode.smallpets.core.sort;
/*

Class created by SmallCode
19.06.2021 23:46

*/

import it.smallcode.smallpets.core.pets.Pet;

import java.util.Comparator;
import java.util.List;

public class NameSorter implements Sorter{
    @Override
    public List<Pet> sort(List<Pet> pets) {
        pets.sort(new Comparator<Pet>() {
            @Override
            public int compare(Pet o1, Pet o2) {
                return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
            }
        });
        return pets;
    }
}
