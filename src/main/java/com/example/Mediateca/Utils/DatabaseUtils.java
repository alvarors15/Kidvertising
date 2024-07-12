package com.example.Mediateca.Utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import org.springframework.stereotype.Component;

/**
 * Clase de utilidades para facilitar las operaciones comunes con la base de
 * datos
 */
@Component
public class DatabaseUtils {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Intenta obtener el primer resultado de una consulta a bbdd, devolviendo null
     * si no se encuentra ningun resultado
     *
     * @param query       la query de la que se obtendra el resultado.
     * @param resultClass la clase del tipo de resultado esperado.
     * @param <T>         el tipo generico del resultado esperado.
     * @return El primer resultado de la consulta o null si no hay resultados.
     */
    @SuppressWarnings("unchecked")
    public <T> T findFirstResult(Query query, Class<T> resultClass) {
        try {
            return (T) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}