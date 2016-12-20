//------------------------------------------------------------------//
// Board.java                                                       //
//                                                                  //
// Class used to represent a 2048 game board                        //
//                                                                  //
// Author:  Shuaiqi Xia		                                        //
// Date:    04/09/16                                                //
//------------------------------------------------------------------//


import java.util.*;
import java.io.*;

public class Board {
    public final int NUM_START_TILES = 2;
    public final int TWO_PROBABILITY = 90;
    public final int GRID_SIZE;


    private Random random = new Random(2015);
    private int[][] grid;
    private int score;

    // TODO PSA3
    // Constructs a fresh board with random tiles
    public Board(int boardSize, Random random) {
        this.random = random; // FIXME
        GRID_SIZE = boardSize; // FIXME
        this.grid = new int[boardSize][boardSize]; //intializing the gird
        this.score = 0; // initializing the score

        // this tile will be added to the board using the addRandomTile()
        for (int i = 0; i < NUM_START_TILES; i++){
            this.addRandomTile();
        }

    }

    // TODO PSA3
    // Construct a board based off of an input file
    public Board(String inputBoard, Random random) throws IOException {
        this.random = random; // FIXME

        //read the string input in format --> TODO
        /*
        size
        score
        board [size]*[size]
        */
        Scanner scanner = new Scanner(new File(inputBoard));
        GRID_SIZE = scanner.nextInt();
        score = scanner.nextInt();

        grid = new int[GRID_SIZE][GRID_SIZE];
        for( int i = 0; i < GRID_SIZE; i++ ){
            for( int j = 0; j < GRID_SIZE; j++ ){
                this.grid[i][j] = 0;
            }
        }

        int row = -1;
        int col = -1;        
        while(scanner.hasNext()){
            String line = scanner.nextLine();
            Scanner innerScan = new Scanner(line);
            col = 0;
            while(innerScan.hasNext()){

                this.grid[row][col] = innerScan.nextInt();
                col++;
            }
            row++;
            innerScan.close();
        }
        scanner.close();

    }
        

    // TODO PSA3
    // Saves the current board to a file
    public void saveBoard(String outputBoard) throws IOException {
        //write the string output in format --> TODO
        /*
        size
        score
        board [row]*[col]
        */
        File file = new File(outputBoard);
        PrintWriter pw = new PrintWriter(outputBoard);

        pw.println(this.GRID_SIZE);
        pw.println(this.score);
        for( int i = 0; i < GRID_SIZE; i++ ){
                for( int j = 0; j < GRID_SIZE; j++ ){
                    pw.print(this.grid[i][j] + " ");
            }
            pw.println();
        }
        pw.close();
    }

    // TODO PSA3
    // Adds a random tile (of value 2 or 4) to a
    // random empty space on the board
    public void addRandomTile() {

        int count = 0;
        int location = 0;
        int value = 0;
        int index = -1;// set to -1 so the first grid encountered = 0
        //random = new Random(2015); 
        int boardSize = GRID_SIZE;

        // count the empty spot first
        for(int i = 0; i < boardSize; i++){
            for(int j = 0; j < boardSize; j++){
                if( grid[i][j] == 0 ){
                    count++;
                }
            }
        }

        // if no empty spot left, exit the game
        if(count == 0) System.exit(1);

        // random(int) --> [0, int)
        location = random.nextInt( count );
        value = random.nextInt( 100 );

        for(int i = 0; i < boardSize; i++){
            for(int j = 0; j < boardSize; j++){
                if( grid[i][j] == 0 ){
                    index++;

                    if( index == location ){

                        // when current grid is the desired location
                        if( value < 90 ){
                            // 2's probablity is 90%
                            this.grid[i][j] = 2;
                        }else{
                            this.grid[i][j] = 4;
                        }
                    }
                }
            }
        }        
    }



    // TODO PSA3
    // Rotates the board by 90 degrees clockwise or 90 degrees counter-clockwise.
    // If rotateClockwise == true, rotates the board clockwise , else rotates
    // the board counter-clockwise
    public void rotate(boolean rotateClockwise) {
        int boardSize = GRID_SIZE;
        int[][] newGrid = new int[boardSize][boardSize];

        if(rotateClockwise){
            // row + newCol  = gridSize || col = newRow 

            for(int row = 0; row < boardSize; row++){
                for(int col = 0; col < boardSize; col++){
                    
                    newGrid[row][col] = grid[ boardSize-col-1 ][ row ];

                }
            }
        }else{
            // counterClockwise
            //  col + newRow  = gridSize || row = newCol
 
            for(int i = 0; i < boardSize; i++){
                for(int j = 0; j < boardSize; j++){
                    newGrid[i][j] = grid[j][ boardSize-i-1 ];
                }
            }
        }
        // redirect the grid reference
        this.grid = newGrid;
    }



