package com.example.Mediateca.Domain;

import jakarta.persistence.*;

/**
 * Entidad que representa los diferentes roles que pueden tomar los menores que
 * aparecen en los anuncios de la aplicacion.
 * Esta entidad esta mapeada a la tabla 'ROL_MENOR' en la base de datos.
 */
@Entity
@Table(name = "ROL_MENOR")
public class ChildRoleTypeDO {

    // Identificador del rol del menor.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ROL_MENOR")
    private int id;

    // Tipo de rol del menor.
    @Column(name = "TIPO_ROL_MENOR")
    private String childRole;

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
}
