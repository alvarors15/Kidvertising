package com.example.Mediateca.Repositories.UserRepository;

import java.util.List;

import com.example.Mediateca.Domain.UserDO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

public class UserRepositoryImpl implements UserRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    @Override
    public List<UserDO> findAll() {
        String query = "SELECT * FROM usuarios";
        return (List<UserDO>) entityManager.createNativeQuery(query, UserDO.class).getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UserDO> findAllButActual(Integer actualUserId) {
        String query = "SELECT * FROM usuarios where ID_USUARIO != :actualUserId";
        List<UserDO> result = (List<UserDO>) entityManager.createNativeQuery(query, UserDO.class)
                .setParameter("actualUserId", actualUserId).getResultList();
        return result;
    }

    @Override
    public UserDO findUserById(Integer id) {
        String query = "SELECT * FROM usuarios WHERE ID_USUARIO = :id";
        UserDO result = (UserDO) entityManager.createNativeQuery(query, UserDO.class)
                .setParameter("id", id)
                .getSingleResult();
        return result;
    }

    @Override
    public UserDO findByEmail(String email) {
        String query = "SELECT * FROM usuarios WHERE CORREO = :email";
        try {
            UserDO result = (UserDO) entityManager.createNativeQuery(query, UserDO.class)
                    .setParameter("email", email)
                    .getSingleResult();
            return result;
        } catch (NoResultException e) {
            return null; // o manejar de otra manera
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UserDO> findByRole(String role) {
        String query = "SELECT * FROM usuarios WHERE ROL = :role";

        List<UserDO> result = (List<UserDO>) entityManager.createNativeQuery(query, UserDO.class)
                .setParameter("role", role).getResultList();
        return result;
    }

    @Override
    public void deleteByIdList(List<Integer> idList) {
        String query = "DELETE FROM usuarios WHERE ID_USUARIO in (:idList)";
        entityManager.createNativeQuery(query)
                .setParameter("idList", idList)
                .executeUpdate();
    }

}
