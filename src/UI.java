import javax.swing.*;
import java.awt.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;


public class UI extends JFrame {
    // The buttons
    private JButton[][] buttons;

    // Number of Buttons in Grid
    private int rows;
    private int cols;

    // Labels
    private JLabel minesLabel;
    private int mines;

    private JLabel timePassedLabel;
    private Thread timer;
    private int timePassed;
    private boolean stopTimer;

    // Frame settings
    private final String FRAME_TITLE = "Minesweeper";

    private int FRAME_WIDTH = 520;
    private int FRAME_HEIGHT = 550;
    private int FRAME_LOC_X = 430;
    private int FRAME_LOC_Y = 50;

    // Icons
    private Icon redMine;
    private Icon mine;
    private Icon flag;
    private Icon tile;

    // Menu Bar and Items

    private JMenuBar menuBar;
    private JMenu gameMenu;
    private JMenuItem newGame;
    private JMenuItem statistics;
    private JMenuItem exit;
}

