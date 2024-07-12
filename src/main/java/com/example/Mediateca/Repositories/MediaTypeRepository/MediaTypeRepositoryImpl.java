package com.example.Mediateca.Repositories.MediaTypeRepository;

import com.example.Mediateca.Domain.MediaTypeDO;
import com.example.Mediateca.Utils.DatabaseUtils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class MediaTypeRepositoryImpl implements MediaTypeRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private DatabaseUtils databaseUtils;

    @SuppressWarnings("unchecked")
    @Override
    public List<MediaTypeDO> findAll() {
        String query = "SELECT * FROM medio_comunicacion";
        List<MediaTypeDO> result = (List<MediaTypeDO>) entityManager
                .createNativeQuery(query, MediaTypeDO.class)
                .getResultList();
        if (result == null) {
            return new ArrayList<MediaTypeDO>();
        }
        return result;
    }

    @Override
    public MediaTypeDO findById(int id) {
        String query = "SELECT * FROM medio_comunicacion WHERE ID_MEDIO_COMUNICACION = :id";
        MediaTypeDO result = (MediaTypeDO) entityManager.createNativeQuery(query, MediaTypeDO.class)
                .setParameter("id", id)
                .getSingleResult();
        return result;
    }

    @Override
    public MediaTypeDO findByType(String type) {
        String query = "SELECT * FROM medio_comunicacion WHERE TIPO_MEDIO_COMUNICACION = :type";
        Query queryFinal = (Query) entityManager
                .createNativeQuery(query, MediaTypeDO.class)
                .setParameter("type", type);
        return databaseUtils.findFirstResult(queryFinal, MediaTypeDO.class);
    }

    @Override
    public void deleteByIdList(List<Integer> idList) {
        String query = "DELETE FROM medio_comunicacion WHERE ID_MEDIO_COMUNICACION in (:idList)";
        entityManager.createNativeQuery(query)
                .setParameter("idList", idList)
                .executeUpdate();
    }

}
