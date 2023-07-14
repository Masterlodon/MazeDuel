package maze.database;

import java.util.ArrayList;
import java.util.Collection;

public class DBFunctions
{
    public static void commit()
    {
        AccessConnection.commit();
    }

    public static void addDistinct(Collection collection0, Collection collection1)
    {
        ArrayList<Object> collectionAdd = new ArrayList<>();

        for(Object object1 : collection1)
        {
            boolean distinct = true;
            for(Object object0 : collection0)
            {
                if(object0 == object1)
                {
                    distinct = false;
                    break;
                }
            }
            if(distinct == true)
            {
                collectionAdd.add(object1);
            }
        }
        collection0.addAll(collectionAdd);
    }
}
