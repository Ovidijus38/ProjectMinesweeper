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

   }