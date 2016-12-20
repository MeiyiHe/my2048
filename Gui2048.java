//------------------------------------------------------------------//
// Gui2048.java                                                     //
//                                                                  //
// Game Driver for 2048                                             //
//                                                                  //
// Author:  Meiyi He                                                //
// Date:    12/20/16                                                //
// How to Play: --> javac Gui2048.java (compiling)                  //
// 1) Start the new game: java Gui2048                              //
// 2) Retrieving saved game: java Gui2048 -i 2048.board             //
// 3) Specifying the file to save: java Gui2048 -o [fileName]       // 
//------------------------------------------------------------------//


import javafx.application.*;
import javafx.scene.control.*;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.scene.text.*;
import javafx.geometry.*;
import java.util.*;
import java.io.*;
import java.lang.Math.*;

public class Gui2048 extends Application
{
    private String outputBoard; // The filename for where to save the Board
    private Board board; // The 2048 Game Board

    private static final int TILE_WIDTH = 106;

    private static final int TEXT_SIZE_LOW = 55; // Low value tiles (2,4,8,etc)
    private static final int TEXT_SIZE_MID = 45; // Mid value tiles 
                                                 //(128, 256, 512)
    private static final int TEXT_SIZE_HIGH = 35; // High value tiles 
                                                  //(1024, 2048, Higher)
    private static final int TEXT_SIZE_GAME_OVER = 70;
    // Fill colors for each of the Tile values
    private static final Color COLOR_EMPTY = Color.rgb(224,255,255);
    //(238, 228, 218, 0.35);
    private static final Color COLOR_2 = Color.rgb(255,245,238);
    //(238, 228, 218);
    private static final Color COLOR_4 = Color.rgb(255,240,245);
    //(237, 224, 200);
    private static final Color COLOR_8 = Color.rgb(255,228,225);
    //(242, 177, 121);
    private static final Color COLOR_16 = Color.rgb(252,157,154);
    //(250,128,114);
    //(255,105,180);
    //(245, 149, 99);
    private static final Color COLOR_32 = Color.rgb(254,67,101);
    //(240,128,128);
    //(64,224,208);
    //(238,130,238);
    //(246, 124, 95);
    private static final Color COLOR_64 = Color.rgb(255,105,180);
    //(246, 94, 59);
    private static final Color COLOR_128 = Color.rgb(127,255,212);
    //(237, 207, 114);
    private static final Color COLOR_256 = Color.rgb(127,252,197);
    //(237, 204, 97);
    private static final Color COLOR_512 = Color.rgb(127,248,180);
    //(237, 200, 80);
    private static final Color COLOR_1024 = Color.rgb(127,245,167);
    //(237, 197, 63);
    private static final Color COLOR_2048 = Color.rgb(127,242,150);
    //(237, 194, 46);
    private static final Color COLOR_OTHER = Color.BLACK;
    private static final Color COLOR_GAME_OVER = Color.rgb(238, 228, 218, 0.73);

    private static final Color COLOR_VALUE_LIGHT = Color.rgb(249, 246, 242); 

                        // For tiles < 8

    private static final Color COLOR_VALUE_DARK = Color.rgb(119, 110, 101); 
                       // For tiles >= 8


    /** Add your own Instance Variables here */

    private Color[] colorArray = {COLOR_EMPTY,COLOR_2,COLOR_4,COLOR_8,COLOR_16,
                                  COLOR_32,COLOR_64,COLOR_128,COLOR_256,COLOR_512,
                                  COLOR_1024,COLOR_2048};
    private GridPane pane;
    private int[][] grid;
    private int score;
    private Text scoreText;
    private Text title;

    private Text text;

    private int tileNum;
    
    private Rectangle tile;
    private Rectangle[][] tileArray;
    private Text[][] textArray;


    /**
     * Main method to start the game thread
     */
    @Override
    public void start(Stage primaryStage)
    {
      // Process Arguments and Initialize the Game Board
      processArgs(getParameters().getRaw().toArray(new String[0]));

      // Create the pane that will hold all of the visual objects
      pane = new GridPane();
      pane.setAlignment(Pos.CENTER);
      pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
      pane.setStyle("-fx-background-color: rgb(135,206,250, 0.65)");
        //(187, 173, 160)");

      pane.setPrefSize(400,400);
      // Set the spacing between the Tiles
      pane.setHgap(15); 
      pane.setVgap(15);


      // get the board and score
      grid = board.getGrid();
      score = board.getScore();

      title = new Text();
      scoreText = new Text();

      title.setText("my2048");
      title.setFont(Font.font("Sans Seriff", FontWeight.BOLD, 25));
      title.setFill(Color.BLACK);

      scoreText.setText("Score: " + score);
      scoreText.setFont(Font.font("Sans Seriff", FontWeight.BOLD, 25));
      title.setFill(Color.WHITE);
      
      pane.add(title,0,0);
      pane.add(scoreText, 2,0,2,1);
      GridPane.setHalignment(title, HPos.CENTER);
      GridPane.setHalignment(scoreText, HPos.CENTER);

      // create a sqaure box for each tile
      tileArray = new Rectangle[grid.length][];
      for (int i = 0; i < grid.length; i++)
        tileArray[i] = new Rectangle[grid.length];

      // set the text of each tile
      textArray = new Text[grid.length][];
      for(int i = 0; i < grid.length; i++)
        textArray[i] = new Text[grid.length];


      startGame();
      updateGame();

      Scene scene = new Scene(pane);
      primaryStage.setTitle("my2048");
      primaryStage.setScene(scene);
      primaryStage.show();

      scene.setOnKeyPressed( new myKeyHandler() );

    }

