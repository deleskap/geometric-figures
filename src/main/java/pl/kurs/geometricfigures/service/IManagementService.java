package pl.kurs.geometricfigures.service;

import java.util.List;
import java.util.Map;

public interface IManagementService<T> {

    T add(T entity);
    void delete(long id);
    T edit(T entity);
    T get(long id);
    List<T> getAll();
    String getType();
    List<T> getAllByParameters(Map<String, Object> parameters);

}
