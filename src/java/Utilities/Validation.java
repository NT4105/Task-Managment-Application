/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

/**
 *
 * @author ChanRoi
 */
public class Validation {
    public static boolean checkRegex(String item, String regex){
        return item.matches(regex);
    }
}
