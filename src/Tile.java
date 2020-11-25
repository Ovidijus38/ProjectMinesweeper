import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Tile extends JButton {

    // Variables ***********************************************************************
    private int tileIndex;
    private int state;
    private MinesweeperPanel panel;
    private ImageIcon mineImage;
    private ImageIcon flagImage;

    private static final String MINE_IMG_PATH = "D:\\ProjectMinesweeper\\src\\resources\\mine.png";
    private static final String FLAG_IMG_PATH = "D:\\ProjectMinesweeper\\src\\resources\\flag.png";

    // Methods *************************************************************************

    public Tile (int tileIndex, MinesweeperPanel p) {

        super ("");
        this.tileIndex = tileIndex;
        this.panel = p;
        state = p.getGame ().getStateOf (this.tileIndex);
        setEnabled (true);
        mineImage = new ImageIcon (Toolkit.getDefaultToolkit ().getImage (MINE_IMG_PATH));
        flagImage = new ImageIcon (Toolkit.getDefaultToolkit ().getImage (FLAG_IMG_PATH));

    }

    public int getTileIndex () {

        return tileIndex;

    }

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

                setText ("@");
                setIcon (null);
                setDisabledIcon (null);
                break;

        }

    }

}
