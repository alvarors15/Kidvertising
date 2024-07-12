package com.example.Mediateca.Repositories.GenderRoleMaleTypeRepository;

import com.example.Mediateca.Domain.GenderRoleMaleTypeDO;

import java.util.List;

/**
 * Interfaz personalizada para operaciones adicionales en el repositorio de
 * tipo rol hombre que no estan cubiertas por JpaRepository.
 */
public interface GenderRoleMaleTypeRepositoryCustom {

    /**
     * Devuelve todos los roles del hombre
     *
     * @return la lista de roles.
     */
    List<GenderRoleMaleTypeDO> findAll();

    /**
     * Devuelve el rol del hombre por su identificador
     *
     * @param id el id del rol del hombre.
     * @return el GenderRoleMaleTypeDO.
     */
    GenderRoleMaleTypeDO findById(int id);

    /**
     * Devuelve el rol del hombre por el nombre del rol
     *
     * @param maleRole el nombre del rol.
     * @return el GenderRoleMaleTypeDO.
     */
    GenderRoleMaleTypeDO findByRole(String maleRole);

    /**
     * Elimina una lista de roles del hombre por sus identificadores
     *
     * @param idList lista de identificadores.
     */
    void deleteByIdList(List<Integer> idList);

}