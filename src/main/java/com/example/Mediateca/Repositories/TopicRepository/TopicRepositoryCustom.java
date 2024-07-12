package com.example.Mediateca.Repositories.TopicRepository;

import com.example.Mediateca.Domain.TopicDO;

import java.util.List;

/**
 * Interfaz personalizada para operaciones adicionales en el repositorio de
 * tematicas que no estan cubiertas por JpaRepository.
 */
public interface TopicRepositoryCustom {

    /**
     * Devuelve todas las tematicas
     *
     * @return la lista de tematicas.
     */
    List<TopicDO> findAll();

    /**
     * Devuelve la tematica por su identificador
     *
     * @param id el id de la tematica.
     * @return el TopicDO.
     */
    TopicDO findById(int id);

    /**
     * Devuelve la tematica por el nombre de la tematica
     *
     * @param topicName el nombre del tematicas.
     * @return el TopicDO.
     */
    TopicDO findByName(String topicName);

    /**
     * Elimina una lista de tematicas por sus identificadores
     *
     * @param idList lista de identificadores.
     */
    void deleteByIdList(List<Integer> idList);

}