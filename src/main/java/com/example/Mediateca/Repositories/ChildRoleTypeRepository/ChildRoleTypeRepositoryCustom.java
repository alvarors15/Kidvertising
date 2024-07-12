package com.example.Mediateca.Repositories.ChildRoleTypeRepository;

import com.example.Mediateca.Domain.ChildRoleTypeDO;

import java.util.List;

/**
 * Interfaz personalizada para operaciones adicionales en el repositorio de
 * tipo rol menor que no estan cubiertas por JpaRepository.
 */
public interface ChildRoleTypeRepositoryCustom {

    /**
     * Devuelve todos los roles del menor
     *
     * @return la lista de roles.
     */
    List<ChildRoleTypeDO> findAll();

    /**
     * Devuelve el rol del menor por su identificador
     *
     * @param id el id del rol del menor.
     * @return el ChildRoleTypeDO.
     */
    ChildRoleTypeDO findById(int id);

    /**
     * Devuelve el rol del menor por el nombre del rol
     *
     * @param childRole el nombre del rol.
     * @return el ChildRoleTypeDO.
     */
    ChildRoleTypeDO findByRole(String childRole);

    /**
     * Elimina una lista de roles del menor por sus identificadores
     *
     * @param idList lista de identificadores.
     */
    void deleteByIdList(List<Integer> idList);
}