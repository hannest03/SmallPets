package it.smallcode.smallpets.core.manager;
/*

Class created by SmallCode
19.06.2021 23:39

*/

import it.smallcode.smallpets.core.SmallPetsCommons;
import it.smallcode.smallpets.core.manager.types.Sort;
import it.smallcode.smallpets.core.manager.types.User;
import it.smallcode.smallpets.core.pets.Pet;
import it.smallcode.smallpets.core.sort.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SortManager {

    private List<Sort> sortOptions;
    private Map<Sort, Sorter> sorterMap;

    public SortManager(){
        sortOptions = new LinkedList<>();
        sorterMap = new HashMap<>();

        registerSorter(Sort.NONE, new NoneSorter());
        registerSorter(Sort.LEVEL_HIGHEST, new LevelHighestSorter());
        registerSorter(Sort.LEVEL_LOWEST, new LevelLowestSorter());
        registerSorter(Sort.TYPE, new TypeSorter());
        registerSorter(Sort.NAME, new NameSorter());
    }

    private void registerSorter(Sort sort, Sorter sorter){
        sortOptions.add(sort);
        sorterMap.put(sort, sorter);
    }

    public Sort getNextSort(Sort sort){
        int index = -1;
        for(int i = 0; i < sortOptions.size(); i++){
            if(sort == sortOptions.get(i)){
                index = i;
                break;
            }
        }

        if(index == -1)
            return null;

        if(index+1 >= sortOptions.size())
            return sortOptions.get(0);

        return sortOptions.get(index+1);
    }

    public Sort getPreviousSort(Sort sort){
        int index = -1;
        for(int i = 0; i < sortOptions.size(); i++){
            if(sort == sortOptions.get(i)){
                index = i;
                break;
            }
        }
        if(index == -1)
            return null;

        if(index-1 < 0)
            return sortOptions.get(sortOptions.size() -1);

        return sortOptions.get(index-1);
    }

    public String sortLore(User user){

        Sort sort = user.getSettings().getSort();

        List<String> lore = new LinkedList<>();

        lore.add(" ");

        for(Sort s : sortOptions){
            String line = "";
            if(s == sort){
                line += "§8➤ ";
            }else{
                line += "   ";
            }
            line += "§7" + SmallPetsCommons.getSmallPetsCommons().getLanguageManager().getLanguage().getStringFormatted(s.getTranslationKey());
            lore.add(line);
        }

        lore.add(" ");

        return lore.stream().collect(Collectors.joining("\n"));

    }

    public List<Pet> getPetsSorted(User user, List<Pet> pets){
        Sorter sorter = getSorter(user.getSettings().getSort());
        return sorter.sort(new LinkedList<>(pets));
    }

    private Sorter getSorter(Sort sort){
        Sorter sorter = sorterMap.get(sort);
        if(sorter == null)
            sorter = sorterMap.get(Sort.NONE);
        return sorter;
    }

}
