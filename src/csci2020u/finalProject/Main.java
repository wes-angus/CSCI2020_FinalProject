/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csci2020u.finalProject;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import csci2020u.finalProject.helper.Dictionary;

/**
 *
 * @author 100449718
 */
public class Main
{
    public static void main(String[] args) throws InterruptedException
    {
        MainDisplay display = new MainDisplay();
        display.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dictionary dictionary = new Dictionary("Dictionary.txt");
 
        //Display the window.
        display.pack();
        display.setVisible(true);
        
        String[] line;
        int numEntered = 0;
        List<PhoneConverter> conv_threads = new ArrayList<>();
        String output;
        String text = "";
        
        while(true)
        {
            if(numEntered != display.numEntered)
            {
                output = "";
                numEntered++;
                line = (display.jTextField1.getText()).split(" ");
                output += "Line " + numEntered + " Results:\n";
                conv_threads.clear();
                
                for(String entry : line)
                {
                    if(entry.matches("[0-9]{7}"))
                    {
                        conv_threads.add(new Phone2Word(entry, dictionary));
                        conv_threads.get(conv_threads.size() - 1).start();
                    }
                    else if(entry.matches("[a-zA-Z]{7}"))
                    {
                        conv_threads.add(new Word2Phone(entry));
                        conv_threads.get(conv_threads.size() - 1).start();
                    }
                    else
                    {
                        conv_threads.add(null);
                    }
                }
                
                for (int i = 0; i < conv_threads.size(); i++)
                {
                    if(conv_threads.get(i) == null)
                    {
                        if(line[i].matches("[a-zA-Z]+") || line[i].matches("[0-9]+"))
                        {
                            output += "-----\n" + "Error: " + line[i] + " is not the correct length. Please try again.\n";
                        }
                        else
                        {
                            output += "-----\n" + "Error: " + line[i] + " is not a word or a number. Please try again.\n";
                        }
                    }
                    else
                    {
                        conv_threads.get(i).join();
                        output += "-----\n" + conv_threads.get(i).output;
                    }
                }
                output += "-----\n\n";
                display.jTextArea1.append(output); //Add the text to the textbox
            }
            if(!text.equals(display.jTextArea1.getText()))
            {
                text = display.jTextArea1.getText();
                //display.jTextArea1.repaint();
            }
        }
    }
}
