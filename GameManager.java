//------------------------------------------------------------------//
// GameManager.java                                                 //
//                                                                  //
// Game Manager for 2048                                            //
//                                                                  //
// Author:  Shuaiqi Xia		                                        //
// Date:    04/09/16                                                //
//------------------------------------------------------------------//

import java.util.*;
import java.io.*;

public class GameManager {
    // Instance variables
    private Board board; // The actual 2048 board
    private String outputFileName; // File to save the board to when exiting

    // TODO PSA3
    // GameManager Constructor
    // Generate new game
    public GameManager(int boardSize, String outputBoard, Random random) {
        
        System.out.println("Generating a New Board");

        board = new Board( boardSize, random );
    }

    // TODO PSA3
    // GameManager Constructor
    // Load a saved game
    public GameManager(String inputBoard, String outputBoard, Random random) throws IOException {
        
        System.out.println("Loading Board from " + inputBoard);
        board = new Board( inputBoard, random );
    }

    // TODO PSA3
    // Main play loop
    // Takes in input from the user to specify moves to execute
    // valid moves are:
    //      k - Move up
    //      j - Move Down
    //      h - Move Left
    //      l - Move Right
    //      q - Quit and Save Board
    //
    //  If an invalid command is received then print the controls
    //  to remind the user of the valid moves.
    //
    //  Once the player decides to quit or the game is over,
    //  save the game board to a file based on the outputFileName
    //  string that was set in the constructor and then return
    //
    //  If the game is over print "Game Over!" to the terminal
    public void play() throws IOException {
        // first print the control
        printControls();
        // then print out the current state of the 2048 board 
        System.out.println(board.toString());

        Scanner input = new Scanner(System.in);

        while( input.hasNext() ){

            // take user's input conmmand --> Char
            userKey = 'k';

            if( checkKey(userKey) ){
                // check if it is a valid move --> canMove()
                // if valid perform that move, add a new random tile
                // else prompt user again
            }else{
                //print board, prompt again for command
            }

            // first print the control
            printControls();
            // then print out the current state of the 2048 board 
            System.out.println(board.toString());

        }
        

    }

    public boolean checkKey(KeyEvent e){
        int key = e.getKeyChar();

        if(key == 'k' || key =='j' || key =='h' || key =='l'){
            return true;
        }else if(key =='q'){
            System.out.println("press q key...need implement");
            
            //save the board to the output file
            this.outputBoard = board.toString();
            // exit the game
            System.exit(1);
        }else{
            return false;
        }
    }

    public void keyTyped(KeyEvent e){
        int key = e.getKeyChar();
        //get the keycode
        if( key == 'k' ){

        }

    }


    // Print the Controls for the Game
    private void printControls() {
        System.out.println("  Controls:");
        System.out.println("    k - Move Up");
        System.out.println("    j - Move Down");
        System.out.println("    h - Move Left");
        System.out.println("    l - Move Right");
        System.out.println("    q - Quit and Save Board");
        System.out.println();
    }
}
