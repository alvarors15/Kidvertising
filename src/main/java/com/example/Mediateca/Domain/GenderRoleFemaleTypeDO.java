package com.example.Mediateca.Domain;

import jakarta.persistence.*;

/**
 * Entidad que representa los diferentes roles que pueden tomar las mujeres que
 * aparecen en los anuncios de la aplicacion
 * Esta entidad esta mapeada a la tabla 'ROL_GENERO_MUJER' en la base de datos.
 */
@Entity
@Table(name = "ROL_GENERO_MUJER")
public class GenderRoleFemaleTypeDO {

    // Identificador del rol de genero de la mujer.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ROL_GENERO_MUJER")
    private int id;

    // Tipo de rol de genero de la mujer.
    @Column(name = "TIPO_ROL_GENERO_MUJER")
    private String genderRoleFemaleType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGenderRoleFemaleType() {
        return genderRoleFemaleType;
    }

    public void setGenderRoleFemaleType(String genderRoleFemaleType) {
        this.genderRoleFemaleType = genderRoleFemaleType;
    }
}
