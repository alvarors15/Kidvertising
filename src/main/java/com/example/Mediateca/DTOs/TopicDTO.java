package com.example.Mediateca.DTOs;

import com.example.Mediateca.Domain.TopicDO;

/**
 * Clase de Transferencia de Datos para la tematica de un anuncio.
 */
public class TopicDTO {
    private int id;
    private String topicName;

    // Getters y setters
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

    /**
     * Asigna los valores de un objeto TopicDO a este TopicDTO,
     * transformando y adaptando los datos del dominio
     * para su uso en la capa de logica de negocio.
     *
     * @param topicDO El objeto de dominio TopicDO desde el cual se extraen los
     *                datos.
     */
    public void fromDO(TopicDO topicDO) {
        this.setId(topicDO.getId());
        this.setTopicName(topicDO.getTopicName());

    }

}