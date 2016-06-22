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
//Converts word to phone number
public class Word2Phone extends PhoneConverter
{
    String word;
    
    public Word2Phone(String word)
    {
        super();
        this.word = word;
    }
    
    @Override
    public void run()
    {
        Word2PhoneMapper word_phone = new Word2PhoneMapper();
        output += "Word: " + word + "\n";//Output user input

        //Break up generated phone number into two strings in order to output it
        //using the format: 123-4567
        String mapOutput = word_phone.map(word);
        String output_a = mapOutput.substring(0, 3) + '-';
        String output_b = mapOutput.substring(3);
        mapOutput = output_a + output_b;
        output += "Phone #: " + mapOutput + "\n";//Convert word to phone number and output result
    }
}
