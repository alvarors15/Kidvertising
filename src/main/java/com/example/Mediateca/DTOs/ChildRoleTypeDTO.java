package com.example.Mediateca.DTOs;

import com.example.Mediateca.Domain.ChildRoleTypeDO;

/**
 * Clase de Transferencia de Datos para los roles de menores.
 */
public class ChildRoleTypeDTO {
    private int id;
    private String childRole;

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChildRole() {
        return childRole;
    }

    public void setChildRole(String childRole) {
        this.childRole = childRole;
    }

    /**
     * Asigna los valores de un objeto ChildRoleTypeDO a este
     * ChildRoleTypeDTO,
     * transformando y adaptando los datos del dominio
     * para su uso en la capa de logica de negocio.
     *
     * @param childRoleTypeDO El objeto de dominio ChildRoleTypeDO desde el cual se
     *                        extraen los datos.
     */
    public void fromDO(ChildRoleTypeDO childRoleTypeDO) {
        this.setId(childRoleTypeDO.getId());
        this.setChildRole(childRoleTypeDO.getChildRole());

    }

}