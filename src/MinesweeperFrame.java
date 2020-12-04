import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MinesweeperFrame extends JFrame implements ActionListener {

    // This part ideas from your lab - sheets and some inspiration from www.stackoverflow.com
    // under supervision for modeling Darius Paulauskas

    /**
     *
     * @param
     */

    // Variables ***********************************************************************
    private MinesweeperGame game;
    private MinesweeperPanel panel;

    private JMenuBar menuBar;
    private JMenuItem newMenuItem;
    private JMenuItem loadMenuItem;
    private JMenuItem saveMenuItem;
    private JMenuItem quitMenuItem;

    private JFileChooser fileBrowser;

    private static final String NEW_ACTION = "New";
    private static final String SAVE_ACTION = "Save";
    private static final String LOAD_ACTION = "Load";
    private static final String QUIT_ACTION = "Quit";

    // Methods *************************************************************************

    public MinesweeperFrame (int numberOfTiles, double mineProbability, long debugSeed) {

        setTitle ("Minesweeper 2020 Mini Project");
        setSize (800, 800);
        setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);

        game = new MinesweeperGame (numberOfTiles, mineProbability, debugSeed);

        fileBrowser = new JFileChooser ();
        FileNameExtensionFilter filter = new FileNameExtensionFilter ("Minesweeper Game (.msg)", game.getFileExtension ());
        fileBrowser.setFileFilter (filter);
        fileBrowser.setFileSelectionMode (JFileChooser.FILES_ONLY );

        buildMenuBar ();

        panel = new MinesweeperPanel (this);
        add (panel);
        setVisible (true);
    }

    public MinesweeperGame getGame () {

        return game;
    }
//This modeling was done under supervision of Dariud Paulauskas
    public void actionPerformed (ActionEvent e) {

        switch (e.getActionCommand ()) {

            case NEW_ACTION:
                newGame ();
                break;

            case LOAD_ACTION:
                loadGame ();
                break;

            case SAVE_ACTION:
                saveGame ();
                break;

            case QUIT_ACTION:
                System.exit (0);
                break;
        }

        panel.reset ();
        invalidate ();
        validate ();
        repaint ();
    }

    private void loadGame () {

        int fileChooserReturnValue = fileBrowser.showOpenDialog (this);
        File saveFile = null;
        String filename = null;

        if (fileChooserReturnValue == JFileChooser.APPROVE_OPTION) {

            saveFile = fileBrowser.getSelectedFile ();
            filename = saveFile.getName ();

            if (filename.indexOf (".") == -1) {

                saveFile = new File (filename + "." + MinesweeperGame.getFileExtension ());
                filename = saveFile.getName ();
            }

            if (!game.load (saveFile)) {

                JOptionPane.showMessageDialog (this,"Could not load game.");
            }
        }
    }

     //Asks the player where to save and saves the game.

    private void saveGame () {

        int fileChooserReturnValue = fileBrowser.showSaveDialog (this);
        File saveFile = null;
        String filename = null;

        if (fileChooserReturnValue == JFileChooser.APPROVE_OPTION) {

            saveFile = fileBrowser.getSelectedFile ();
            filename = saveFile.getName ();

            if (filename.indexOf (".") == -1) {

                saveFile = new File (filename + "." + MinesweeperGame.getFileExtension ());
                filename = saveFile.getName ();

            }
            if (!game.save (saveFile)) {

                JOptionPane.showMessageDialog (this,"Could not save to file.");

            }
        }
    }

     //Asks the player for a difficulty and starts a new game.

    private void newGame () {

        String difficulty = (String) (JOptionPane.showInputDialog (this, "Difficulty:", "New Game", JOptionPane.QUESTION_MESSAGE,
                null, new String [] {"Easy", "Intermediate", "Expert"}, "Intermediate"));

        if (difficulty != null) {

            if (difficulty.equals ("Easy")) {

                game.newGame (MinesweeperGame.Difficulty.EASY);

            } else if (difficulty.equals ("Expert")) {

                game.newGame (MinesweeperGame.Difficulty.EXPERT);

            } else {

                game.newGame (MinesweeperGame.Difficulty.INTERMEDIATE);
            }

        } else {

            return;
        }
    }

    private void buildMenuBar () {

        // MenuBar
        menuBar = new JMenuBar ();

        // New *************************************************************************
        newMenuItem = new JMenuItem ("New");
        newMenuItem.setActionCommand (NEW_ACTION);
        newMenuItem.addActionListener (this);
        menuBar.add (newMenuItem);

        // Load ************************************************************************
        loadMenuItem = new JMenuItem ("Load");
        loadMenuItem.setActionCommand (LOAD_ACTION);
        loadMenuItem.addActionListener (this);
        menuBar.add (loadMenuItem);

        // Save ***********************************************************************
        saveMenuItem = new JMenuItem ("Save");
        saveMenuItem.setActionCommand (SAVE_ACTION);
        saveMenuItem.addActionListener (this);
        menuBar.add (saveMenuItem);

        // Quit ************************************************************************
        quitMenuItem = new JMenuItem ("Quit");
        quitMenuItem.setActionCommand (QUIT_ACTION);
        quitMenuItem.addActionListener (this);
        menuBar.add (quitMenuItem);

        // Show the menu
        setJMenuBar (menuBar);

    }

}