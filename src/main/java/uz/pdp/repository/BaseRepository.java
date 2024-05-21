package uz.pdp.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;
import uz.pdp.model.BaseModel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public abstract class BaseRepository<T extends BaseModel> {

    protected String path;

    protected Class<T> type;

    public static ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);


    public void save(T t) {
        ArrayList<T> list = readValue();
        list.add(t);
        writeValue(list);
    }

    public ArrayList<T> readValue() {

        try {
            return objectMapper.readValue(new File(path), TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, type));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void writeValue(ArrayList<T> data) {

        try {
            objectMapper.writeValue(new File(path), data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }






    public ArrayList<T> getAll() {
        return readValue();
    }

}
