package com.example.Mediateca.Repositories.GenderRoleFemaleTypeRepository;

import com.example.Mediateca.Domain.GenderRoleFemaleTypeDO;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para manejar las operaciones en bbdd para los objetos de tipo rol
 * mujer GenderRoleFemaleTypeDO.
 * Extiende de JpaRepository para usar sus funcionalidades basicas y ademas
 * integra nuevas funcionalidades definidas en
 * GenderRoleFemaleTypeRepositoryCustom.
 */
@Repository
@Primary
public interface GenderRoleFemaleTypeRepository
        extends JpaRepository<GenderRoleFemaleTypeDO, Integer>, GenderRoleFemaleTypeRepositoryCustom {

}