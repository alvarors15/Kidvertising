package com.example.Mediateca.Repositories.GenderRoleFemaleTypeRepository;

import com.example.Mediateca.Domain.GenderRoleFemaleTypeDO;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interfaz personalizada para operaciones adicionales en el repositorio de
 * tipo rol mujer que no estan cubiertas por JpaRepository.
 */
@Repository
@Primary
public interface GenderRoleFemaleTypeRepositoryCustom {

    /**
     * Devuelve todos los roles de la mujer
     *
     * @return la lista de roles.
     */
    List<GenderRoleFemaleTypeDO> findAll();

    /**
     * Devuelve el rol de la mujer por su identificador
     *
     * @param id el id del rol de la mujer.
     * @return el GenderRoleFemaleTypeDO.
     */
    GenderRoleFemaleTypeDO findById(int id);

    /**
     * Devuelve el rol de la mujer por el nombre del rol
     *
     * @param femaleRole el nombre del rol.
     * @return el GenderRoleFemaleTypeDO.
     */
    GenderRoleFemaleTypeDO findByRole(String femaleRole);

    /**
     * Elimina una lista de roles de la mujer por sus identificadores
     *
     * @param idList lista de identificadores.
     */
    void deleteByIdList(List<Integer> idList);

}