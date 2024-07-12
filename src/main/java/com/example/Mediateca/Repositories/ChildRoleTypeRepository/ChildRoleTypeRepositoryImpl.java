package com.example.Mediateca.Repositories.ChildRoleTypeRepository;

import com.example.Mediateca.Domain.ChildRoleTypeDO;
import com.example.Mediateca.Utils.DatabaseUtils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class ChildRoleTypeRepositoryImpl implements ChildRoleTypeRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private DatabaseUtils databaseUtils;

    @SuppressWarnings("unchecked")
    @Override
    public List<ChildRoleTypeDO> findAll() {
        String query = "SELECT * FROM rol_menor";
        List<ChildRoleTypeDO> result = (List<ChildRoleTypeDO>) entityManager
                .createNativeQuery(query, ChildRoleTypeDO.class)
                .getResultList();
        if (result == null) {
            return new ArrayList<ChildRoleTypeDO>();
        }
        return result;
    }

    @Override
    public ChildRoleTypeDO findById(int id) {
        String query = "SELECT * FROM rol_menor WHERE ID_ROL_MENOR = :id";
        ChildRoleTypeDO result = (ChildRoleTypeDO) entityManager.createNativeQuery(query, ChildRoleTypeDO.class)
                .setParameter("id", id)
                .getSingleResult();
        return result;
    }

    @Override
    public ChildRoleTypeDO findByRole(String role) {
        String query = "SELECT * FROM rol_menor WHERE TIPO_ROL_MENOR = :role";
        Query queryFinal = (Query) entityManager.createNativeQuery(query, ChildRoleTypeDO.class)
                .setParameter("role", role);
        return databaseUtils.findFirstResult(queryFinal, ChildRoleTypeDO.class);
    }

    @Override
    public void deleteByIdList(List<Integer> idList) {
        String query = "DELETE FROM rol_menor WHERE ID_ROL_MENOR in (:idList)";
        entityManager.createNativeQuery(query)
                .setParameter("idList", idList)
                .executeUpdate();
    }

}
