package it.smallcode.smallpets.core.utils;
/*

Class created by SmallCode
18.05.2021 10:51

*/

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtils {

    private List<String> filesList = new LinkedList<>();

    public void addFiles(File node){

        //Is file -> add to list
        if(node.isFile())
            filesList.add(node.toString());

        if(node.isDirectory())
            for(String subFile : node.list())
                addFiles(new File(node, subFile));

    }

    public void exportZip(String exportFileName, String[] delete){

        if(!new File(exportFileName).exists()){

            new File(exportFileName).getParentFile().mkdirs();

            try {

                new File(exportFileName).createNewFile();

            } catch (IOException ex) {

                ex.printStackTrace();

            }

        }

        byte[] buffer = new byte[1024];

        FileOutputStream fileOutputStream = null;
        ZipOutputStream zipOutputStream = null;

        try {

            fileOutputStream = new FileOutputStream(exportFileName);
            zipOutputStream = new ZipOutputStream(fileOutputStream);

            FileInputStream in = null;

            for(String file : filesList){

                String fileName = file;

                for(String rep : delete) {

                    if (file.startsWith(rep)) {

                        fileName = file.replace(rep, "");
                        break;

                    }
                }

                ZipEntry zipEntry = new ZipEntry(fileName);
                zipOutputStream.putNextEntry(zipEntry);

                try {

                    in = new FileInputStream(file);
                    int length;

                    while ((length = in.read(buffer)) > 0)
                        zipOutputStream.write(buffer, 0, length);

                }finally {

                    in.close();

                }

            }

            zipOutputStream.closeEntry();

        } catch (IOException ex) {

            ex.printStackTrace();

        }finally {

            try {

                zipOutputStream.close();
                fileOutputStream.close();

            } catch (IOException ex) {

                ex.printStackTrace();

            }

        }

    }

}