    /** Add your own Instance Methods Here */

    private class myKeyHandler implements EventHandler<KeyEvent> {

      @Override
      public void handle(KeyEvent event) {

        // moving up 
        if (event.getCode() == KeyCode.UP) {

          // first check if it can move, then move
          if (board.canMove(Direction.UP)) {
            board.move(Direction.UP);
            board.addRandomTile();
            updateGame();
            //System.out.println("Moving UP");

            // check if game is over after moving
            if (board.isGameOver()) {
                gameOver();
            //System.out.println("Game Over!");
            }
          }
        }

        else if (event.getCode() == KeyCode.DOWN) {
          // first check if it can move, then move
          if (board.canMove(Direction.DOWN)) {
            board.move(Direction.DOWN);
            board.addRandomTile();
            updateGame();
            //System.out.println("Moving UP");

            // check if game is over after moving
            if (board.isGameOver()) {
                gameOver();
            //System.out.println("Game Over!");
            }
          }
        }

        else if (event.getCode() == KeyCode.LEFT) {
          // first check if it can move, then move
          if (board.canMove(Direction.LEFT)) {
            board.move(Direction.LEFT);
            board.addRandomTile();
            updateGame();
            //System.out.println("Moving UP");

            // check if game is over after moving
            if (board.isGameOver()) {
                gameOver();
            //System.out.println("Game Over!");
            }
          }
        }

        else if (event.getCode() == KeyCode.RIGHT) {
          // first check if it can move, then move
          if (board.canMove(Direction.RIGHT)) {
            board.move(Direction.RIGHT);
            board.addRandomTile();
            updateGame();
            //System.out.println("Moving UP");

            // check if game is over after moving
            if (board.isGameOver()) {
                gameOver();
            //System.out.println("Game Over!");
            }
          }
        }

        //Checks if the "s" key is pressed and proceeds to
        //save the game to an output file if pressed.
        //Catches any errors when saving.
        else if (event.getCode() == KeyCode.S) {
          try {
            board.saveBoard(outputBoard);
          } catch (IOException exception) { 
          
          System.out.println("saveBoard threw an Exception");
          }

          System.out.println("Saving Board to " + outputBoard);
        }

        //Checks to see if game is over.
        else if (board.isGameOver()) {
          gameOver();
          System.out.println("Game Over!");
        }
      }
    }

    /**
     * set up an empty board upon start the game
     */
    private void startGame(){

      grid = board.getGrid();

      for( int row = 0; row < grid.length; row++ ){

        for( int col = 0; col < grid.length; col++ ){
          tile = tileArray[row][col];
          text = textArray[row][col];

          tile = setTile( TILE_WIDTH, TILE_WIDTH, COLOR_EMPTY );
          text = setText("", "Comic Sans", FontWeight.BOLD, TEXT_SIZE_HIGH
                          , COLOR_VALUE_LIGHT);

          pane.add(tile, col, (row+1));
          pane.add(text, col, (row+1));

          GridPane.setHalignment( text, HPos.CENTER );

          tileArray[row][col] = tile;
          textArray[row][col] = text;

        }
      }
    }

    /**
     * update the tile color based on current value
     */
    private void updateTileText(Text t,int num){
      if( num == 0 ){
        t.setText("");
      }else{
        t.setText("" + num);
        t.setFont(Font.font("Comic Sans", FontWeight.BOLD, TEXT_SIZE_LOW));
        t.setFill(COLOR_VALUE_DARK);
      }
    }


    /**
     * update the game based on current grid[][]
     * set score
     */
    private void updateGame(){

      grid = board.getGrid();
      for( int row = 0; row < grid.length; row++ ){
        for( int col = 0; col < grid.length; col++ ){

          tileNum = grid[row][col];
          tile = tileArray[row][col];
          text = textArray[row][col];

          //int index = (int)Math.sqrt(tileNum);
          int index = calculateIdx(tileNum);
          //(int)(log((double)tileNum))/(int)(log((double)2));
          System.out.println("index: " + index);
          tile.setFill(colorArray[index]);
          updateTileText(text, tileNum);
        
          score = board.getScore();
          scoreText.setText("Score: " + score);

        }
      }
    }

