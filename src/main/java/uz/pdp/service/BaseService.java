package uz.pdp.service;

import uz.pdp.model.BaseModel;
import uz.pdp.repository.BaseRepository;
import java.util.ArrayList;

public class BaseService<T extends BaseModel, R extends BaseRepository<T>> {

    protected R repository;

    public BaseService(R repository) {
        this.repository = repository;
    }


    public ArrayList<T> getAll() {
        return repository.getAll();
    }

    public void add(T t) {
        repository.save(t);
    }
}
