package maze.game;

import gui.mazeduelclient.Application;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.io.Serializable;

public class Maze implements Serializable
{
    private static final long serialVersionUID = "Maze".hashCode();
    private static final double sizeX = 350;
    private static final double sizeY = 350;
    private static final double anchorX = 136;
    private static final double anchorY = 45;
    private transient double gridWidth;
    private transient double gridHeight;
    private int width;
    private int height;
    private Cell[][] cells;

    public Maze(int width, int height)
    {
        this.width = width;
        this.height = height;
        if(width > height)
        {
            gridWidth = 1;
            gridHeight = Double.valueOf(height) / Double.valueOf(width);
        }
        else if(height > width)
        {
            gridHeight = 1;
            gridWidth = Double.valueOf(width) / Double.valueOf(height);
        }
        else
        {
            gridWidth = 1;
            gridHeight = 1;
        }
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

    public void adjustGridPane(GridPane gridPane)
    {
        try
        {
            double withPercentage = 100 / width;
            double heightPercentage = 100 / height;
            //Width
            gridPane.setPrefWidth(gridWidth * sizeX);
            gridPane.getColumnConstraints().clear();
            for(int i = 0; i < width; i++)
            {
                ColumnConstraints column = new ColumnConstraints();
                column.setPercentWidth(withPercentage);
                gridPane.getColumnConstraints().add(column);
            }
            //Height
            gridPane.setPrefHeight(gridHeight * sizeY);
            gridPane.getRowConstraints().clear();
            for(int i = 0; i < height; i++)
            {
                RowConstraints row = new RowConstraints();
                row.setPercentHeight(heightPercentage);
                gridPane.getRowConstraints().add(row);
            }
            //layout
            double layoutX = anchorX + (sizeX * 0.5) - (gridWidth * sizeX * 0.5);
            double layoutY = anchorY + (sizeY * 0.5) - (gridHeight * sizeY * 0.5);
            System.out.println("LayoutX: " + layoutX + ", LayoutY: " + layoutY);
            //gridPane.setLayoutX(layoutX);
            //gridPane.setLayoutX(layoutY);
        }
        catch (Exception e)
        {
            System.err.println("Error in maze.adjustGridPane.");
            e.printStackTrace();
        }
    }

    public void renderEdit(GridPane gridPane)
    {

    }

    public void renderInGame()
    {

    }

    public void renderSpectate()
    {

    }
}
