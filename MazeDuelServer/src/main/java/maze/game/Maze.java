package maze.game;

public class Maze
{
    private int width;
    private int height;
    private Cell[][] cells;

    public Maze(int width, int height)
    {
        this.height = height;
        this.width = width;
        initCells();
    }

    private void initCells()
    {
        cells = new Cell[width][height];
        for(Cell[] layer : cells)
        {
            for(Cell cell : layer)
            {
                cell = new Cell("path");
            }
        }
    }

    public void renderEdit()
    {

    }

    public void renderInGame()
    {

    }

    public void renderSpectate()
    {

    }
}
