package com.example.Mediateca.Repositories.TopicRepository;

import com.example.Mediateca.Domain.TopicDO;
import com.example.Mediateca.Utils.DatabaseUtils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class TopicRepositoryImpl implements TopicRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private DatabaseUtils databaseUtils;

    @SuppressWarnings("unchecked")
    @Override
    public List<TopicDO> findAll() {
        String query = "SELECT * FROM tematicas";
        List<TopicDO> result = (List<TopicDO>) entityManager
                .createNativeQuery(query, TopicDO.class)
                .getResultList();
        if (result == null) {
            return new ArrayList<TopicDO>();
        }
        return result;
    }

    @Override
    public TopicDO findById(int id) {
        String query = "SELECT * FROM tematicas WHERE ID_TEMATICA = :id";
        TopicDO result = (TopicDO) entityManager.createNativeQuery(query, TopicDO.class)
                .setParameter("id", id)
                .getSingleResult();
        return result;
    }

    @Override
    public TopicDO findByName(String name) {
        String query = "SELECT * FROM tematicas WHERE NOMBRE_TEMATICA = :name";
        Query queryFinal = (Query) entityManager.createNativeQuery(query, TopicDO.class)
                .setParameter("name", name);
        return databaseUtils.findFirstResult(queryFinal, TopicDO.class);
    }

    @Override
    public void deleteByIdList(List<Integer> idList) {
        String query = "DELETE FROM tematicas WHERE ID_TEMATICA in (:idList)";
        entityManager.createNativeQuery(query)
                .setParameter("idList", idList)
                .executeUpdate();
    }

}
