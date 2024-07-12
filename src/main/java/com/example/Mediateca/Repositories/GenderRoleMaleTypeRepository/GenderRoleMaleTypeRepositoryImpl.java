package com.example.Mediateca.Repositories.GenderRoleMaleTypeRepository;

import com.example.Mediateca.Domain.GenderRoleMaleTypeDO;
import com.example.Mediateca.Utils.DatabaseUtils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class GenderRoleMaleTypeRepositoryImpl implements GenderRoleMaleTypeRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private DatabaseUtils databaseUtils;

    @SuppressWarnings("unchecked")
    @Override
    public List<GenderRoleMaleTypeDO> findAll() {
        String query = "SELECT * FROM rol_genero_hombre";
        List<GenderRoleMaleTypeDO> result = (List<GenderRoleMaleTypeDO>) entityManager
                .createNativeQuery(query, GenderRoleMaleTypeDO.class)
                .getResultList();
        if (result == null) {
            return new ArrayList<GenderRoleMaleTypeDO>();
        }
        return result;
    }

    @Override
    public GenderRoleMaleTypeDO findById(int id) {
        String query = "SELECT * FROM rol_genero_hombre WHERE ID_ROL_GENERO_HOMBRE = :id";
        GenderRoleMaleTypeDO result = (GenderRoleMaleTypeDO) entityManager
                .createNativeQuery(query, GenderRoleMaleTypeDO.class)
                .setParameter("id", id)
                .getSingleResult();
        return result;
    }

    @Override
    public GenderRoleMaleTypeDO findByRole(String role) {
        String query = "SELECT * FROM rol_genero_hombre WHERE TIPO_ROL_GENERO_HOMBRE = :role";
        Query queryFinal = (Query) entityManager
                .createNativeQuery(query, GenderRoleMaleTypeDO.class)
                .setParameter("role", role);
        return databaseUtils.findFirstResult(queryFinal, GenderRoleMaleTypeDO.class);
    }

    @Override
    public void deleteByIdList(List<Integer> idList) {
        String query = "DELETE FROM rol_genero_hombre WHERE ID_ROL_GENERO_HOMBRE in (:idList)";
        entityManager.createNativeQuery(query)
                .setParameter("idList", idList)
                .executeUpdate();
    }

}
