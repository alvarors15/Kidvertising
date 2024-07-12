package com.example.Mediateca.Domain;

import jakarta.persistence.*;

/**
 * Entidad que representa los medios en los que se pueden anunciar los anuncios.
 * Esta entidad esta mapeada a la tabla 'MEDIOS' en la base de datos.
 */
@Entity
@Table(name = "MEDIOS")
public class MediaDO {

    // Identificador del medio
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_MEDIO")
    private int id;

    // Nombre del medio
    @Column(name = "NOMBRE_MEDIO")
    private String mediaName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMediaName() {
        return mediaName;
    }

    public void setMediaName(String mediaName) {
        this.mediaName = mediaName;
    }
}
