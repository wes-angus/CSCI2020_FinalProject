/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csci2020u.finalProject;

/**
 *
 * @author 100449718
 */
import java.util.*;

//Converts phone number to list of possible words
public class Phone2Word extends PhoneConverter
{
    String phoneNum;
    List<String> dict;
    
    Phone2Word(String phoneNumber, List<String> dictionary)
    {
        super();
        phoneNum = phoneNumber;
        dict = dictionary;
    }
    
    @Override
    public void run()
    {
        Word2PhoneMapper dict_phone  = new Word2PhoneMapper();
        List<String> dict_nums = new ArrayList<>();//Stores list of phone numbers corresponding to all of the dictionary words
        List<String> final_words = new ArrayList<>();//Stores list of words to be output
        boolean matchFound = false;//Stops looking through the dictionary once a group of matching phone numbers is found
        int in_num = Integer.parseInt(phoneNum); //Convert user input to integer

        //Only checks the dictionary for words if the phone number does not contain 0 or 1,
        //since neither number has any corresponding letters
        if(!(phoneNum.contains("0") || phoneNum.contains("1")))
        {
            for(int i = 0; i<dict.size(); i++)
            {
                //Converts the dictionary words to phone numbers
                dict_nums.add(dict_phone.map(dict.get(i)));
                int dict_num = 0;
                if(dict_nums.get(dict_nums.size()-1).matches("[0-9]+"))
                {
                    dict_num = Integer.parseInt(dict_nums.get(i));
                }
                else
                {
                    System.err.println("Error.");
                }

                //Compares user input to the phone numbers for each of the dictionary words
                //If they are equal, add the word to the final output
                if(in_num == dict_num)
                {
                    final_words.add(dict.get(i));
                    if(!matchFound)
                    {
                        matchFound = true;
                    }
                }
                else if(in_num != dict_num && matchFound)//Breaks once a group of matching numbers is found
                {
                    break;
                }
            }
        }

        //Break up inputed phone number into two strings in order to output it
        //using the format: 123-4567
        String phoneNumOutput;
        String output_a = phoneNum.substring(0, 3) + '-';
        String output_b = phoneNum.substring(3);
        phoneNumOutput = output_a + output_b;
        output += "Phone #: " + phoneNumOutput + "\n";//Output user input
        output += "Words: " + final_words + "\n";//Output list of corresponding words
    }
}
