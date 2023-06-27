package maze.database;

import java.sql.*;

public class AccessConnection
{
    private static AccessConnection instance;
    private Connection connection;
    private String path = "src/main/resources/maze/database";
    private String fileName = "MazeData.accdb";
    private String identifier = "";
    private String password = "";

    private AccessConnection()
    {
        try
        {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
        }
        catch (ClassNotFoundException e)
        {
            System.err.println("Error in AccessConnection Constructor.");
            e.printStackTrace();
        }
        this.connect();
    }

    public Connection getConnection()
    {
        return connection;
    }

    private void connect()
    {
        try
        {
            connection = DriverManager.getConnection(generateUrl(), identifier, password);
            connection.setAutoCommit(false);
        }
        catch (SQLException e)
        {
            System.err.println("Error in accessConnection.connect.");
            e.printStackTrace();
        }
    }

    public static void holdConnection()
    {
        if(getInstance().connection == null)
        {
            getInstance().connect();
        }
    }

    public static AccessConnection getInstance()
    {
        if(instance == null)
        {
            instance = new AccessConnection();
        }
        return instance;
    }

    private Statement getStatement()
    {
        try
        {
            return connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        }
        catch(Exception e)
        {
            System.err.println("Error in accessConnection.getStatement.");
            e.printStackTrace();
            return null;
        }
    }

    private PreparedStatement getPreparedStatement(String command, Object[] values)
    {
        try
        {
            PreparedStatement statement = connection.prepareStatement(command, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            for(int i = 0; i < values.length; i++)
            {
                if(values[i] instanceof String value)
                {
                    statement.setString(i + 1, value);
                }
                else if(values[i] instanceof Integer value)
                {
                    statement.setInt(i + 1, value);
                }
                else if(values[i] instanceof Float value)
                {
                    statement.setFloat(i + 1, value);
                }
                else if(values[i] instanceof Double value)
                {
                    statement.setDouble(i + 1, value);
                }
                else if(values[i] instanceof Short value)
                {
                    statement.setShort(i + 1, value);
                }
                else if(values[i] instanceof Long value)
                {
                    statement.setLong(i + 1, value);
                }
                else if(values[i] instanceof Boolean value)
                {
                    statement.setBoolean(i + 1, value);
                }
                else if(values[i] instanceof Character value)
                {
                    statement.setString(i + 1, String.valueOf(value));
                }
                else
                {
                    statement.setObject(i + 1, values[i]);
                }
            }
            return statement;
        }
        catch(Exception e)
        {
            System.err.println("Error in accessConnection.getPreparedStatement.");
            e.printStackTrace();
            return null;
        }
    }

    public static void closeConnection(boolean commit)
    {
        try
        {
            if(getInstance().connection != null)
            {
                if(commit)
                {
                    getInstance().connection.commit();
                }
                getInstance().connection.close();
                getInstance().connection = null;
            }
        }
        catch(Exception e)
        {
            System.err.println("Error in AccessConnection.closeConnection.");
            e.printStackTrace();
        }
    }

    public static void commit()
    {
        try
        {
            getInstance().connection.commit();
        }
        catch (Exception e)
        {
            System.err.println("Error in AccessConnection.commit.");
            e.printStackTrace();
        }
    }

    private String generateUrl()
    {
        return "jdbc:ucanaccess://" + path + "/" + fileName;
    }

    public static ResultSet selectCommand(String command, Object[] values)
    {
        try
        {
            return getInstance().getPreparedStatement(command, values).executeQuery();
        }
        catch (Exception e)
        {
            System.err.println("Error in AccessConnection.selectCommand.");
            e.printStackTrace();
        }
        return null;
    }

    public static ResultSet selectCommand(String command)
    {
        try
        {
            return getInstance().getStatement().executeQuery(command);
        }
        catch (Exception e)
        {
            System.err.println("Error in AccessConnection.selectCommand.");
            e.printStackTrace();
        }
        return null;
    }

    public static void modifyCommand(String command, Object[] values)
    {
        try
        {
            getInstance().getPreparedStatement(command, values).executeUpdate();
        }
        catch (Exception e)
        {
            System.err.println("Error in AccessConnection.modifyCommand.");
            e.printStackTrace();
        }
    }

    public static void modifyCommand(String command)
    {
        try
        {
            getInstance().getStatement().executeUpdate(command);
        }
        catch (Exception e)
        {
            System.err.println("Error in AccessConnection.modifyCommand.");
            e.printStackTrace();
        }
    }

    public static  void displayResultSet(ResultSet result)
    {
        try
        {
            int columnsNumber = result.getMetaData().getColumnCount();
            while (result.next())
            {
                for (int i = 1; i <= columnsNumber; i++)
                {
                    System.out.print(result.getMetaData().getColumnName(i) + " = " +result.getString(i) + ", ");
                }
                System.out.println("");
            }
        }
        catch (Exception e)
        {
            System.err.println("Error in AccessConnection.displayResultSet.");
            e.printStackTrace();
        }

    }
}
