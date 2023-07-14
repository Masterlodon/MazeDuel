package maze.game;

import java.io.Serializable;

public class Cell implements Serializable
{
    private String element;
    private boolean visible;

    public Cell(String element)
    {
        this.element = element;
        visible = false;
    }

    public Cell(String element, boolean visible)
    {
        this.element = element;
        this.visible = visible;
    }

    public String getElement()
    {
        return element;
    }

    public void setElement(String element)
    {
        this.element = element;
    }

    public boolean isVisible()
    {
        return visible;
    }

    public void setVisible(boolean visible)
    {
        this.visible = visible;
    }
}
