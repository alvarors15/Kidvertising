package com.example.Mediateca.Domain;

import com.example.Mediateca.DTOs.UserDTO;

import jakarta.persistence.*;

/**
 * Representa un usuario en la aplicacion.
 * Esta entidad está mapeada a la tabla 'USUARIOS' en la base de datos.
 */
@Entity
@Table(name = "USUARIOS")
public class UserDO {

    // Identificador del usuario.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USUARIO")
    private Integer id;

    // Nombre completo del usuario
    @Column(name = "NOMBRE")
    private String username;

    // Correo electronico del usuario.
    @Column(name = "CORREO", unique = true)
    private String email;

    // Contrasenia del usuario.
    @Column(name = "CONTRASENIA")
    private String password;

    // Rol del usuario (Profesor o Alumno).
    @Column(name = "ROL")
    private String rol;

    public UserDO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    /**
     * Asigna propiedades de un UserDTO a este objeto UserDO.
     *
     * @param userDTO que contiene informacion del usuario.
     */
    public void fromDTO(UserDTO userDTO) {

        this.setUsername(userDTO.getUsername());
        this.setEmail(userDTO.getEmail());
        this.setPassword(userDTO.getPassword());
        this.setRol(userDTO.getRol());
    }
}