package com.example.Mediateca.Repositories.GenderRoleFemaleTypeRepository;

import com.example.Mediateca.Domain.GenderRoleFemaleTypeDO;
import com.example.Mediateca.Utils.DatabaseUtils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class GenderRoleFemaleTypeRepositoryImpl implements GenderRoleFemaleTypeRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private DatabaseUtils databaseUtils;

    @SuppressWarnings("unchecked")
    @Override
    public List<GenderRoleFemaleTypeDO> findAll() {
        String query = "SELECT * FROM rol_genero_mujer";
        List<GenderRoleFemaleTypeDO> result = (List<GenderRoleFemaleTypeDO>) entityManager
                .createNativeQuery(query, GenderRoleFemaleTypeDO.class)
                .getResultList();
        if (result == null) {
            return new ArrayList<GenderRoleFemaleTypeDO>();
        }
        return result;
    }

    @Override
    public GenderRoleFemaleTypeDO findById(int id) {
        String query = "SELECT * FROM rol_genero_mujer WHERE ID_ROL_GENERO_MUJER = :id";
        GenderRoleFemaleTypeDO result = (GenderRoleFemaleTypeDO) entityManager
                .createNativeQuery(query, GenderRoleFemaleTypeDO.class)
                .setParameter("id", id)
                .getSingleResult();
        return result;
    }

    @Override
    public GenderRoleFemaleTypeDO findByRole(String role) {
        String query = "SELECT * FROM rol_genero_mujer WHERE TIPO_ROL_GENERO_MUJER = :role";
        Query queryFinal = (Query) entityManager
                .createNativeQuery(query, GenderRoleFemaleTypeDO.class)
                .setParameter("role", role);
        return databaseUtils.findFirstResult(queryFinal, GenderRoleFemaleTypeDO.class);
    }

    @Override
    public void deleteByIdList(List<Integer> idList) {
        String query = "DELETE FROM rol_genero_mujer WHERE ID_ROL_GENERO_MUJER in (:idList)";
        entityManager.createNativeQuery(query)
                .setParameter("idList", idList)
                .executeUpdate();
    }

}
