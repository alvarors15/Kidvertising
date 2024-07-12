package com.example.Mediateca.DTOs;

import com.example.Mediateca.Domain.UserDO;

/**
 * Clase de Transferencia de Datos para las notificaciones que se envian a los
 * usuarios en la aplicacion.
 */
public class UserDTO {
    private Integer id;
    private String username;
    private String email;
    private String password;
    private String rol;

    public UserDTO() {
    }

    // Getters y setters
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

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Asigna los valores de un objeto UserDO a este UserDTO,
     * transformando y adaptando los datos del dominio
     * para su uso en la capa de logica de negocio.
     *
     * @param userDO El objeto de dominio UserDO desde el cual se extraen los datos.
     */
    public void fromDO(UserDO userDO) {
        this.setId(userDO.getId());
        this.setUsername(userDO.getUsername());
        this.setEmail(userDO.getEmail());
        this.setPassword(userDO.getPassword());
        this.setRol(userDO.getRol());
    }

}