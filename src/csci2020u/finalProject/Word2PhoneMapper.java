/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csci2020u.finalProject;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author 100449718
 */

//Class for converting words into phone numbers
public class Word2PhoneMapper
{
    Map<Character, Character> letterToPhoneNumMap;
    
    public Word2PhoneMapper()
    {
        this.letterToPhoneNumMap = new HashMap<>();
        this.letterToPhoneNumMap.put('a', '2');
        this.letterToPhoneNumMap.put('b', '2');
        this.letterToPhoneNumMap.put('c', '2');
        this.letterToPhoneNumMap.put('d', '3');
        this.letterToPhoneNumMap.put('e', '3');
        //this.letterToPhoneNumMap.put('é', '3');
        //this.letterToPhoneNumMap.put('è', '3');
        this.letterToPhoneNumMap.put('f', '3');
        this.letterToPhoneNumMap.put('g', '4');
        this.letterToPhoneNumMap.put('h', '4');
        this.letterToPhoneNumMap.put('i', '4');
        this.letterToPhoneNumMap.put('j', '5');
        this.letterToPhoneNumMap.put('k', '5');
        this.letterToPhoneNumMap.put('l', '5');
        this.letterToPhoneNumMap.put('m', '6');
        this.letterToPhoneNumMap.put('n', '6');
        this.letterToPhoneNumMap.put('o', '6');
        //this.letterToPhoneNumMap.put('ó', '6');
        this.letterToPhoneNumMap.put('p', '7');
        this.letterToPhoneNumMap.put('q', '7');
        this.letterToPhoneNumMap.put('r', '7');
        this.letterToPhoneNumMap.put('s', '7');
        this.letterToPhoneNumMap.put('t', '8');
        this.letterToPhoneNumMap.put('u', '8');
        this.letterToPhoneNumMap.put('v', '8');
        this.letterToPhoneNumMap.put('w', '9');
        this.letterToPhoneNumMap.put('x', '9');
        this.letterToPhoneNumMap.put('y', '9');
        this.letterToPhoneNumMap.put('z', '9');
    }
    
    //Returns a set of numbers that corresponds to the inputed word
    public String map(String word)
    {
        String number = "";//Phone number based off of "word"
        word = word.toLowerCase();
        //loops through the word to get the integer that corresponds to each letter
        for(int i = 0; i < word.length(); i++)
        {
            number += this.letterToPhoneNumMap.get(word.charAt(i));
        }
        return number;
    }
}

