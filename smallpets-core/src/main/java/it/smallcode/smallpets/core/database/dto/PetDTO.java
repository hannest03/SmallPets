package it.smallcode.smallpets.core.database.dto;
/*

Class created by SmallCode
07.02.2022 20:54

*/

import lombok.Data;

@Data
public class PetDTO {
    private String pid;
    private String pnamespace;
    private String ptype;
    private String pname;
    private long pexp;
    private String uid;
}
