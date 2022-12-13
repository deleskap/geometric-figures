package pl.kurs.geometricfigures.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

public class GenericManagementService<T extends Identificationable, R extends JpaRepository<T, Long>> implements IManagementService<T> {

    protected R repository;
    String entity;
//TODO exceptions, globalexceptionhandler
    public GenericManagementService(R repository, String entity) {
        this.repository = repository;
        this.entity = entity;
    }

    @Override
    public T add(T entity) {
        return repository.save(
                Optional.ofNullable(entity)
                        .filter(x -> x.getId() == null)
//                        .orElseThrow(() -> new BadEntityException(entity))
                        .orElseThrow(() -> new RuntimeException())
        );
    }

    @Override
    public void delete(long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
//            throw new BadIdException("Id " + id + " not found for delete!", e, id);
            throw new RuntimeException();
        }
    }

    //dirty checking - metoda @Transactional, wczytujemy encje powiązaną z kontekstem utrwalania, przypisujemy nową encje
    @Override
    @Transactional
    public T edit(T entity) {

        T loadedEntity = repository.findById(
                Optional.ofNullable(entity.getId())
                        .filter(Objects::nonNull)
//                        .orElseThrow(() -> new BadEntityException(entity))
                        .orElseThrow(() ->   new RuntimeException())
//        ).orElseThrow(() -> new BadEntityException(entity));
        ).orElseThrow(() -> new RuntimeException());

        loadedEntity = entity;
        return loadedEntity;

    }

    @Override
    public T get(long id) {
//        return repository.findById(id).orElseThrow(() -> new SpecificEntityNotFoundException(id));
        return repository.findById(id).orElseThrow(() -> new RuntimeException());
    }

    @Override
    public List<T> getAll() {
        return repository.findAll();
    }

    @Override
    public String getType() {
        return entity;
    }
}
