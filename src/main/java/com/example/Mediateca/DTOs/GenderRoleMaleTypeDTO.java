package com.example.Mediateca.DTOs;

import com.example.Mediateca.Domain.GenderRoleMaleTypeDO;

/**
 * Clase de Transferencia de Datos para los roles de genero del hombre.
 */
public class GenderRoleMaleTypeDTO {
    private int id;
    private String genderMaleRole;

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGenderMaleRole() {
        return genderMaleRole;
    }

    public void setGenderMaleRole(String genderMaleRole) {
        this.genderMaleRole = genderMaleRole;
    }

    /**
     * Asigna los valores de un objeto GenderRoleMaleTypeDO a este
     * GenderRoleMaleTypeDTO,
     * transformando y adaptando los datos del dominio
     * para su uso en la capa de logica de negocio.
     *
     * @param genderRoleMaleTypeDO El objeto de dominio GenderRoleMaleTypeDO desde
     *                             el cual se extraen los datos.
     */
    public void fromDO(GenderRoleMaleTypeDO genderRoleMaleTypeDO) {
        this.setId(genderRoleMaleTypeDO.getId());
        this.setGenderMaleRole(genderRoleMaleTypeDO.getGenderRoleMaleType());

    }

}