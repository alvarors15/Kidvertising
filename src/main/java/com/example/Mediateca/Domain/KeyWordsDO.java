package com.example.Mediateca.Domain;

import jakarta.persistence.*;

/**
 * Entidad que representa las palabras clave que pueden ir asociadas a un
 * anuncio
 * Esta entidad esta mapeada a la tabla 'PALABRAS_CLAVE' en la base de datos.
 */
@Entity
@Table(name = "PALABRAS_CLAVE")
public class KeyWordsDO {

    // Identificador de la palabra clave
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PALABRA_CLAVE")
    private int id;

    // La palabra clave.
    @Column(name = "PALABRA_CLAVE")
    private String word;

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
}
