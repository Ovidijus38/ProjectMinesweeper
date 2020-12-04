import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MinesweeperPanel extends JPanel {

    // This part ideas from your lab - sheets and some inspiration from www.stackoverflow.com
    // under supervision for modeling Darius Paulauskas

    /**
     *
     * @param
     */

    // Variables ***********************************************************************
    private MinesweeperFrame frame;
    private JPanel gridPanel;
    private JLabel mineLabel;
    private JLabel statusLabel;
    private int mineCount;
    private String status;
    private Tile [] tiles;

    // Methods *************************************************************************

    public MinesweeperPanel (MinesweeperFrame parent) {

        frame = parent;
        tiles = null;
        gridPanel = null;

        mineLabel = new JLabel ("Mines: "+ Integer.toString (mineCount));
        statusLabel = new JLabel ("Careful!", SwingConstants.CENTER);

        setLayout (new BorderLayout ());

        add (mineLabel, BorderLayout.SOUTH);
        add (statusLabel, BorderLayout.NORTH);

        reset ();
    }
    //Resets the panel

    public void reset () {

        int numberOfTiles = 0;

        if (tiles != null) {

            for (int i = 0; i < numberOfTiles; i++) {

                gridPanel.remove (tiles[i]);

            }
        }

        if (gridPanel != null) {

            remove (gridPanel);

        }

        numberOfTiles = (int)Math.pow (frame.getGame ().getSquareLength (), 2.0);
        gridPanel = new JPanel (new GridLayout (frame.getGame ().getSquareLength (), frame.getGame ().getSquareLength ()));

        tiles = new Tile [numberOfTiles];

        for (int i = 0; i < numberOfTiles; i++) {

            tiles[i] = new Tile (i, this);
            tiles[i].addMouseListener (new MouseHandler ());
            gridPanel.add (tiles[i]);

        }

        mineCount = getGame ().getEstimatedNumberOfMines ();

        status = "Careful!";

        add (gridPanel, BorderLayout.CENTER);

        repaint ();
    }

    public void paintComponent (Graphics g) {

        super.paintComponent (g);

        mineLabel.setText ("Mines: "+ Integer.toString (mineCount));
        statusLabel.setText (status);

    }
    public MinesweeperGame getGame () {

        return frame.getGame ();

    }
      //Handles button presses for the game.

    private class MouseHandler extends MouseAdapter {


        public void mousePressed (MouseEvent e) {

            if (e.getButton () == MouseEvent.BUTTON3) {

                getGame ().flagTile (((Tile)(e.getSource ())).getTileIndex ());

            } else if (e.getButton () == MouseEvent.BUTTON1) {

                getGame ().exploreTile (((Tile)(e.getSource ())).getTileIndex ());

            }

            switch (getGame ().getGameState ()) {

                case PLAYING:

                    mineCount = getGame ().getEstimatedNumberOfMines ();
                    break;

                case GAME_OVER:

                    mineCount = getGame ().getNumberOfMines ();
                    status = "You Lost In A Mere " + Float.toString (getGame ().getFinalTime ()) + " Seconds!";
                    break;

                case WON:

                    mineCount = getGame ().getNumberOfMines ();
                    status = "You Won In A Mere " + Float.toString (getGame ().getFinalTime ()) + " Seconds!";
                    break;

            }

            repaint ();

        }

    }

}