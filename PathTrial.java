import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
import java.util.ArrayList;
import javax.swing.border.EmptyBorder;

public class PathTrial{
   int[][] playerSpace = new int[][]
   {
      { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
      { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
      { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
      { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
      { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
      { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
      { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
      { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
      { 0, 0, 0, 0, 0, 0, 0, 0, 0 }
                                    };
                                    
   int[][] rightWall = new int[][]
   {
      { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
      { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
      { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
      { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
      { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
      { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
      { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
      { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
      { 0, 0, 0, 0, 0, 0, 0, 0, 0 }
                                    };

   int[][] bottomWall = new int[][]
   {
      { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
      { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
      { 1, 1, 1, 0, 0, 1, 1, 1, 1 },
      { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
      { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
      { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
      { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
      { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
      { 0, 0, 0, 0, 0, 0, 0, 0, 0 }
                                    };

   int[][] centerWall = new int[][]
   {
      { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
      { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
      { 1, 1, 1, 0, 0, 1, 1, 1, 1 },
      { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
      { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
      { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
      { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
      { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
      { 0, 0, 0, 0, 0, 0, 0, 0, 0 }
                                    };
   int[][] solution = new int[][]
   {
      { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
      { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
      { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
      { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
      { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
      { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
      { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
      { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
      { 0, 0, 0, 0, 0, 0, 0, 0, 0 }
                                    };
                                    
   int[][] tLocation = new int[][]{
                                 {0,0}, //change 2nd number to 4 when using this for real
                                 {8,4},
                                 {4,0},
                                 {4,8}
                                       };
                                       
   int[] winCon = new int[]{8 , 0, 8, 0}; //p1-4 col or row needed to win
   
   int[][] pathOrder = new int[][]{
                                    { 1, 0, 0, 1},
                                    {-1, 0, 0,-1},
                                    { 0, 1, 1, 0},
                                    { 0,-1,-1, 0},
                                                   };
                                                   
   int pAmount = 4; //# of players 
                                       
   public static void main(String[] args){
      new PathTrial();
   }//end of main
                                    
   public PathTrial(){
      for(int pNum = 0; pNum < pAmount; pNum++){//change pAmount for less players or have a list of only active players (Option)
         if(pathFound(pNum) == false){
            clearArray(solution);
            //return false;
            System.out.println("Wall Blocks Path");
            break;
         }
         clearArray(solution);
         if(pNum == pAmount -1){
            System.out.println("Wall placement Allowed");
            //return true;
         }
      }
   }//end PathTrail
   
   public void printSolution(int solution[][], int bottomWall[][], int rightwall[][],int centerWall[][]){
      for(int i = 0; i < 9; i++){
         for(int j = 0; j < 9; j++){
               System.out.print(" " + solution[i][j] + " " + rightWall[i][j]);
         }//end of inner for loop
         System.out.println();
         for(int j = 0; j < 9; j++){
               System.out.print(" " + bottomWall[i][j] + " " + centerWall[i][j]);
         }//end of inner for loop         
         System.out.println();
      }//end of outer for loop
     System.out.println("Exit printSolution");
   }//end printSolution
   
   boolean pathFound(int playerNum){
   
      //passes in player token locations and player number
      if (pathRec(tLocation[playerNum][0], tLocation[playerNum][1], playerNum) == false){
      
         System.out.println("No path found");
         return false;
      }
      
      System.out.println("Path found");     
      return true;
   }//end pathFound
   
   //returns false if the cordinates are outside of the array or player space is filled
   boolean isSafe(int x, int y, int array[][]){
      return( x >= 0 && x < 9 && y >= 0 && y < 9 && array[x][y] == 0); //replace 9 with a variable for Array lengths
   }//end isSafe
   
   //reset the solution to empty
   public void clearArray(int array[][]){
      for(int i = 0; i < 9; i++){
         for(int j = 0; j < 9; j++){
            array[i][j] = 0;
         }//end of inner loop
      }//end of outer loop
   }//end of Clear Solution
      
   boolean pathRec(int x, int y, int num){
   
      //Check for path avaliable allows exit of recursion
      if((num == 0 || num == 1) && x == winCon[num]){
         //System.out.println("in win codition "+ x + " "+ winCon[num]);
         solution[x][y] = 1;
         printSolution(solution, bottomWall, rightWall, centerWall); //Prints array of solution path
         return true; 
      }//end of if , check for player 1 and 2 have a path
      if((num == 2 || num == 3) && y == winCon[num]){
         solution[x][y] = 1;
         printSolution(solution, bottomWall, rightWall, centerWall); //Prints array of solution path
         return true;
      }// end of if, check for player 3 and 4 have a path
      
      if(isSafe(x, y, solution) == true){ //runs if player space is safe
         
         //mark solution path taken
         solution[x][y] = 1;
         
         //check for movement depending on player direction needed to win
         if(num == 0){
            //try south
            if(isSafe(x,y,bottomWall) == true){ //check if wall in array and empty 
               if(pathRec(x + 1, y, num)){
                  return true;
               }
            }
            //try east
            if(isSafe(x,y,rightWall) == true){ //check if wall in array and empty
               if(pathRec(x, y + 1, num)){
                  return true;
               }
            }
            //try west
            if(isSafe(x,y-1,rightWall) == true){//check if wall in array and empty
               if(pathRec(x, y - 1, num)){
                  return true;
               }
            }
            //backtrack
            solution[x][y] = 0;
            return false;          
         }
         if(num == 1){
            //try north
            if(isSafe(x - 1,y,bottomWall) == true){ //check if wall in array and empty 
               if(pathRec(x - 1, y, num)){
                  return true;
               }
            }
            //try west
            if(isSafe(x,y - 1,rightWall) == true){//check if wall in array and empty
               if(pathRec(x, y - 1, num)){
                  return true;
               }
            }
            //try east
            if(isSafe(x,y,rightWall) == true){ //check if wall in array and empty
               if(pathRec(x, y + 1, num)){
                  return true;
               }
            }
            //backtrack
            solution[x][y] = 0;
            return false;  
         }
         if(num == 2){
            //try east
            if(isSafe(x,y,rightWall) == true){ //check if wall in array and empty
               if(pathRec(x, y + 1, num)){
                  return true;
               }
            }
            //try south
            if(isSafe(x,y,bottomWall) == true){ //check if wall in array and empty 
               if(pathRec(x + 1, y, num)){
                  return true;
               }
            }
            //try north
            if(isSafe(x - 1,y,bottomWall) == true){ //check if wall in array and empty 
               if(pathRec(x - 1, y, num)){
                  return true;
               }
            }
            //backtrack
            solution[x][y] = 0;
            return false;    
         }
         if(num == 3){
            //try west
            if(isSafe(x,y - 1,rightWall) == true){//check if wall in array and empty
               if(pathRec(x, y - 1, num)){
                  return true;
               }
            }
            //try north
            if(isSafe(x - 1,y,bottomWall) == true){ //check if wall in array and empty 
               if(pathRec(x - 1, y, num)){
                  return true;
               }
            }
            //try south
            if(isSafe(x,y,bottomWall) == true){ //check if wall in array and empty 
               if(pathRec(x + 1, y, num)){
                  return true;
               }
            }
            //backtrack
            solution[x][y] = 0;
            return false;    
         }
      }//is playerspace safe end 
      
      return false; //if player space is unsafe           
   }//end of path Recursion  
}//end 