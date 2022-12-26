package pl.kurs.geometricfigures.service;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import pl.kurs.geometricfigures.exceptions.BadEntityException;
import pl.kurs.geometricfigures.exceptions.BadIdException;
import pl.kurs.geometricfigures.exceptions.SpecificEntityNotFoundException;


import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class GenericManagementService<T extends Identificationable, R extends JpaRepository<T, Long>> implements IManagementService<T> {

    protected R repository;

    public GenericManagementService(R repository) {
        this.repository = repository;
    }

    @Override
    public T add(T entity) {
        return repository.save(
                Optional.ofNullable(entity)
                        .filter(x -> x.getId() == null)
                        .orElseThrow(() -> new BadEntityException(entity))
        );
    }

    @Override
    public void delete(long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new BadIdException("Id " + id + " not found for delete!", e, id);
        }
    }

    @Override
    @Transactional
    public T edit(T entity) {

        T loadedEntity = repository.findById(
                Optional.ofNullable(entity.getId())
                        .filter(Objects::nonNull)
                        .orElseThrow(() -> new BadEntityException(entity))
        ).orElseThrow(() -> new BadEntityException(entity));

        loadedEntity = entity;
        return loadedEntity;

    }

    @Override
    public T get(long id) {
        return repository.findById(id).orElseThrow(() -> new SpecificEntityNotFoundException(id));
    }

    @Override
    public List<T> getAll() {
        return repository.findAll();
    }

}
