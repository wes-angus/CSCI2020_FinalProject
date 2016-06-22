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
import java.io.*;
import java.net.*;
import javax.swing.JFrame;

public class Client
{
    Socket socket;
    String name;
    int numEntered = 0;
    MainDisplay display;//Main JFrame, including a JTextField for sending messages
                        //and a JTextArea for displaying results from the server

    class Sender implements Runnable
    {
        PrintWriter writer;
        OutputStream out;
        //InputStream in;
        //BufferedReader thisReader;
        String sendLine;
        
        public Sender(OutputStream out)
        {
            //Initialize variables
            this.out = out;
            //this.in = in;
            writer = new PrintWriter(out, true);
            //thisReader = new BufferedReader(new InputStreamReader(in));
        }
        
        @Override
        public void run()
        {
            while(true)
            {
                if(display.numEntered != numEntered)
                {
                    numEntered++;
                    sendLine = display.jTextField1.getText();
                    display.jTextArea1.append("SENT: " + sendLine + '\n');
                    synchronized(out)
                    {
                        writer.println(sendLine);
                    }
                }
            }
        }
    }
    
    class Receiver implements Runnable
    {
        InputStream in;
        BufferedReader servReader;
        String line;
        
        public Receiver(InputStream in)
        {
            //Initialize variables
            this.in = in;
            servReader = new BufferedReader(new InputStreamReader(in));
        }
        
        @Override
        public void run()
        {
            try
            {
                //Read in from the server
                while((line = servReader.readLine()) != null)
                {
                    //Add the output from the server to the JTextArea
                    display.jTextArea1.append(line + '\n');
                }
            }
            catch(IOException e)
            {
                System.exit(1);//Exit if a problem occurs with the server
                               //(Ex. if the server stops running)
            }
        }
    }
    
    public Client(String hostname, int port)
    {
        try
        {
            //Initialize sockets and main JFrame
            this.socket = new Socket(hostname, port);
        }
        catch (IOException ex)
        {
            System.err.println("Connection refused.\nIs the server running at hostname: " + hostname + " and port: " + port + "?");
            System.exit(1);
        }
        display = new MainDisplay();
        display.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //Display the window
        display.pack();
    }

    public void connect() throws IOException, InterruptedException
    {
        InputStream in = this.socket.getInputStream();
        OutputStream out = this.socket.getOutputStream();
        
        // Handle the connection
        display.jTextArea1.append("Handling connection to server\n\n");
        display.setVisible(true);
        display.setTitle("Word to Phone Number & Phone Number to Word");
        Runnable sender = new Sender(out);
        Runnable receiver = new Receiver(in);
        Thread senderThread = new Thread(sender);
        Thread receiverThread = new Thread(receiver);

        senderThread.start();
        receiverThread.start();

        senderThread.join();
        receiverThread.join();
    }

    public static void main(String[] args) throws Exception
    {
    	if(args.length > 1)
    	{
            Client c = new Client(args[0], Integer.parseInt(args[1]));
            c.connect();
    	}
    	else
    	{
            System.out.println("Not enough command-line arguments.");
    	}
    }
}