    //Complete this method ONLY if you want to attempt at getting the extra credit
    //Returns true if the file to be read is in the correct format, else return
    //false
    public static boolean isInputFileCorrectFormat(String inputFile) {
        //The try and catch block are used to handle any exceptions
        //Do not worry about the details, just write all your conditions inside the
        //try block
        try {
            //write your code to check for all conditions and return true if it satisfies
            //all conditions else return false
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // No need to change this for PSA3
    // Performs a move Operation
    public boolean move( Direction direction ) {
        
        if(direction == Direction.LEFT){
            return moveLeft();
        }
        if(direction == Direction.RIGHT){
            return moveRight();
        }
        if(direction == Direction.UP){
            return moveUp();
        }
        if(direction == Direction.DOWN){
            return moveDown();
        }
        return false;
    }

    /**
     * Helper methods for move()
     * =============================================================
     */
    private boolean moveRight(){
    
      for( int row = 0; row < GRID_SIZE; row++ ){
        int col = GRID_SIZE-1;

        Queue<Integer> oldGrid = new LinkedList<Integer>();
        for( int i = col; i >= 0; i-- ){
          if( grid[row][i] != 0 )
            oldGrid.add( grid[row][i] );
        }

        Queue<Integer> newGrid = new LinkedList<Integer>();

        while( !oldGrid.isEmpty() ){
          int value = oldGrid.poll();
          if( !oldGrid.isEmpty() && ( value == oldGrid.peek() ) ){
            newGrid.add(value*2);
            this.score += value*2;
            oldGrid.poll();
          }else{
            newGrid.add(value);
          }
        }

        for( int i = col; i >= 0; i--){
          grid[row][i] = (newGrid.isEmpty()) ? 0 : newGrid.poll();
        }
      }
      return true;
    }

    private boolean moveLeft(){
      
      boolean check = false;
      rotate(false);
      rotate(false);
      check = moveRight();
      rotate(false);
      rotate(false);

      return check;
    }


    private boolean moveUp(){

        boolean check = false;
        rotate(true);
        check = moveRight();
        rotate(false);

        return check;
    }

    private boolean moveDown(){

      boolean check = false;
      rotate(false);
      check = moveRight();
      rotate(true);

      return check;

    }


    /**
     * ==============================================================
     */    
    // Check to see if we have a game over
    public boolean isGameOver() {

      for( Direction direction: Direction.values() ){
        if( canMove(direction) )
          return false;
      }
      System.out.println("Game Over!");
      return true;
    }


    // Determine if we can move in a given direction
    public boolean canMove(Direction direction) {

      if(direction == Direction.LEFT){
          return canMoveLeft();
      }
      if(direction == Direction.RIGHT){
          return canMoveRight();
      }
      if(direction == Direction.UP){
          return canMoveUp();
      }
      if(direction == Direction.DOWN){
          return canMoveDown();
      } 
    
    return false;
      
    }

    /**
     * Helper methods for canMove()
     * ================================================================
     */

    private boolean canMoveRight(){
        // loop over the row in RIGHTmost column when trying to move right
        
      for( int row = 0; row < GRID_SIZE; row++ ){
        
        for( int col = GRID_SIZE-1; col > 0; col-- ){
          if( ((grid[row][col] == 0 )&&(grid[row][col-1] != 0 )) ||
              ((grid[row][col] != 0 )&&(grid[row][col-1] == grid[row][col] ))  )
              return true;     
        }
      }
      return false;
    }

    private boolean canMoveLeft(){
      
      rotate(false);
      rotate(false);
      boolean check = canMoveRight();
      rotate(false);
      rotate(false);

      return check;   
    }


    private boolean canMoveUp(){

      rotate(true);
      boolean check = canMoveRight();
      rotate(false);
      return check;

    }
    private boolean canMoveDown(){
      
      rotate(false);
      boolean check = canMoveRight();
      rotate(true);

      return check;  
    }

    /**
     * ==============================================================
     */
    // Return the reference to the 2048 Grid
    public int[][] getGrid() {
        return grid;
    }

    // Return the score
    public int getScore() {
        return score;
    }

    // print the board ???
    @Override
    public String toString() {
        StringBuilder outputString = new StringBuilder();
        outputString.append(String.format("Score: %d\n", score));
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int column = 0; column < GRID_SIZE; column++)
                outputString.append(grid[row][column] == 0 ? "    -" :
                        String.format("%5d", grid[row][column]));

            outputString.append("\n");
        }
        return outputString.toString();
    }
}
