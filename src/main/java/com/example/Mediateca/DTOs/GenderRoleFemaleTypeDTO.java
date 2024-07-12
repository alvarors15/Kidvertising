package com.example.Mediateca.DTOs;

import com.example.Mediateca.Domain.GenderRoleFemaleTypeDO;

/**
 * Clase de Transferencia de Datos para los roles de genero de la mujer.
 */
public class GenderRoleFemaleTypeDTO {
    private int id;
    private String genderFemaleRole;

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGenderFemaleRole() {
        return genderFemaleRole;
    }

    public void setGenderFemaleRole(String genderFemaleRole) {
        this.genderFemaleRole = genderFemaleRole;
    }

    /**
     * Asigna los valores de un objeto GenderRoleFemaleTypeDO a este
     * GenderRoleFemaleTypeDTO,
     * transformando y adaptando los datos del dominio
     * para su uso en la capa de logica de negocio.
     *
     * @param genderRoleFemaleTypeDO El objeto de dominio GenderRoleFemaleTypeDO
     *                               desde el cual se extraen los datos.
     */
    public void fromDO(GenderRoleFemaleTypeDO genderRoleFemaleTypeDO) {
        this.setId(genderRoleFemaleTypeDO.getId());
        this.setGenderFemaleRole(genderRoleFemaleTypeDO.getGenderRoleFemaleType());

    }

}