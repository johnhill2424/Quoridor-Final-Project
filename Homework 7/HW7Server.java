import java.io.*;
import java.net.*;
import java.util.*;

/**
   This class is one of two parts that define the program for Homework 7.
   This specific part handles the server of the chat program, interacting with
   The HW7Client class.

   @author Jack Old
   @version 1.0
*/
public class HW7Server {
   //The Vector stores the Print Writers for each thread
   private Vector<PrintWriter> clientStreams = new Vector<PrintWriter>();
   
/**
   The main method that runs an instance of HW7Server
   
   @param args A string array that takes in the commands for main.
*/   
   public static void main(String[] args){
   
      new HW7Server();
   
   }//End of main
/**
   Is instantiated in the main method. The constructor creates a ServerSocket Object, and
   waits for a client to connect. When a client does connect, it creates a ThreadServer
   object, and starts it as its own thread.
*/   
   public HW7Server(){
      
      try{
         //make a connection with a client
         
         ServerSocket ss = new ServerSocket(16789);
         
         //get a client connection, and a socket
         
         Socket cs = null;
         
        
         while(true){
            //wait for a client to connect
            System.out.println("Waiting for a client to connect...");
            cs = ss.accept();
            System.out.println("Have a client connection: "+cs);
            //Create and start ThreadServer object
            ThreadServer ts = new ThreadServer(cs);
            ts.start();
         }
       }
       catch(IOException ioe){
         ioe.printStackTrace();
       } 
   
   }//End of Constructor
/**
   Takes in a string and prints it into the Print Writers in the clientStreams Vector.
   
   @param message A string that will act as a message to be sent into Print Writers.
*/   
   public void messageChecker(String message){
      //Loops through the Vector of Print Writers
      for(int i=0; i<clientStreams.size(); i++){
         //Sends message from parameter to all clients
         clientStreams.get(i).println(message);
         
         clientStreams.get(i).flush();
      
      }
      
   }
/**
   Inner class that is used to create thread objects of clients that have connected
   to the server.
   
   @author Jack Old
   @version 1.0
*/  
   class ThreadServer extends Thread{
   
      Socket cs = null;

/**
   The constructor for the ThreadServer inner class.
   
   @param _cs A socket object that is taken in and assigned as an attribute
              for the ThreadServer class.
*/      
      public ThreadServer(Socket _cs){
      
         cs = _cs;
      }

/**
   Creates an I/O stream to take in and send message for client to client.
   Uses the messageChecker method to distribute the messages that it takes in from
   different clients.
   <p>
   When reading in strings, it checks for certain keywords that have different functionality.

*/     
      public void run(){
         
         try{
            //Create Output Streams
            OutputStream out = cs.getOutputStream();
            PrintWriter pout = new PrintWriter(new OutputStreamWriter(out));
            //Add Print Writer to Vector
            clientStreams.add(pout);
            //Print Welcome message to connected Client
            pout.println("Welcome, you joined the Chat!");
            pout.flush();
            
            //Create Input Stream
            InputStream in = cs.getInputStream();
            BufferedReader bin = new BufferedReader(new InputStreamReader(in));
            
            String clientMsg = null;
            //Loop constantly checks for input streams by client
            while(true){
               clientMsg = bin.readLine();
               //Console command added to display the size of PW Vector
               if(clientMsg.equals("/list")){
                  pout.println(clientStreams.size());
                  pout.flush();
               }
               //Automatic shut-down command to remove PW from Vector
               //And to break from the loop
               else if(clientMsg.equals("***EXITING***")){
                  clientStreams.remove(pout);
                  break;
               }
               else{
               //If regular message, send it to message Checker to be distributed
                  messageChecker(clientMsg);
               }
               
            }
            
         }
         catch(UnknownHostException uhe){
            uhe.printStackTrace();
            System.out.println("Unknown Server");
         }
         catch(BindException be){
            be.printStackTrace();
            System.out.println("Server already running");
         }
         catch(IOException io){
            System.out.println("Client has abnormally exited");
            messageChecker("*A CLIENT'S CONNECTION HAS BEEN INTURRUPTED*");
            
         }
       
         
         
      }//End of run method
   
   }//End of threadServer inner class
   
        

}//End of HW7Server class
