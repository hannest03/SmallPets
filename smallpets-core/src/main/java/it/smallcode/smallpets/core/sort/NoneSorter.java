package it.smallcode.smallpets.core.sort;
/*

Class created by SmallCode
19.06.2021 23:45

*/

import it.smallcode.smallpets.core.pets.Pet;

import java.util.List;

public class NoneSorter implements Sorter {

    @Override
    public List<Pet> sort(List<Pet> pets) {
        return pets;
    }
}
