package com.example.Mediateca.Repositories.TokenResetPasswordRepository;

import com.example.Mediateca.Domain.TokenResetPasswordDO;

/**
 * Interfaz personalizada para operaciones adicionales en el repositorio de
 * tokens de reseteo de contrasenia que no estan cubiertas por JpaRepository.
 */
public interface TokenResetPasswordRepositoryCustom {

    /**
     * Devuelve un token que no este usario ni expirado
     *
     * @param token el token.
     * @return el TokenResetPasswordDO.
     */
    TokenResetPasswordDO findByTokenAndNotUsedAndNotExpired(String token);
}
