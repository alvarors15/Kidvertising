package com.example.Mediateca.Repositories.AdRepository;

import com.example.Mediateca.Domain.AdDO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Interfaz personalizada para operaciones adicionales en el repositorio de
 * anuncios que no estan cubiertas por JpaRepository.
 */
@Repository
public interface AdRepositoryCustom {

    /**
     * Obtiene todos los anuncios de bbdd
     *
     * @return la lista de anuncios.
     */
    List<AdDO> findAll();

    /**
     * Devuelve un anuncio por su identificador
     *
     * @param id el id del anuncio.
     * @return el AdDO.
     */
    AdDO findAdById(Long id);

    /**
     * Elimina todos los anuncios de un usuario
     *
     * @param id el id del usuario creador de los anuncios.
     * @return el numero de anuncios eliminados.
     */
    Integer deleteByUserId(Integer userId);

    /**
     * Devuelve todos los anuncios de un usuario
     *
     * @param id el id del usuario creador de los anuncios.
     * @return el listado de anuncios.
     */
    List<AdDO> findAdByUserId(Integer userId);

    /**
     * Devuelve todos los 20 ultimos anuncios con un video como archivo
     *
     * @return el listado de anuncios.
     */
    List<AdDO> findLastsVideos();

    /**
     * Devuelve todos los 20 ultimos anuncios con una imagen como archivo
     *
     * @return el listado de anuncios.
     */
    List<AdDO> findLastsImages();

    /**
     * Devuelve todos los 20 ultimos anuncios con un audio como archivo
     *
     * @return el listado de anuncios.
     */
    List<AdDO> findLastsAudios();

    /**
     * Devuelve el total de anuncios
     *
     * @return el numero total de anuncios.
     */
    Integer countAllAds();

    /**
     * Devuelve el total de anuncios para cada tipo de relacion de genero.
     *
     * @return mapa Relacion Genero-Numero de anuncios.
     */
    Map<String, Integer> countAdsByGenderRelation();

    /**
     * Devuelve el total de anuncios para cada rol del hombre.
     *
     * @return mapa Rol del hombre-Numero de anuncios.
     */
    Map<String, Integer> countAdsByMaleRole();

    /**
     * Devuelve el total de anuncios para cada rol de la mujer.
     *
     * @return mapa Rol de la mujer-Numero de anuncios.
     */
    Map<String, Integer> countAdsByFemaleRole();

    /**
     * Devuelve el total de anuncios para cada rol del menor.
     *
     * @return mapa Rol del menor-Numero de anuncios.
     */
    Map<String, Integer> countAdsByChildRole();

    /**
     * Devuelve el total de anuncios para cada genero del menor.
     *
     * @return mapa Genero del menor-Numero de anuncios.
     */
    Map<String, Integer> countAdsByChildGender();

    /**
     * Devuelve el total de anuncios para cada protagonismo del menor.
     *
     * @return mapa Protagonismo del menor-Numero de anuncios.
     */
    Map<String, Integer> countAdsByProtagonism();

    /**
     * Devuelve el total de anuncios para cada tematica.
     *
     * @return mapa Tematica-Numero de anuncios.
     */
    Map<String, Integer> countAdsByTopic();

    /**
     * Devuelve el total de anuncios para cada medio.
     *
     * @return mapa Medio-Numero de anuncios.
     */
    Map<String, Integer> countAdsByMedia();

    /**
     * Devuelve el total de anuncios para cada tipo de medio de comunicacion.
     *
     * @return mapa Tipo medio comunicacion-Numero de anuncios.
     */
    Map<String, Integer> countAdsByMediaType();

    /**
     * Devuelve el total de anuncios para cada tipo de publicidad.
     *
     * @return mapa Tipo de publicidad-Numero de anuncios.
     */
    Map<String, Integer> countAdsByAdvertisingType();

    /**
     * Devuelve el total de anuncios para cada decada.
     *
     * @return mapa Decada-Numero de anuncios.
     */
    Map<String, Integer> countAdsByDecade();

}