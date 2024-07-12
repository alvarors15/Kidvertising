package com.example.Mediateca.Repositories.ChildRoleTypeRepository;

import com.example.Mediateca.Domain.ChildRoleTypeDO;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para manejar las operaciones en bbdd para los objetos de tipo rol
 * menor ChildRoleTypeDO.
 * Extiende de JpaRepository para usar sus funcionalidades basicas y ademas
 * integra nuevas funcionalidades definidas en
 * ChildRoleTypeRepositoryCustom.
 */
@Repository
@Primary
public interface ChildRoleTypeRepository
                extends JpaRepository<ChildRoleTypeDO, Integer>, ChildRoleTypeRepositoryCustom {

}