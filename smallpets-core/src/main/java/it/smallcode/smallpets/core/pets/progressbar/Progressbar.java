package it.smallcode.smallpets.core.pets.progressbar;
/*

Class created by SmallCode
24.06.2021 11:59

*/

import java.util.List;

public interface Progressbar {

    String generateProgressbar(long exp, int level);
    List<String> generateFullProgressbar(long exp, int level);

}
