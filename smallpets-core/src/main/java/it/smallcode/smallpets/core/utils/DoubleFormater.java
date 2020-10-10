package it.smallcode.smallpets.core.utils;
/*

Class created by SmallCode
10.10.2020 18:33

*/

public class DoubleFormater {

    public static double maxDecimalPlaces(double number, int decimalPlaces){

        return  (int) (number * Math.pow(10, decimalPlaces)) / Math.pow(10, decimalPlaces);

    }

}
