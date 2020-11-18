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
    public void createEmptyCells()
    {
        for (int x = 0; x < cols; x++)
        {
            for (int y = 0; y < rows; y++)
            {
                cells[x][y] = new Cell();
            }
        }
    }
    public void setMines()
    {
        int x,y;
        boolean hasMine;
        int currentMines = 0;

        while (currentMines != numberOfMines)
        {
            // Generate a random x coordinate (between 0 and cols)
            x = (int)Math.floor(Math.random() * cols);

            // Generate a random y coordinate (between 0 and rows)
            y = (int)Math.floor(Math.random() * rows);

            hasMine = cells[x][y].getMine();

            if(!hasMine)
            {
                cells[x][y].setMine(true);
                currentMines++;
            }
        }
    }
    public void setSurroundingMinesNumber()
    {
        for(int x = 0 ; x < cols ; x++)
        {
            for(int y = 0 ; y < rows ; y++)
            {
                cells[x][y].setSurroundingMines(calculateNeighbours(x,y));
            }
        }
    }

    //Got help from Darius Paulauskas for modeling this part

    public int calculateNeighbours(int xCo, int yCo)
    {
        int neighbours = 0;

        // Check the neighbours (the columns xCo - 1, xCo, xCo + 1)
        for(int x=makeValidCoordinateX(xCo - 1); x<=makeValidCoordinateX(xCo + 1); x++)
        {
            // Check the neighbours (the rows yCo - 1, yCo, yCo + 1).
            for(int y=makeValidCoordinateY(yCo - 1); y<=makeValidCoordinateY(yCo + 1); y++)
            {
                // Skip (xCo, yCo), since that's no neighbour.
                if(x != xCo || y != yCo)
                    if(cells[x][y].getMine())   // If the neighbour contains a mine, neighbours++.
                        neighbours++;
            }
        }

        return neighbours;
    }
    public int makeValidCoordinateX(int i)
    {
        if (i < 0)
            i = 0;
        else if (i > cols-1)
            i = cols-1;

        return i;
    }
    public int makeValidCoordinateY(int i)
    {
        if (i < 0)
            i = 0;
        else if (i > rows-1)
            i = rows-1;

        return i;
    }
}
