package maze.game;

import maze.database.data.User;

import java.io.Serializable;
import java.util.ArrayList;

public class Game implements Serializable
{
    private int width;
    private int height;
    private ArrayList<Competitor> competitors;
    private transient Competitor player;
    private transient Competitor host;
    private int hostId;
    private boolean publicGame;

    public Game(ArrayList<User> users, int width, int height, boolean publicGame)
    {
        competitors = new ArrayList<>();
        for(User user : users)
        {
            competitors.add(new Competitor(user, width, height));
        }
        if(competitors.size() >= 1)
        {
            host = competitors.get(0);
            hostId = host.getUser().getId();
        }
        this.width = width;
        this.height = height;
        this.publicGame = publicGame;
    }

    public Game(User user, int width, int height, boolean publicGame)
    {
        host = new Competitor(user, width, height);
        hostId = host.getUser().getId();
        competitors = new ArrayList<>();
        competitors.add(host);
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
        ArrayList<Competitor> removed = new ArrayList<>();
        for(Competitor competitor : competitors)
        {
            if(competitor.getUser() == user)
            {
                removed.add(competitor);
            }
        }
        for(Competitor competitor : removed)
        {
            competitors.remove(competitor);
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

    public Competitor getHost()
    {
        return host;
    }

    public Competitor getPlayer()
    {
        return player;
    }

    public void fillReferences(User user)
    {
        fillHostReference();
        fillPlayerReference(user);
    }

    public void fillHostReference()
    {
        for(Competitor competitor : competitors)
        {
            if(competitor.getUser().getId() == hostId)
            {
                host = competitor;
            }
        }
    }

    public void fillPlayerReference(User user)
    {
        for(Competitor competitor : competitors)
        {
            if(competitor.getUser().getId() == user.getId())
            {
                player = competitor;
            }
        }
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
