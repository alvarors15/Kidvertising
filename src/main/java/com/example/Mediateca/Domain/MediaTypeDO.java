package com.example.Mediateca.Domain;

import jakarta.persistence.*;

/**
 * Entidad que representa los tipos de medios de comunicacion en los que se
 * pueden anunciar los anuncios.
 * Esta entidad esta mapeada a la tabla 'MEDIO_COMUNICACION' en la base de
 * datos.
 */
@Entity
@Table(name = "MEDIO_COMUNICACION")
public class MediaTypeDO {

    // Identificador del medio de comunicacion
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_MEDIO_COMUNICACION")
    private int id;

    // Tipo de medio de comunicacion
    @Column(name = "TIPO_MEDIO_COMUNICACION")
    private String mediaType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }
}
