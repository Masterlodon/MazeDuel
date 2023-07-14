package maze.game;

import java.io.Serializable;

public class Maze implements Serializable
{
    private static final long serialVersionUID = "Maze".hashCode();
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

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public Cell[][] getCells()
    {
        return cells;
    }
}
