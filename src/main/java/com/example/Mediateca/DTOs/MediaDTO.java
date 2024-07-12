package com.example.Mediateca.DTOs;

import com.example.Mediateca.Domain.MediaDO;

/**
 * Clase de Transferencia de Datos para el medio de un anuncio.
 */
public class MediaDTO {
    private int id;
    private String mediaName;

    // Getters y setters
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

    /**
     * Asigna los valores de un objeto MediaDO a este MediaDTO,
     * transformando y adaptando los datos del dominio
     * para su uso en la capa de logica de negocio.
     *
     * @param mediaDO El objeto de dominio MediaDO desde el cual se
     *                extraen los datos.
     */
    public void fromDO(MediaDO mediaDO) {
        this.setId(mediaDO.getId());
        this.setMediaName(mediaDO.getMediaName());

    }

}