    private int calculateIdx(int num){

      int count = 0;
      while(num > 1){

        num = num/2;
        count++;
      }
      return count;
    }


    /**
     * when game over, display game over messages
     */
    private void gameOver() {

      //Creates rectangle and text for Game Over message.
      Rectangle gameOverBox = new Rectangle();
      Text gameOverText = new Text();

      //Sets properties of message's rectangle and text.
      gameOverBox.setFill(COLOR_GAME_OVER);
      gameOverBox.setWidth(pane.getWidth());
      gameOverBox.setHeight(pane.getHeight());
      gameOverText.setText("Game Over!");
      gameOverText.setFont((Font.font("Comic Sans", FontWeight.BOLD, 
          TEXT_SIZE_GAME_OVER)));
      gameOverText.setFill(COLOR_VALUE_DARK);

      //Adds on rectangle and text and centers them.
      pane.add(gameOverBox, 0, 0, grid.length, grid.length + 1);
      pane.add(gameOverText, 0, 2, grid.length, (grid.length / 2));
      GridPane.setHalignment(gameOverBox, HPos.CENTER);
      GridPane.setValignment(gameOverBox, VPos.CENTER);
      GridPane.setHalignment(gameOverText, HPos.CENTER);
      GridPane.setValignment(gameOverText, VPos.CENTER);

    }

    private Rectangle setTile( int width, int height, Color color){

      Rectangle tile = new Rectangle();
      tile.setWidth(width);
      tile.setHeight(height);
      tile.setFill(color);

      return tile;
    }

    private Text setText(String string, String font, FontWeight weight, 
                          int size, Color color){
      Text text = new Text();
      text.setText(string);
      text.setFont(Font.font(font,weight,size));
      text.setFill(color);

      return text;
    }


    /** DO NOT EDIT BELOW */

    // The method used to process the command line arguments
    private void processArgs(String[] args)
    {
        String inputBoard = null;   // The filename for where to load the Board
        int boardSize = 0;          // The Size of the Board

        // Arguments must come in pairs
        if((args.length % 2) != 0)
        {
            printUsage();
            System.exit(-1);
        }

        // Process all the arguments 
        for(int i = 0; i < args.length; i += 2)
        {
            if(args[i].equals("-i"))
            {   // We are processing the argument that specifies
                // the input file to be used to set the board
                inputBoard = args[i + 1];
            }
            else if(args[i].equals("-o"))
            {   // We are processing the argument that specifies
                // the output file to be used to save the board
                outputBoard = args[i + 1];
            }
            else if(args[i].equals("-s"))
            {   // We are processing the argument that specifies
                // the size of the Board
                boardSize = Integer.parseInt(args[i + 1]);
            }
            else
            {   // Incorrect Argument 
                printUsage();
                System.exit(-1);
            }
        }

        // Set the default output file if none specified
        if(outputBoard == null)
            outputBoard = "2048.board";
        // Set the default Board size if none specified or less than 2
        if(boardSize < 2)
            boardSize = 4;

        // Initialize the Game Board
        try{
            if(inputBoard != null)
                board = new Board(inputBoard, new Random());
            else
                board = new Board(boardSize, new Random());
        }
        catch (Exception e)
        {
            System.out.println(e.getClass().getName() + 
                               " was thrown while creating a " +
                               "Board from file " + inputBoard);
            System.out.println("Either your Board(String, Random) " +
                               "Constructor is broken or the file isn't " +
                               "formated correctly");
            System.exit(-1);
        }
    }

    // Print the Usage Message 
    private static void printUsage()
    {
        System.out.println("Gui2048");
        System.out.println("Usage:  Gui2048 [-i|o file ...]");
        System.out.println();
        System.out.println("  Command line arguments come in pairs of the "+ 
                           "form: <command> <argument>");
        System.out.println();
        System.out.println("  -i [file]  -> Specifies a 2048 board that " + 
                           "should be loaded");
        System.out.println();
        System.out.println("  -o [file]  -> Specifies a file that should be " + 
                           "used to save the 2048 board");
        System.out.println("                If none specified then the " + 
                           "default \"2048.board\" file will be used");  
        System.out.println("  -s [size]  -> Specifies the size of the 2048" + 
                           "board if an input file hasn't been"); 
        System.out.println("                specified.  If both -s and -i" + 
                           "are used, then the size of the board"); 
        System.out.println("                will be determined by the input" +
                           " file. The default size is 4.");
    }
}
