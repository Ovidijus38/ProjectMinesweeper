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

/**
 * This part does all main game process
 */

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
    
// public enum was told to use by Darius

    public enum GameState { PLAYING, GAME_OVER, WON }
    public enum Difficulty { EASY, INTERMEDIATE, EXPERT }
    private GameState gameState;

// line 38 to line 51 those number was told how to do by Darius

    public static final int MINE = -1;
    public static final int FLAGGED = -2;
    public static final int UNEXPLORED = -3;

    private static final int EASY_NUMBER_OF_TILES = 9;
    private static final double EASY_MINE_PROBABILITY = 0.12;

    private static final int INTERMEDIATE_NUMBER_OF_TILES = 16;
    private static final double INTERMEDIATE_MINE_PROBABILITY = 0.15;

    private static final int EXPERT_NUMBER_OF_TILES = 22;
    private static final double EXPERT_MINE_PROBABILITY = 0.20;

    private static final String FILE_EXTENSION = "msg";

    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

    // Methods *************************************************************************

    /**
     * This is three argument constructor
     * @param numberOfTiles
     * @param mineProbability
     * @param debugSeed
     */

    public MinesweeperGame (int numberOfTiles, double mineProbability, long debugSeed) {

//reset was idea of Darius
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

    /**
     *
     * @param position
     * @return
     */

    public int getStateOf (int position) {

        //this part partialy was supervised by Darius

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
    /*
     * Gets the time elapsed since the game started.
     * Returns the time elapsed since the game started in seconds.
     */

    public float getGameTime () {

        return (System.currentTimeMillis () - startTime) / 1000.0f;

    }

    /*
     * Gets the time elapsed since the game started to when it ended.
     * Returns the time elapsed since the game started to when it ended in seconds.
     *
     */

    public float getFinalTime () {

        return (stopTime - startTime) / 1000.0f;

    }

    /*
     * Gets the file extension used to save games.
     * Returns FILE_EXTENSION.
     */
    // this logic by Darius
    public static String getFileExtension () {

        return FILE_EXTENSION;

    }

    /*
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

    /*
     * Checks to see if the selected tile is a mine or not, adjust gameState
     * of tile to check
     * Returns false if not a mine, returns true otherwise
     */

    /**
     *
     * @param position
     * @return
     */

    // from line 224 to 330 partially advice by Darius
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

    /*
     * Toggles a tile to be flagged or unflagged.
     */

    /**
     *
     * @param position
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

    /**
     * Sets games difficulty
     * @param difficulty
     */

    //this difficulty section modeled by Darius
    public void newGame (Difficulty difficulty) {

        switch (difficulty) {

            case EASY:
                reset (EASY_NUMBER_OF_TILES, EASY_MINE_PROBABILITY, -1);
                break;

            case INTERMEDIATE:
                reset (INTERMEDIATE_NUMBER_OF_TILES, INTERMEDIATE_MINE_PROBABILITY, -1);
                break;

            case EXPERT:
                reset (EXPERT_NUMBER_OF_TILES, EXPERT_MINE_PROBABILITY, -1);
                break;

        }

    }

    /*
     * Loads a game from the given file.
     */

    /**
     *
     * @param saveFile
     * @return
     */
    public boolean load (File saveFile) {

        String filename = saveFile.getName ();
        FileInputStream inStream = null;
        BufferedInputStream bufferedInStream = null;
        ObjectInputStream objectStream = null;

        if (filename.indexOf (".") == -1) {

            saveFile = new File (filename + "." + FILE_EXTENSION);
            filename = saveFile.getName ();

        }

        if (saveFile.exists () && saveFile.canRead () && (filename.substring (filename.indexOf ("."))).equals ("." + FILE_EXTENSION)) {

            //this part was help for modeling from Darius Paulauskas

            try {

                inStream = new FileInputStream (saveFile);
                bufferedInStream = new BufferedInputStream (inStream);
                objectStream = new ObjectInputStream (bufferedInStream);

                mineProbability = (Double) (objectStream.readObject ());
                randomSeed = (Long) (objectStream.readObject ());
                numberOfMines = (Integer) (objectStream.readObject ());
                squareLength = (Integer) (objectStream.readObject ());
                numberOfTilesExplored = (Integer) (objectStream.readObject ());
                startTime = System.currentTimeMillis () - (Long) (objectStream.readObject ());
                stopTime = (Long) (objectStream.readObject ());
                gameState = (GameState) (objectStream.readObject ());
                flags = castObjectToArrayList (objectStream.readObject ());
                gameGrid = (int []) (objectStream.readObject ());
                explored = (boolean []) (objectStream.readObject ());

            } catch (Exception e) {

                return false;

            }

            return true;

        }

        return false;

    }
//=====================================================//

    /*
     * Saves a game to the given file.
     * return false if failed to save.
     */

    /**
     *
     * @param saveFile
     * @return
     */
    public boolean save (File saveFile) {

        String filename = saveFile.getName ();
        FileOutputStream outStream = null;
        BufferedOutputStream bufferedOutStream = null;
        ObjectOutputStream objectStream = null;


        if (filename.indexOf (".") == -1) {

            saveFile = new File (filename + "." + FILE_EXTENSION);
            filename = saveFile.getName ();

        }

        if (filename.substring (filename.indexOf (".")).equals ("." + FILE_EXTENSION)) {

            try {

                //this part was help for modeling from Darius Paulauskas

                outStream = new FileOutputStream (saveFile);
                bufferedOutStream = new BufferedOutputStream (outStream);
                objectStream = new ObjectOutputStream (bufferedOutStream);

                objectStream.writeObject (mineProbability);
                objectStream.writeObject (randomSeed);
                objectStream.writeObject (numberOfMines);
                objectStream.writeObject (squareLength);
                objectStream.writeObject (numberOfTilesExplored);
                objectStream.writeObject (System.currentTimeMillis() - startTime);
                objectStream.writeObject (stopTime);
                objectStream.writeObject (gameState);
                objectStream.writeObject (flags);
                objectStream.writeObject (gameGrid);
                objectStream.writeObject (explored);

                bufferedOutStream.flush ();

            } catch (Exception e) {

                return false;

            }

            return true;

        }

        return false;

    }

    //===============================================//

    /*
     * newGame
     */

    /**
     *
     * @param numberOfTiles
     * @param mineProbability
     * @param debugSeed
     */
    //this part from line  471 to 667 was modeled by Darius and logic , i was corrected what to code
    public void reset (int numberOfTiles, double mineProbability, long debugSeed) {

        numberOfMines = 0;
        numberOfTilesExplored = 0;
        gameState = GameState.PLAYING;
        stopTime = -1;
        startTime = 0;

        if (numberOfTiles < 1) {

            numberOfTiles = INTERMEDIATE_NUMBER_OF_TILES * INTERMEDIATE_NUMBER_OF_TILES;

        } else {

            numberOfTiles = numberOfTiles * numberOfTiles;

        }

        if (mineProbability < 0.0) {

            this.mineProbability = INTERMEDIATE_MINE_PROBABILITY;

        } else {

            this.mineProbability = mineProbability;

        }

        if (debugSeed == -1) {

            this.randomSeed = System.currentTimeMillis ();

        } else {

            this.randomSeed = debugSeed;

        }

        squareLength = (int)Math.sqrt (numberOfTiles);

        gameGrid = new int [numberOfTiles];
        explored = new boolean [numberOfTiles];
        propogateGameGrid ();

        flags = new ArrayList<Integer> (numberOfMines);

        startTime = System.currentTimeMillis ();

    }

    /*
     * Fills the game grid with a random number of mines and calculates the adjacent mines for each tile
     */

    private void propogateGameGrid () {

        Random rand = new Random (randomSeed);

        for (int i = 0; i < gameGrid.length; i++) {

            if (rand.nextDouble () <= mineProbability) {

                gameGrid[i] = MINE;
                numberOfMines++;

                updateAdjacent (i);

            }

        }

    }

    /*
     * Increases the adjacent mine count of nearby tiles
     */

    /**
     *
     * @param minePosition
     */
    private void updateAdjacent (int minePosition) {

        int adjacentIndex = 0;

        // Is the position given invalid?
        if (minePosition < 0 || minePosition >= gameGrid.length || gameGrid[minePosition] != MINE) {

            return;

        }

        // Adjust adjacent mine count of nearby tiles
        for (int j = -1; j <= 1; j++) {

            for (int k = -1; k <= 1; k++) {

                adjacentIndex = minePosition + (j * squareLength);

                // Avoid counting first and last tiles as adjacent when mine is a first or last element
                if ((adjacentIndex % squareLength == 0 && k == -1) || ((adjacentIndex + 1) % squareLength == 0 && k == 1)) {

                    continue;

                }

                adjacentIndex += k;

                // These conditions must be checked twice to avoid catching the end of a line when k = 1
                if (adjacentIndex >= 0 && adjacentIndex < gameGrid.length && gameGrid[adjacentIndex] != MINE) {

                    gameGrid[adjacentIndex]++;

                }

            }

        }

    }

    /**
     *
     * @param obj
     * @return
     */
    private ArrayList<Integer> castObjectToArrayList (Object obj) {

        if (obj instanceof ArrayList) {

            return (ArrayList<Integer>)(obj);

        }

        return null;

    }

    /*
     * Checks all adjacent tiles.
     */

    /**
     *
     * @param position
     */
    private void exploreAdjacent (int position) {

        int adjacentIndex = 0;

        // Is the position given invalid?
        if (position < 0 || position >= gameGrid.length) {

            return;

        }

        // Adjust adjacent mine count of nearby tiles
        for (int j = -1; j <= 1; j++) {

            for (int k = -1; k <= 1; k++) {

                adjacentIndex = position + (j * squareLength);

                // Avoid counting first and last tiles as adjacent when mine is a first or last element
                if ((adjacentIndex % squareLength == 0 && k == -1) || ((adjacentIndex + 1) % squareLength == 0 && k == 1)) {

                    continue;

                }

                adjacentIndex += k;

                // These conditions must be checked twice to avoid catching the end of a line when k = 1
                if (adjacentIndex >= 0 && adjacentIndex < gameGrid.length) {

                    exploreTile (adjacentIndex);

                }

            }

        }

    }

    /*
     * Explores all tiles when the game is over or won.
     */

    private void revealAll () {

        for (int i = 0; i < explored.length; i++) {

            explored[i] = true;

        }

    }
}