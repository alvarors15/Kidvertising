package com.example.Mediateca.DTOs;

import com.example.Mediateca.Domain.MediaTypeDO;

/**
 * Clase de Transferencia de Datos para el tipo de medio de comunicacion de un
 * anuncio.
 */
public class MediaTypeDTO {
    private int id;
    private String mediaType;

    // Getters y setters
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

    /**
     * Asigna los valores de un objeto MediaTypeDO a este MediaTypeDTO,
     * transformando y adaptando los datos del dominio
     * para su uso en la capa de logica de negocio.
     *
     * @param mediaTypeDO El objeto de dominio MediaTypeDO desde el cual se
     *                    extraen los datos.
     */
    public void fromDO(MediaTypeDO mediaTypeDO) {
        this.setId(mediaTypeDO.getId());
        this.setMediaType(mediaTypeDO.getMediaType());

    }

}