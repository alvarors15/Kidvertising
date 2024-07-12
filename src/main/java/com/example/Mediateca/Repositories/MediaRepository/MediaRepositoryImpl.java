package com.example.Mediateca.Repositories.MediaRepository;

import com.example.Mediateca.Domain.MediaDO;
import com.example.Mediateca.Utils.DatabaseUtils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class MediaRepositoryImpl implements MediaRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private DatabaseUtils databaseUtils;

    @SuppressWarnings("unchecked")
    @Override
    public List<MediaDO> findAll() {
        String query = "SELECT * FROM medios";
        List<MediaDO> result = (List<MediaDO>) entityManager
                .createNativeQuery(query, MediaDO.class)
                .getResultList();
        if (result == null) {
            return new ArrayList<MediaDO>();
        }
        return result;
    }

    @Override
    public MediaDO findById(int id) {
        String query = "SELECT * FROM ROL_MENOR WHERE ID_MEDIO = :id";
        MediaDO result = (MediaDO) entityManager.createNativeQuery(query, MediaDO.class)
                .setParameter("id", id)
                .getSingleResult();
        return result;
    }

    @Override
    public MediaDO findByName(String mediaName) {
        String query = "SELECT * FROM medios WHERE NOMBRE_MEDIO = :mediaName";
        Query queryFinal = (Query) entityManager
                .createNativeQuery(query, MediaDO.class)
                .setParameter("mediaName", mediaName);
        return databaseUtils.findFirstResult(queryFinal, MediaDO.class);
    }

    @Override
    public void deleteByIdList(List<Integer> idList) {
        String query = "DELETE FROM medios WHERE ID_MEDIO in (:idList)";
        entityManager.createNativeQuery(query)
                .setParameter("idList", idList)
                .executeUpdate();
    }

}
