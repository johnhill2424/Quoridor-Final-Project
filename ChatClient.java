import java.io.*;
import java.net.*;

/*
   1 object for message info. (doesn't include the messages, the messages are sent in either client/server);
   1 server
   Many clients   
   
   
*/


/* Feel free to uncomment (lines 4-6) for displaying the GUI, unless we're using a separate java file for GUI */
   import javax.swing.*;
   //import java.awt.*;
   //import java.awt.event.*;
   //import java.util.*;        //if needed, uncomment this line as well.

/**
 * Section - 01
 * Course # - ISTE 121
 * Instructor - Michael Floeser 
 *
 * @authors: Catherine Poggioli, John Hill, Jack Old, David Luong
 *
 * @Description: Final Project ChatClient
 *
 *
 * Use built-in toString() method to display the client/server message onto the GUI TextArea.
 *
 *
 *
 */
public class ChatClient implements Serializable{
   /**
      Global, private attributes & constants
   */
      //IO objects
      private InputStream ins;
      private InputStreamReader isr;      
      private BufferedReader br;      //To read in the server's response to the client's message
      
      private OutputStream out;
      private OutputStreamWriter osw;
      private PrintWriter pw;         //To send client's message to the server
      
      //Socket
      private Socket sock;            //To establish connection to the server
       
       
      //The host name and port number all players/clients will connect to
      private final String HOST = "localhost";
      private final int PORT    = 16789;
      
      
      //String representation of client's username/player's name
      private String user;
      
      //GUI Components
      	//The JTextAreas for holding each of the connected player's info
      	private JTextArea jtaP1 = new JTextArea(5,20);
      	private JTextArea jtaP2 = new JTextArea(5,20);
      	private JTextArea jtaP3 = new JTextArea(5,20);
      	private JTextArea jtaP4 = new JTextArea(5,20);
      	
      	//The JTextArea that holds the output of the chat program
      	private JTextArea jtaChatLog = new JTextArea(5,50);
      	
      	/** 
      	Potentially add server commands that can be typed into the chat
      	
      	*/                  



   /**
      Main method 
   
   public static void main(String [] args){
      new ChatClient();
      
   } //end main
   */


   /**
      ChatClient Default Constructor
   */
   public ChatClient(){        
         
      /*
      //Instantiate ThreadedClient objects
      ThreadedClient tc1 = new ThreadedClient(); 
      ThreadedClient tc2 = new ThreadedClient();
      ThreadedClient tc3 = new ThreadedClient();
      ThreadedClient tc4 = new ThreadedClient();
      */
      
    /*
      //Start the clients
      tc1.start();
      tc2.start();
      tc3.start();
      tc4.start();            

      //If (lines 60-68) are needed, keep it, otherwise, remove it.
      try{
         tc1.join();
         tc2.join();
         tc3.join();
         tc4.join();
      }
      catch(InterruptedException ie){
         System.err.println("The current client is still connecting.");
      }
         
    */
   
   } //end of ChatClient Default Constructor


   /**
      Separate methods for simplifying client actions
   */
      /**
         Connect() method
      */
      public boolean connect(){
         try{
            //Initialize the socket
            sock = new Socket(HOST, PORT);
            
            //Initialize IO objects for each client/player to read in server's response
            ins = sock.getInputStream();
            isr = new InputStreamReader( ins );
            br  = new BufferedReader( isr);
            
            //Initialize IO objects for each client/player to send his/her message to server
            out = sock.getOutputStream();
            osw = new OutputStreamWriter ( out );
            pw  = new PrintWriter( osw ); 
            
            //Instantiate ThreadedClient objects
            ThreadedClient tc1 = new ThreadedClient(); 
               //ThreadedClient tc2 = new ThreadedClient();
               //ThreadedClient tc3 = new ThreadedClient();
               //ThreadedClient tc4 = new ThreadedClient();         
            
            //Start the clients
            tc1.start();   
               //tc2.start();
               //tc3.start();
               //tc4.start();                                    
         }
         catch(UnknownHostException ukhe){
            System.err.println("This server's host could not be recognized.");
            return false;
         }
         catch(IOException ioe){
            System.err.println("Unable to connect to server.");
            return false;
         }
         
         return true;       //Indicates that the connection is successful.
         
      } //end of connect() method

      /**
         Send() method - will send information about player's move on his/her turn in terms of the 
                           the allocation of the GUI components.
                           
                         The server will then send the status/game progress based on all player-movement
                         information it received back to each of the players/clients.
      */
      public void send(String infoMsg){
         pw.println( infoMsg );
         pw.flush();
         
      } //end of send() method
      
      /**
         Close() method
      */
      public boolean close(){
         try{
            //Display the closing statements on close
            pw.println("Closing IO's...\nSocket is closing...");
            pw.flush();
            
            br.close();     //Close BufferedReader
            pw.close();     //Close PrintWriter
            sock.close();   //Close socket
         }
         catch(IOException ioe){
            ioe.printStackTrace();
         }   
         
         return true;       //Indicates that BufferedReader, PrintWriter, and Socket were successfully closed.
         
      } //end of close() method


   /**
      Inner class for ThreadedClient, which represents each player in the Quorridor Game 
   */
   class ThreadedClient extends Thread{
      @Override
      public void run(){
         while(true){
            try{            
               //Read server's response
               jtaChatLog.append(br.readLine()+"\n");
               System.out.println(br.readLine());
            }
            catch(IOException ioe){
               ioe.printStackTrace();
            }
         } //end of while-loop
      
      } //end of run() method
      
   } //end of inner class, ThreadedClient    

} //end of ChatClient class   