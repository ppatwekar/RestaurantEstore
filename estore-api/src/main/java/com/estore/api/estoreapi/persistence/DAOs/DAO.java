package com.estore.api.estoreapi.persistence.DAOs;

import java.io.IOException;
import java.util.List;

public interface DAO<T, ID> {
    public T save(T entity) throws IOException;

    public List<T> findAll();

    public T findById(ID id);

    boolean existsById(ID id);

    public T update(T entity) throws IOException;

    public void delete(T entity);

    public boolean deleteById(ID id) throws IOException;
}