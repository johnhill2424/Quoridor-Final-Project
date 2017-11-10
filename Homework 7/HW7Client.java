import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
   The client side of the HW 7 chat program. This file contains the information
   that would be seen and interacted with by the client. Entering within the textfield
   of the GUI and pressing the "Send!" button will result in the message being sent to
   the server, and displayed on all other clients' text areas.
   
   @author Jack Old
   @version 1.0

*/
public class HW7Client extends JFrame{

   private JTextArea jta = new JTextArea(15, 30);
   private JTextField jtf;
   private JButton jbSend;
   private boolean endClient = false;

/**
   The Main method creates a new HW7Client object.
   
   @param args Command line input
*/  
   public static void main(String[] args){
      
      
      new HW7Client();
      
   
   }//End of HW7Client main

/**
   Constructor initializes JFrame components, including the use of JTextArea, JTextField,
   and JButton. 
   <p>
   Constructor then creates a socket object which will be used to connect to a running
   instance of HW7Server.
   <p>
   There is also an action listener for the JButton, as well as a windowListener.
*/
   public HW7Client(){
      
      //Create JTextArea and make it Scrollable
      JScrollPane scroll = new JScrollPane(jta);
      
      jta.setEditable(false);
      add(scroll, BorderLayout.CENTER);
      
      //Create JTextField and JButton
      JPanel jpSend = new JPanel();
      
      jtf = new JTextField(20);
      jpSend.add(jtf);
      
      jbSend = new JButton("Send!");
      jpSend.add(jbSend);
      
      //add panels to JFrame
      add(jpSend, BorderLayout.SOUTH);
      
      
      //Gui Components
      setTitle("Chat Client");
      pack();
      setLocationRelativeTo(null);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      setVisible(true);
      
      //Set up a connection to a server
      try{
         
         Socket s = new Socket("127.0.0.1", 16789);
         
         //Create I/O Streams
         OutputStream out = s.getOutputStream();
         PrintWriter pout = new PrintWriter(new OutputStreamWriter(out));
         
         InputStream in = s.getInputStream();
         BufferedReader bin = new BufferedReader(new InputStreamReader(in));
      
         MessageChecker mc = new MessageChecker(bin);
         
         //Start the thread object
         mc.start();
         
         
         //JBSend's action listener
         jbSend.addActionListener(new SendButton(pout));

/**
   An anonymous inner class that acts as a window listener for the HW7Client JFrame.
   It detects when the window is closing, and makes sure that I/O streams and threads
   are smoothly compromised, to prevent errors.
*/         
         this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e){
               //Print info stating user has exited
               try{  
                  pout.println("*A USER HAS DISCONNECTED*");
                  pout.flush();
                 //Print special command to tell server to remove print writer from vector. 
                  pout.println("***EXITING***");
                  pout.flush();
                 //Setting boolean value to true to break out of thread loop 
                  endClient = true;
                  
                 //Join and closing operations 
                  mc.join();
                  
                  bin.close();
                  pout.close();
                  s.close();
               }
               catch(IOException ioe){
                  ioe.printStackTrace();
                  System.out.println("WindowClosing IOException");
               }
               catch(InterruptedException ie){
                  ie.printStackTrace();
                  System.out.println("WindowClosing InterruptedException");
               }
               
            
            }
      
         });
      
      }
      catch(UnknownHostException uhe){
         uhe.printStackTrace();
         System.out.println("Unknown Server");
      }
      catch(NullPointerException npe){
         System.out.println("Inturrupted Client Connection");
         jta.append("*A CLIENT'S CONNECTION HAS BEEN INTURRUPTED*");
      }
      catch(IOException io){
         io.printStackTrace();
         System.out.println("Server hasn't been started yet");
         String connectMsg = String.format("A server has not been started yet. %nPlease try reconnecting at a later time.");
         //This jOptionPane shows up when the client starts before a server is created
         JOptionPane.showMessageDialog(null, connectMsg);
      }
      catch(ArrayIndexOutOfBoundsException ab){
         ab.printStackTrace();
         System.out.println("AIOOBException");
      }
   
   
   }//End of HW7Client constructor

/**
   SendButton class is the action listener class that activates when jbSend is pressed.
*/   
   class SendButton implements ActionListener{
   
      private PrintWriter pout;

/**
   Constructor that takes in PrintWriter as a parameter and assigns it as an attribute
   for the SendButton class.
   
   @param _pout The PrintWriter object that was created in the HW7Client constructor needs
                to be passed through to be used by the method actionPerformed.
*/   
      public SendButton(PrintWriter _pout){
      
         pout = _pout;
      }
/**
   ActionPerformed method takes in the text from JTextField jtf and sends it out through
   PrintWriter pout. It then clears the TextField.
   
   @param ae Detects whener jbSend has been pressed.
*/   
      public void actionPerformed(ActionEvent ae){
       //Take jtf text and send it to server via Print Writer  
         pout.println(jtf.getText());
         
         pout.flush();
       //Clear text once message has been sent.  
         jtf.setText(null);
      
      }
   
   }//End of SendButton inner class

/**
   MessageChecker class creates a threaded object for HW7Client.
*/   
   class MessageChecker extends Thread{
   
      private BufferedReader bin;
/**
   Constructor for the MessageChecker class. Takes in Buffered Reader and assigns
   it as an attribute.
   
   @param _bin Buffered Reader that will be used to read messages.
*/      
      public MessageChecker(BufferedReader _bin){
         
         bin = _bin;
      }
 /**
   The run method for MessageChecker. Constantly checks for string inputs that are
   sent from the HW7Server class. It then appends the string it reads to JTextArea jta.
   <p>
   Checks to see if endClient boolean value because true, due to when window listener
   assigns it to be true. Once this happens, it breaks out of the loop and ends the thread.
 */     
      public void run(){
      
         String input = "";
      //Loop constantly checks for input stream from server.
         //while(true){
         
            try{
               while((input = bin.readLine()) != null){
                  //The boolean value becomes true when window listener activates.
                  if(endClient == true){
                     break;
                  }
                  else{
                     //Take the string read in, and append to the clients' JTextArea
                     
                     
                     String fInput = String.format("> %s %n", input);
                     System.out.println("> "+input);
                     
                     jta.append(fInput);
                  }
               }
               jta.append("*SERVER HAS SHUT DOWN UNEXPECTEDLY*");
               
            }
            catch(IOException ioe){
               System.out.println("The Server has been shut down");
               jta.append("*SERVER HAS SHUT DOWN UNEXPECTEDLY*");
               
            }
         //}
      
      
      }//End of run method
   
   
   }//End of MessageChecker inner class
   
   

}//End of HW7Client Class