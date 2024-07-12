package com.example.Mediateca.Repositories.KeyWordsRepository;

import com.example.Mediateca.Domain.KeyWordsDO;

import java.util.List;

/**
 * Interfaz personalizada para operaciones adicionales en el repositorio de
 * palabras clave que no estan cubiertas por JpaRepository.
 */
public interface KeyWordsRepositoryCustom {

    /**
     * Devuelve todos las palabras clave
     *
     * @return la lista de palabras clave.
     */
    List<KeyWordsDO> findAll();

    /**
     * Devuelve el palabras clave por su identificador
     *
     * @param id el id de la palabra clave.
     * @return el KeyWordsDO.
     */
    KeyWordsDO findById(int id);

    /**
     * Devuelve las palabras clave por una lista de palabras
     *
     * @param words la lista de palabras.
     * @return la lista de palabras clave.
     */
    List<String> findWordsByList(List<String> words);

    /**
     * Devuelve la palabra clave por la palabra
     *
     * @param words la palabra.
     * @return el KeyWordsDO.
     */
    KeyWordsDO findByWord(String world);

    /**
     * Elimina una lista de palabras clave por sus identificadores
     *
     * @param idList lista de identificadores.
     */
    void deleteByIdList(List<Integer> idList);

}