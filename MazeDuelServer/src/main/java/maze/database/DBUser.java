package maze.database;

import maze.database.data.MainData;
import maze.database.data.User;
import net.ucanaccess.console.Main;

import java.sql.ResultSet;
import java.util.ArrayList;

public class DBUser
{
    public static ArrayList<User> getUsersFromDB()
    {
        ResultSet result = AccessConnection.selectCommand("select ID, userName, passwordHash from User;");
        return getUsersFromResult(result);
    }

    public static ArrayList<User> getUsersFromResult(ResultSet result)
    {
        try
        {
            ArrayList<User> users = new ArrayList<>();
            result.first();
            if(result.getRow() == 1)
            {
                users.add(getUserFromCurrentResult(result));
                while(result.next())
                {
                    users.add(getUserFromCurrentResult(result));
                }
            }
            return users;
        }
        catch (Exception e)
        {
            System.err.println("Error in DBUser.getUserFromResult.\n" + e);
        }
        return null;
    }

    public static User getUserFromCurrentResult(ResultSet result)
    {
        try
        {
            return new User(result.getInt(1), result.getString(2), result.getInt(3));
        }
        catch (Exception e)
        {
            System.err.println("Error in DBUser.getUserFromCurrentResult.\n" + e);
        }
        return null;
    }

    public static boolean addUserToDB(String userName, int passwordHash)
    {
        if(DBUser.getUserByUserName(userName) == null)
        {
            try
            {
                AccessConnection.modifyCommand("insert into User (userName, passwordHash) values(?, ?);", new Object[]{userName, passwordHash});
                ResultSet result = AccessConnection.selectCommand("select max(ID) from User;");
                result.first();
                MainData.getInstance().getUsers().add(new User(result.getInt(1), userName, passwordHash));
                return true;
            }
            catch (Exception e)
            {
                System.err.println("Error in DBUser.addUserToDB.");
                e.printStackTrace();
            }
        }
        return false;
    }

    public static User getUserByUserName(String userName)
    {
        for(User user : MainData.getInstance().getUsers())
        {
            if(user.getUserName().equals(userName))
            {
                return user;
            }
        }
        return null;
    }

    public static int checkLoginData(String userName, int passwordHash)
    {
        User user = getUserByUserName(userName);
        if(user == null)
        {
            //User not found
            return 0;
        }
        if(user.getPasswordHash() == passwordHash)
        {
            //Login data correct
            return 1;
        }
        //Password incorrect
        return -1;
    }
}
