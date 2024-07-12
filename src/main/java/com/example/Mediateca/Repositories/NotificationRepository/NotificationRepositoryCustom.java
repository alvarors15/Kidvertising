package com.example.Mediateca.Repositories.NotificationRepository;

import java.util.List;

import com.example.Mediateca.Domain.NotificationDO;

/**
 * Interfaz personalizada para operaciones adicionales en el repositorio de
 * notificaciones que no estan cubiertas por JpaRepository.
 */
public interface NotificationRepositoryCustom {

    /**
     * Devuelve todas las notificaciones
     *
     * @return la lista de notificaciones.
     */
    List<NotificationDO> findAll();

    /**
     * Devuelve una lista de notificaciones que tienen un estado asignado
     *
     * @return la lista de notificaciones.
     */
    List<NotificationDO> findAllWithStatusNotNull();

    /**
     * Devuelve la notificacion por su identificador
     *
     * @param id el id de la notificacion.
     * @return el NotificationDO.
     */
    NotificationDO findById(int id);

    /**
     * Devuelve una lista de notificaciones por su estado
     *
     * @param status el estado de la notificacion.
     * @return la lista de notificaciones.
     */
    List<NotificationDO> findByStatus(String status);

    /**
     * Devuelve una lista de notificaciones por su usuario y que esten revisadas
     *
     * @param userId id del usuario que crea la notificacion.
     * @return la lista de notificaciones.
     */
    List<NotificationDO> findByUserIdAndChecked(Integer userId);

    /**
     * Devuelve una lista de notificaciones por su usuario
     *
     * @param userId id del usuario que crea la notificacion.
     * @return la lista de notificaciones.
     */
    List<NotificationDO> findByUserId(Integer userId);

    /**
     * Devuelve una lista de notificaciones de un anuncio que esten pendientes
     *
     * @param adId el id del anuncio.
     * @return la lista de notificaciones.
     */
    List<NotificationDO> findPendingByAd(Long adId);

    /**
     * Elimina una lista de notificaciones por sus identificadores
     *
     * @param idList lista de identificadores.
     */
    void deleteByIdList(List<Integer> idList);
}
