package maze.game;

import maze.database.data.User;

import java.util.ArrayList;

public class Game
{
    private int width;
    private int height;
    private ArrayList<Competitor> competitors;
    private boolean publicGame;

    public Game(ArrayList<User> users, int width, int height, boolean publicGame)
    {
        competitors = new ArrayList<>();
        for(User user : users)
        {
            competitors.add(new Competitor(user, width, height));
        }
        this.width = width;
        this.height = height;
        this.publicGame = publicGame;
    }

    public Game(User user, int width, int height, boolean publicGame)
    {
        competitors = new ArrayList<>();
        competitors.add(new Competitor(user, width, height));
        this.width = width;
        this.height = height;
        this.publicGame = publicGame;
    }

    public ArrayList<Competitor> getCompetitors()
    {
        return competitors;
    }

    public void addCompetitor(Competitor competitor)
    {
        competitors.add(competitor);
    }

    public void removeCompetitor(User user)
    {
        for(Competitor competitor : competitors)
        {
            if(competitor.getUser() == user)
            {
                competitors.remove(competitor);
            }
        }
    }

    public boolean isPublicGame()
    {
        return publicGame;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    @Override
    public String toString()
    {
        String string = "Width: " + width + ", Height: " + height + ", Public: " + publicGame + "\n";
        for(Competitor competitor : competitors)
        {
            string += competitor.toString() + "\n";
        }
        return string;
    }
}
