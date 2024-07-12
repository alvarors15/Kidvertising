package com.example.Mediateca.Repositories.GenderRoleMaleTypeRepository;

import com.example.Mediateca.Domain.GenderRoleMaleTypeDO;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para manejar las operaciones en bbdd para los objetos de tipo rol
 * hombre GenderRoleMaleTypeDO.
 * Extiende de JpaRepository para usar sus funcionalidades basicas y ademas
 * integra nuevas funcionalidades definidas en
 * GenderRoleMaleTypeRepositoryCustom.
 */
@Repository
@Primary
public interface GenderRoleMaleTypeRepository
        extends JpaRepository<GenderRoleMaleTypeDO, Integer>, GenderRoleMaleTypeRepositoryCustom {

}