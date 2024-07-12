package com.example.Mediateca.Repositories.NotificationRepository;

import java.util.List;

import com.example.Mediateca.Domain.NotificationDO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class NotificationRepositoryImpl implements NotificationRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    @Override
    public List<NotificationDO> findAll() {
        String query = "SELECT * FROM notificaciones WHERE ESTADO IS NOT NULL";
        List<NotificationDO> result = (List<NotificationDO>) entityManager
                .createNativeQuery(query, NotificationDO.class)
                .getResultList();
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<NotificationDO> findAllWithStatusNotNull() {
        String query = "SELECT * FROM notificaciones";
        return (List<NotificationDO>) entityManager.createNativeQuery(query, NotificationDO.class).getResultList();
    }

    @Override
    public NotificationDO findById(int id) {
        String query = "SELECT * FROM notificaciones WHERE ID_NOTIFICACION = :id";
        NotificationDO result = (NotificationDO) entityManager.createNativeQuery(query, NotificationDO.class)
                .setParameter("id", id)
                .getSingleResult();
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<NotificationDO> findByStatus(String status) {
        String query = "SELECT * FROM notificaciones WHERE ESTADO = :status";
        List<NotificationDO> result = (List<NotificationDO>) entityManager
                .createNativeQuery(query, NotificationDO.class)
                .setParameter("status", status)
                .getResultList();
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<NotificationDO> findPendingByAd(Long adId) {
        String query = "SELECT * FROM notificaciones WHERE ID_ANUNCIO = :adId AND ESTADO = :status";
        List<NotificationDO> result = (List<NotificationDO>) entityManager
                .createNativeQuery(query, NotificationDO.class)
                .setParameter("status", "PENDING")
                .setParameter("adId", adId)
                .getResultList();
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<NotificationDO> findByUserId(Integer userId) {
        String query = "SELECT * FROM notificaciones WHERE ID_USUARIO = :userId";
        List<NotificationDO> result = (List<NotificationDO>) entityManager
                .createNativeQuery(query, NotificationDO.class)
                .setParameter("userId", userId)
                .getResultList();
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<NotificationDO> findByUserIdAndChecked(Integer userId) {
        String query = "SELECT * FROM notificaciones WHERE ID_USUARIO = :userId AND FECHA_RESOLUCION IS NOT NULL";
        List<NotificationDO> result = (List<NotificationDO>) entityManager
                .createNativeQuery(query, NotificationDO.class)
                .setParameter("userId", userId)
                .getResultList();
        return result;
    }

    @Override
    public void deleteByIdList(List<Integer> idList) {
        String query = "DELETE FROM notificaciones WHERE ID_NOTIFICACION in (:idList)";
        entityManager.createNativeQuery(query)
                .setParameter("idList", idList)
                .executeUpdate();
    }

}
