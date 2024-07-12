package com.example.Mediateca.Repositories.UserRepository;

import java.util.List;

import com.example.Mediateca.Domain.UserDO;

/**
 * Interfaz personalizada para operaciones adicionales en el repositorio de
 * usuarios que no estan cubiertas por JpaRepository.
 */
public interface UserRepositoryCustom {

    /**
     * Devuelve todos los usuarios
     *
     * @return la lista de usuarios.
     */
    List<UserDO> findAll();

    /**
     * Devuelve todos los usuarios menos el actual de la sesion
     *
     * @param actualUserId el id del usuario actual.
     * @return la lista de usuarios.
     */
    List<UserDO> findAllButActual(Integer actualUserId);

    /**
     * Devuelve el usuario por su identificador
     *
     * @param id el id del usuario.
     * @return el UserDO.
     */
    UserDO findUserById(Integer id);

    /**
     * Devuelve el usuario por su correo
     *
     * @param email el correo del usuario.
     * @return el UserDO.
     */
    UserDO findByEmail(String email);

    /**
     * Devuelve una lista de usuarios por su rol
     *
     * @param role el rol del usuario.
     * @return la lista de usuarios con ese rol.
     */
    List<UserDO> findByRole(String role);

    /**
     * Elimina una lista de usuarios por sus identificadores
     *
     * @param idList lista de identificadores.
     */
    void deleteByIdList(List<Integer> idList);
}
