/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csci2020u.finalProject;

import csci2020u.finalProject.helper.Dictionary;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 100449718
 * Server class handles the conversion of multiple words to phone numbers and
 * phone numbers to words while maintaining connections to multiple clients.
 */
public class Server
{
    ServerSocket main;
    
    //Handles the word-phone number conversion
    static class Reply extends Thread
    {
        OutputStream out;//Output to client
        InputStream in;//Input from client
        BufferedReader clientReader;
        PrintWriter writer;//Writes to client
        String[] clientMessage;
        //String output;
        List<PhoneConverter> conv_threads;//Keeps track of all threads,
                                          //ensuring they execute in the order that
                                          //the words/phone numbers are in in the original input
        List<String> globalDict;//Dictionary to use for the phone number to word(s) conversion

        public Reply(InputStream in, OutputStream out, List<String> dictionary)
        {
            //Initialize variables
            this.in = in;
            this.out = out;
            clientReader = new BufferedReader(new InputStreamReader(in));
            writer = new PrintWriter(out, true);
            conv_threads = new ArrayList<>();
            globalDict = dictionary;
        }
        
        @Override
        public void run()
        {
            do
            {
                try
                {
                    synchronized(in)
                    {
                        clientMessage = (clientReader.readLine()).split(" ");
                    }
                    if(clientMessage.length > 0)
                    {
                        conv_threads.clear();

                        for (String entry : clientMessage)
                        {
                            if (entry.matches("[0-9]{7}"))
                            {
                                conv_threads.add(new Phone2Word(entry, globalDict));
                                conv_threads.get(conv_threads.size() - 1).start();
                            }
                            else if (entry.matches("[a-zA-Z]{7}"))
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
                                if(clientMessage[i].matches("[a-zA-Z]+") || clientMessage[i].matches("[0-9]+"))
                                {
                                    writer.println("-----\n" + "Error: " + clientMessage[i] + " is not the correct length. Please try again.");
                                }
                                else
                                {
                                    writer.println("-----\n" + "Error: " + clientMessage[i] + " is not a word or a number. Please try again.");
                                }
                            }
                            else
                            {
                                conv_threads.get(i).join();
                                writer.print("-----\n" + conv_threads.get(i).output);
                            }
                        }
                        writer.println("-----\n");
                    }
                    else
                    {
                        writer.println("Error: No command specified. Please enter a valid command.");
                    }
                }
                catch(IOException | NumberFormatException | InterruptedException e)
                {
                    break;
                }
            }
            while(clientMessage != null);
        }
    }

    public Server(int port) throws Exception
    {
        this.main = new ServerSocket(port);
    }

    public void serve(String filename) throws IOException
    {
    	int index = 1;
        Dictionary dictionary = new Dictionary(filename);
        while(true)
        {
            Socket socket = this.main.accept();
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();

            // handle the connection
            System.out.println("Handling connection to client " + index);
            (new Reply(in, out, dictionary)).start();
            index++;
        }
    }

    public static void main(String[] args) throws Exception
    {
    	if(args.length > 1)
    	{
            Server s = new Server(Integer.parseInt(args[0]));
            System.out.println("Serving....");
            s.serve(args[1]);
    	}
    	else
    	{
            System.out.println("Not enough command-line arguments.");
    	}
    }
}