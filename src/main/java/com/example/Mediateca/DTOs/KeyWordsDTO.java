package com.example.Mediateca.DTOs;

import com.example.Mediateca.Domain.KeyWordsDO;

/**
 * Clase de Transferencia de Datos para las palabras clave de un anuncio.
 */
public class KeyWordsDTO {
    private int id;
    private String word;

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    /**
     * Asigna los valores de un objeto KeyWordsDO a este KeyWordsDTO,
     * transformando y adaptando los datos del dominio
     * para su uso en la capa de logica de negocio.
     *
     * @param keyWordsDO El objeto de dominio KeyWordsDO desde el cual se
     *                   extraen los datos.
     */
    public void fromDO(KeyWordsDO keyWordsDO) {
        this.setId(keyWordsDO.getId());
        this.setWord(keyWordsDO.getWord());

    }

}