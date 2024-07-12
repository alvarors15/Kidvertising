package com.example.Mediateca.Repositories.TokenResetPasswordRepository;

import com.example.Mediateca.Domain.TokenResetPasswordDO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

public class TokenResetPasswordRepositoryImpl implements TokenResetPasswordRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public TokenResetPasswordDO findByTokenAndNotUsedAndNotExpired(String token) {
        String query = "SELECT * FROM tokens_reinicio_password WHERE TOKEN = :token AND USADO = false AND FECHA_EXPIRACION > CURRENT_TIMESTAMP";

        try {
            TokenResetPasswordDO result = (TokenResetPasswordDO) entityManager
                    .createNativeQuery(query, TokenResetPasswordDO.class)
                    .setParameter("token", token)
                    .getSingleResult();
            return result;
        } catch (NoResultException e) {
            return null;
        }
    }

}
