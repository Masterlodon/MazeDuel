package jsonHandler;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

//Follow these rules when using the JsonHandler:

//Implement the JsonPrivateAccessor in your class, if you want private fields to be serialized.
//It is not required to implement Serializable in your class when using this JsonHandler.
//Add the @JsonIgnore annotation above fields you don't want to be serialized.
//Every class that should be serialized needs to have an empty constructor.

public class JsonHandler
{
    private static ObjectMapper objectMapper;

    private static ObjectMapper getObjectMapper()
    {
        if(objectMapper == null)
        {
            objectMapper = new ObjectMapper();
            objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        }
        return objectMapper;
    }

    private static JavaType createListType(Class type)
    {
        return objectMapper.getTypeFactory().constructCollectionType(List.class, type);
    }

    private static  JavaType createMapType(Class key, Class type)
    {
        return objectMapper.getTypeFactory().constructMapType(Map.class, key, type);
    }

    //write to file
    public static void writeToFile(String path,  Object object) throws IOException
    {
        getObjectMapper().writeValue(new File(path), object);
    }

    //serialize
    public static String serializeToString(Object object) throws JsonProcessingException
    {
        return getObjectMapper().writeValueAsString(object);
    }

    public static byte[] serializeToByteArray(Object object) throws JsonProcessingException
    {
        return getObjectMapper().writeValueAsBytes(object);
    }

    //read from file
    public static Object readFromFileToObject(String path, Class type) throws IOException
    {
        return getObjectMapper().readValue(new File(path), type);
    }

    public static List<Object> readFromFileToCollection(String path, Class type) throws IOException
    {
        return Collections.singletonList(getObjectMapper().readValue(new File(path), createListType(type)));
    }

    public static Map<Object, Object> readFromFileToCollection(String path, Class key, Class type) throws IOException
    {
        return getObjectMapper().readValue(new File(path), createMapType(key, type));
    }

    //deserialize to object/collection
    public static Object deserializeToObject(String value, Class type) throws JsonProcessingException
    {
        return getObjectMapper().readValue(value, type);
    }

    public static Object deserializeToObject(byte[] value, Class type) throws IOException
    {
        return getObjectMapper().readValue(value, type);
    }

    public static List<Object> deserializeToCollection(String value, Class type) throws IOException
    {
        return Collections.singletonList(getObjectMapper().readValue(value, createListType(type)));
    }

    public static Map<Object, Object> deserializeToCollection(String value, Class key, Class type) throws IOException
    {
        return getObjectMapper().readValue(value, createMapType(key, type));
    }

    public static List<Object> deserializeToCollection(byte[] value, Class type) throws IOException
    {
        return Collections.singletonList(getObjectMapper().readValue(value, createListType(type)));
    }

    public static Map<Object, Object> deserializeToCollection(byte[] value, Class key, Class type) throws IOException
    {
        return getObjectMapper().readValue(value, createMapType(key, type));
    }
}