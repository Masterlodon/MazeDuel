package maze.game;

import maze.database.data.User;

import java.io.Serializable;

public class Competitor implements Serializable
{
    private User user;
    private Maze maze;

    public Competitor(User user, int width, int height)
    {
        this.user = user;
        this.maze = new Maze(width, height);
    }

    public User getUser()
    {
        return user;
    }

    public Maze getMaze()
    {
        return maze;
    }

    public int getWidth()
    {
        return maze.getWidth();
    }

    public int getHeight()
    {
        return maze.getHeight();
    }

    @Override
    public String toString()
    {
        return user.toString();
    }
}
