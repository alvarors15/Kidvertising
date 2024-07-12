package com.example.Mediateca.Repositories.KeyWordsRepository;

import com.example.Mediateca.Domain.KeyWordsDO;
import com.example.Mediateca.Utils.DatabaseUtils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class KeyWordsRepositoryImpl implements KeyWordsRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private DatabaseUtils databaseUtils;

    @SuppressWarnings("unchecked")
    @Override
    public List<KeyWordsDO> findAll() {
        String query = "SELECT * FROM palabras_clave";
        List<KeyWordsDO> result = (List<KeyWordsDO>) entityManager
                .createNativeQuery(query, KeyWordsDO.class)
                .getResultList();
        if (result == null) {
            return new ArrayList<KeyWordsDO>();
        }
        return result;
    }

    @Override
    public KeyWordsDO findById(int id) {
        String query = "SELECT * FROM palabras_clave WHERE ID_PALABRA_CLAVE = :id";
        KeyWordsDO result = (KeyWordsDO) entityManager.createNativeQuery(query, KeyWordsDO.class)
                .setParameter("id", id)
                .getSingleResult();
        return result;
    }

    @Override
    public KeyWordsDO findByWord(String word) {
        String query = "SELECT * FROM palabras_clave WHERE PALABRA_CLAVE = :word";
        Query queryFinal = (Query) entityManager.createNativeQuery(query, KeyWordsDO.class)
                .setParameter("word", word);
        return databaseUtils.findFirstResult(queryFinal, KeyWordsDO.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<String> findWordsByList(List<String> words) {
        String query = "SELECT PALABRA_CLAVE FROM palabras_clave WHERE PALABRA_CLAVE in (:words)";
        List<String> result = (List<String>) entityManager.createNativeQuery(query, String.class)
                .setParameter("words", words)
                .getResultList();
        if (result == null) {
            return new ArrayList<String>();
        }
        return result;
    }

    @Override
    public void deleteByIdList(List<Integer> idList) {
        String query = "DELETE FROM palabras_clave WHERE ID_PALABRA_CLAVE in (:idList)";
        entityManager.createNativeQuery(query)
                .setParameter("idList", idList)
                .executeUpdate();
    }

}
