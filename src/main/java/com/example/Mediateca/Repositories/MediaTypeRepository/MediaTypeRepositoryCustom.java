package com.example.Mediateca.Repositories.MediaTypeRepository;

import com.example.Mediateca.Domain.MediaTypeDO;

import java.util.List;

/**
 * Interfaz personalizada para operaciones adicionales en el repositorio de
 * tipo tipo de medio de comunicacion comunicacion que no estan cubiertas por
 * JpaRepository.
 */
public interface MediaTypeRepositoryCustom {

    /**
     * Devuelve todos los tipos de medio de comunicacion
     *
     * @return la lista de tipos de medio de comunicacion.
     */
    List<MediaTypeDO> findAll();

    /**
     * Devuelve el tipo de medio de comunicacion por su identificador
     *
     * @param id el id del tipo de medio de comunicacion.
     * @return el MediaTypeDO.
     */
    MediaTypeDO findById(int id);

    /**
     * Devuelve el tipo de medio de comunicacion por el tipo
     *
     * @param type el tipo de medio de comunicacion.
     * @return el MediaTypeDO.
     */
    MediaTypeDO findByType(String type);

    /**
     * Elimina una lista de tipo de medio de comunicacion por sus identificadores
     *
     * @param idList lista de identificadores.
     */
    void deleteByIdList(List<Integer> idList);

}