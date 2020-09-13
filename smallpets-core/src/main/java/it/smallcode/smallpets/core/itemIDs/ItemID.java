package it.smallcode.smallpets.core.itemIDs;
/*

Class created by SmallCode
30.08.2020 17:47

*/

public class ItemID {

    private int id;
    private int subId;

    public ItemID(int id, int subId) {
        this.id = id;
        this.subId = subId;
    }

    public int getId() {
        return id;
    }

    public int getSubId() {
        return subId;
    }
}
