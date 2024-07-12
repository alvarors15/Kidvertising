package com.example.Mediateca.Repositories.UserRepository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Mediateca.Domain.UserDO;

/**
 * Repositorio para manejar las operaciones en bbdd para los objetos de
 * usuarios UserDO.
 * Extiende de JpaRepository para usar sus funcionalidades basicas y ademas
 * integra nuevas funcionalidades definidas en
 * UserRepositoryCustom.
 */
@Repository
public interface UserRepository extends JpaRepository<UserDO, Integer>, UserRepositoryCustom {

}
