package maze.game;

import maze.database.data.User;

public class Competitor
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

    @Override
    public String toString()
    {
        return user.toString();
    }
}
