import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;

//Modeling was done with help of Darius Paulauskas

public class MinesweeperGame {

    // Variables ***********************************************************************
    private double mineProbability;
    private long randomSeed;
    private int [] gameGrid;
    private ArrayList<Integer> flags;
    private boolean [] explored;
    private int numberOfMines;
    private int squareLength;
    private int numberOfTilesExplored;
    private long startTime;
    private long stopTime;

    public enum GameState { PLAYING, GAME_OVER, WON }
    public enum Difficulty { EASY, INTERMEDIATE, EXPERT }
    private GameState gameState;


    public static final int MINE = -1;
    public static final int FLAGGED = -2;
    public static final int UNEXPLORED = -3;

    private static final int EASY_NUMBER_OF_TILES = 9;
    private static final double EASY_MINE_PROBABILITY = 0.123456789;

    private static final int INTERMEDIATE_NUMBER_OF_TILES = 16;
    private static final double INTERMEDIATE_MINE_PROBABILITY = 0.15625;

    private static final int EXPERT_NUMBER_OF_TILES = 22;
    private static final double EXPERT_MINE_PROBABILITY = 0.20661157;

    private static final String FILE_EXTENSION = "msg";

    // Methods *************************************************************************

    public MinesweeperGame (int numberOfTiles, double mineProbability, long debugSeed) {

        reset (numberOfTiles, mineProbability, debugSeed);

    }

    public GameState getGameState () {

        return gameState;

    }

    public int getNumberOfMines () {

        return numberOfMines;

    }

    public long getRandomSeed () {

        return randomSeed;

    }

    public int getSquareLength () {

        return squareLength;

    }

    public int getNumberOfFlags () {

        return flags.size ();

    }

    public int getEstimatedNumberOfMines () {

        return numberOfMines - flags.size ();

    }

    public int getStateOf (int position) {

        // Is the position given invalid?
        if (position < 0 || position >= gameGrid.length) {

            System.out.println ("Tile Invalid");
            assert (false);

        }

        if (explored[position]) {

            return gameGrid[position];

        }

        for (int i = 0; i < flags.size (); i++) {

            if (flags.get (i) == position) {

                return FLAGGED;

            }

        }

        return UNEXPLORED;

    }
    /**
     * Gets the time elapsed since the game started.
     * Returns the time elapsed since the game started in seconds.
     */
    public float getGameTime () {

        return (System.currentTimeMillis () - startTime) / 1000.0f;

    }

    /**
     * Gets the time elapsed since the game started to when it ended.
     * Returns the time elapsed since the game started to when it ended in seconds.
     */
    public float getFinalTime () {

        return (stopTime - startTime) / 1000.0f;

    }

    /**
     * Gets the file extension used to save games.
     * Returns FILE_EXTENSION.
     */
    public static String getFileExtension () {

        return FILE_EXTENSION;

    }

    /**
     * Prints the game board to the console
     */
    public void print () {

        for (int i = 0; i < gameGrid.length; i++) {

            if (i != 0 && i % squareLength == 0) {

                System.out.println ();

            }

            if (gameGrid[i] == MINE) {

                System.out.print ('*');

            } else {

                System.out.print (gameGrid[i]);

            }

        }

    }

    /**
     * Checks to see if the selected tile is a mine or not, alters gameState
     * of tile to check
     * Returns false if not a mine, returns true otherwise
     */
    public boolean exploreTile (int position) {

        // Is the position given invalid?
        if (position < 0 || position >= gameGrid.length) {

            System.out.println ("Tile Invalid");
            assert (false);

        }

        if (explored[position]) {

            return false;

        }

        explored[position] = true;

        if (gameGrid[position] != MINE) {

            numberOfTilesExplored++;

            if (numberOfMines + numberOfTilesExplored == gameGrid.length) {

                gameState = GameState.WON;
                stopTime = System.currentTimeMillis ();
                revealAll ();

            } else if (gameGrid[position] == 0) {

                exploreAdjacent (position);

            }

            return false;

        }

        gameState = GameState.GAME_OVER;
        stopTime = System.currentTimeMillis ();
        revealAll ();
        return true;

    }

    /**
     * Toggles a tile to be flagged or unflagged.
     * @param Index of tile to flagged
     */
    public void flagTile (int position) {

        // Is the position given invalid?
        if (position < 0 || position >= gameGrid.length) {

            System.out.println ("Tile Invalid");
            assert (false);

        }

        if (explored[position]) {

            return;

        }

        for (int i = 0; i < flags.size (); i++) {

            if (flags.get (i) == position) {

                flags.remove (i);
                return;

            }

        }

        if (flags.size () < numberOfMines) {

            flags.add (position);

        }

    }

   }