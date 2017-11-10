import java.util.*;
import java.io.*;

public class PlayerInfo implements Serializable {

   String name;
   
   public PlayerInfo(String name_){
      name = name_;

   } //End of constructor
   
   public String getName(){
      return name;
   }
   
   public String toString(){
      return "Player = " + name;
   } 
   
   public String welcomeMessage(){
      return "Player " + name + " has joined\n";
   } 






} //End of class playerInfo 