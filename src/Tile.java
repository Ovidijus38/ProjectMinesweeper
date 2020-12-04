import javax.swing.*;
import java.awt.*;

/**
 * This is instantiable class
 * creates object Tile and Button
 */

public class Tile extends JButton {

    /* modeling done with help from Darius Paulauskas
    *  ideas from Your lab sheets and www.stacoverflow.com forums*/

    // Variables ***********************************************************************
    private int tileIndex;
    private int state;
    private MinesweeperPanel panel;
    private ImageIcon mineImage;
    private ImageIcon flagImage;
    private ImageIcon unexImage;

    private static final String MINE_IMG_PATH = "src\\resources\\redmine.png";
    private static final String FLAG_IMG_PATH = "src\\resources\\flag.png";
    private static final String UNEX_IMG_PATH = "src\\resources\\tile.png";

    // Methods ************************************************************************

    /**
     * This two argument constructor creates Tile and puts images icons
     *
     * @param tileIndex
     * @param p
     */
    public Tile (int tileIndex, MinesweeperPanel p) {

        super ("");
        this.tileIndex = tileIndex;
        this.panel = p;
        state = p.getGame ().getStateOf (this.tileIndex);
        setEnabled (true);
        mineImage = new ImageIcon (Toolkit.getDefaultToolkit ().getImage (MINE_IMG_PATH));
        flagImage = new ImageIcon (Toolkit.getDefaultToolkit ().getImage (FLAG_IMG_PATH));
        unexImage = new ImageIcon (Toolkit.getDefaultToolkit ().getImage (UNEX_IMG_PATH));

    }

    /**
     * This method get a Tile index
     * @return
     */

    public int getTileIndex () {

        return tileIndex;

    }
// This partialy was done with help from Darius Paulauskas

    /**
     * This method makes panel with items
     * @param g
     */

    public void paintComponent (Graphics g) {

        super.paintComponent (g);

        state = panel.getGame ().getStateOf (this.tileIndex);

        switch (state) {

            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                
                setEnabled (false);
                setIcon (null);
                setDisabledIcon (null);
                if (state == 0) {

                    setText (" ");

                } else {

                    setText (Integer.toString (state));

                }
                break;
//==========================================//
            case MinesweeperGame.MINE:

                setEnabled (false);
                setText ("");
                setIcon (mineImage);
                setDisabledIcon (mineImage);
                break;

            case MinesweeperGame.FLAGGED:

                setText ("");
                setIcon (flagImage);
                break;

            case MinesweeperGame.UNEXPLORED:

                setText ("");
                setIcon (unexImage);
                setDisabledIcon (null);
                break;

        }

    }

}
