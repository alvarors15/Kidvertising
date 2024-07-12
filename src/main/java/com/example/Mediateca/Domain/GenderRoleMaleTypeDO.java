package com.example.Mediateca.Domain;

import jakarta.persistence.*;

/**
 * Entidad que representa los diferentes roles que pueden tomar los hombres que
 * aparecen en los anuncios de la aplicacion
 * Esta entidad esta mapeada a la tabla 'ROL_GENERO_HOMBRE' en la base de datos.
 */
@Entity
@Table(name = "ROL_GENERO_HOMBRE")
public class GenderRoleMaleTypeDO {

    // Identificador del rol de genero del hombre.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ROL_GENERO_HOMBRE")
    private int id;

    // Tipo de rol de genero del hombre.
    @Column(name = "TIPO_ROL_GENERO_HOMBRE")
    private String genderRoleMaleType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGenderRoleMaleType() {
        return genderRoleMaleType;
    }

    public void setGenderRoleMaleType(String genderRoleMaleType) {
        this.genderRoleMaleType = genderRoleMaleType;
    }
}
