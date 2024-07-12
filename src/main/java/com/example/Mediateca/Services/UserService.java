package com.example.Mediateca.Services;

import com.example.Mediateca.Domain.TokenResetPasswordDO;
import com.example.Mediateca.Domain.UserDO;
import com.example.Mediateca.DTOs.UserDTO;
import com.example.Mediateca.Repositories.UserRepository.UserRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Servicio que proporciona operaciones para manejar los usuarios dentro de la
 * aplicacion.
 * Incluye operaciones de recuperacion, creacion, actualizacion y eliminacion
 * de usuarios.
 */
@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Recupera todos los usuarios excepto el usuario actualmente autentificado.
     *
     * @param actualUserId el identificador del usuario actual para excluir de la
     *                     lista de resultados.
     * @return Una lista de usuarios con todos los usuarios menos el actual.
     */
    public List<UserDTO> getAllUsersButActual(Integer actualUserId) {
        List<UserDO> userDOList = userRepository.findAllButActual(actualUserId);
        List<UserDTO> listUserDTO = new ArrayList<UserDTO>();
        for (UserDO userDO : userDOList) {
            UserDTO userDTO = new UserDTO();
            userDTO.fromDO(userDO);
            listUserDTO.add(userDTO);
        }
        return listUserDTO;
    }

    /**
     * Recupera un UserDTO por su correo electronico.
     *
     * @param email el correo electronico del usuario a buscar.
     * @return un UserDTO o null si no se encuentra.
     */
    public UserDTO getUserByEmail(String email) {
        UserDO userDO = userRepository.findByEmail(email);
        UserDTO userDTO = new UserDTO();
        userDTO.fromDO(userDO);
        return userDTO;
    }

    /**
     * Recupera un DTO del usuario por su identificador.
     *
     * @param id el identificador del usuario a buscar.
     * @return el usuario encontrado con el id o null si el usuario no existe.
     */
    public UserDTO getDTOById(Integer id) {
        UserDO userDO = userRepository.findUserById(id);
        if (userDO == null) {
            return null;
        }
        UserDTO userDTO = new UserDTO();
        userDTO.fromDO(userDO);
        return userDTO;
    }

    /**
     * Recupera un UserDO por su correo electronico.
     *
     * @param email El correo electrónico del usuario a buscar.
     * @return un UserDO o null si no se encuentra.
     */
    public UserDO getDOByEmail(String email) {
        UserDO userDO = userRepository.findByEmail(email);
        if (userDO == null) {
            return null;
        }
        return userDO;
    }

    /**
     * Elimina una lista de usuarios por sus identificadores.
     *
     * @param idList lista de identificadores de los usuarios a eliminar.
     */
    @SuppressWarnings("null")
    public void deleteUserList(List<Integer> idList) {
        userRepository.deleteByIdList(idList);
    }

    /**
     * Guarda o actualiza un usuario en la base de datos.
     *
     * @param userDO el usuario a guardar o actualizar.
     */
    @SuppressWarnings("null")
    public void saveUser(UserDO userDO) {
        userRepository.save(userDO);
    }

    /**
     * Actualiza la contrasenia de un usuario basado en un token de restablecimiento
     * de contrasenia.
     * Este metodo sirve para el restablecimiento de contrasenias.
     *
     * @param tokenResetPasswordDO el token asociado con la solicitud de
     *                             restablecimiento de contrasenia
     * @param password             la nueva contrasenia a establecer.
     */
    @SuppressWarnings("null")
    public void updateUserPasswordWithToken(TokenResetPasswordDO tokenResetPasswordDO, String password) {
        UserDO userDO = userRepository.findUserById(tokenResetPasswordDO.getUserId());
        userDO.setPassword(passwordEncoder.encode(password));
        userRepository.save(userDO);
    }

    /**
     * Actualiza la contrasenia de un usuario.
     * Este metodo sirve para el registro del usuario
     *
     * @param userDO   el usuario cuya contraseña sera actualizada.
     * @param password la contrasenia a establecer.
     */
    @SuppressWarnings("null")
    public void updateUserPassword(UserDO userDO, String password) {
        userDO.setPassword(passwordEncoder.encode(password));
        userRepository.save(userDO);
    }

    /**
     * Cambia el rol de un usuario.
     *
     * @param userId el identificador del usuario cuyo rol sera actualizado.
     * @return el usuario con el rol actualizado.
     * @throws Exception Si el usuario no existe.
     */
    @Transactional
    public UserDO updateUserRol(Integer userId) throws Exception {
        UserDO userDO = userRepository.findUserById(userId);
        if (userDO == null) {
            throw new Exception("El usuario no existe");
        }
        // Si el usuario es un Profesor, se le cambia el rol a Alumno y viceversa
        String rol = "ROLE_" + (userDO.getRol().equals("ROLE_PROFESOR") ? "ALUMNO" : "PROFESOR");

        userDO.setRol(rol);
        userRepository.saveAndFlush(userDO);
        return userDO;
    }
}