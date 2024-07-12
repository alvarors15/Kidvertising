package com.example.Mediateca.Repositories.MediaRepository;

import com.example.Mediateca.Domain.MediaDO;

import java.util.List;

/**
 * Interfaz personalizada para operaciones adicionales en el repositorio de
 * medios que no estan cubiertas por JpaRepository.
 */
public interface MediaRepositoryCustom {

    /**
     * Devuelve todos los medios
     *
     * @return la lista de medios.
     */
    List<MediaDO> findAll();

    /**
     * Devuelve el medio por su identificador
     *
     * @param id el id del medio.
     * @return el MediaDO.
     */
    MediaDO findById(int id);

    /**
     * Devuelve el medio por el nombre del medio
     *
     * @param mediaName el nombre del medio.
     * @return el MediaDO.
     */
    MediaDO findByName(String mediaName);

    /**
     * Elimina una lista de medios por sus identificadores
     *
     * @param idList lista de identificadores.
     */
    void deleteByIdList(List<Integer> idList);

}