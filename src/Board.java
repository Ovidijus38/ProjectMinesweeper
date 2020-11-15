public class Board {
    private int numberOfMines;
    private Cell cells[][];

    private int rows;
    private int cols;


    //---------------------------------------------//

    public Board(int numberOfMines, int r, int c) {
        this.rows = r;
        this.cols = c;
        this.numberOfMines = numberOfMines;

        cells = new Cell[rows][cols];

        //Step 1: First create a board with empty Cells
        //Method
        createEmptyCells();

        //Step 2: Then set mines randomly at cells
        //Method
        setMines();

        //Step 3: Then set the number of surrounding mines("neighbours") at each cell
        //Method
        setSurroundingMinesNumber();
    }
}
