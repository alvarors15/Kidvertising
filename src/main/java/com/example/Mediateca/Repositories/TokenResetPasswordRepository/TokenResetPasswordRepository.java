package com.example.Mediateca.Repositories.TokenResetPasswordRepository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Mediateca.Domain.TokenResetPasswordDO;

/**
 * Repositorio para manejar las operaciones en bbdd para los objetos de token de
 * reseteo de contrasenia TokenResetPasswordDO.
 * Extiende de JpaRepository para usar sus funcionalidades basicas y ademas
 * integra nuevas funcionalidades definidas en
 * TokenResetPasswordRepositoryCustom.
 */
@Repository
public interface TokenResetPasswordRepository
        extends JpaRepository<TokenResetPasswordDO, Long>, TokenResetPasswordRepositoryCustom {

}
