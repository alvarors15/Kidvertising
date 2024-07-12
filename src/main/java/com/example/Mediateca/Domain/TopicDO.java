package com.example.Mediateca.Domain;

import jakarta.persistence.*;

/**
 * Entidad que representa las tematicas que puede tener un anuncio.
 * Esta entidad esta mapeada a la tabla 'TEMATICAS' en la base de
 * datos.
 */
@Entity
@Table(name = "TEMATICAS")
public class TopicDO {

    // Identificador de la tematica
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TEMATICA")
    private int id;

    // Nombre de la tematica
    @Column(name = "NOMBRE_TEMATICA")
    private String topicName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }
}
