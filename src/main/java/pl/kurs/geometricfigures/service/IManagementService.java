package pl.kurs.geometricfigures.service;

import java.util.List;

public interface IManagementService<T> {

    T add(T entity);
    void delete(long id);
    T edit(T entity);
    T get(long id);
    List<T> getAll();
}
