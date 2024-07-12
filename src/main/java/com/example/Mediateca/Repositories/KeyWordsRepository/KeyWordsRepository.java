package com.example.Mediateca.Repositories.KeyWordsRepository;

import com.example.Mediateca.Domain.KeyWordsDO;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para manejar las operaciones en bbdd para los objetos de palabras
 * clave KeyWordsDO.
 * Extiende de JpaRepository para usar sus funcionalidades basicas y ademas
 * integra nuevas funcionalidades definidas en
 * KeyWordsRepositoryCustom.
 */
@Repository
@Primary
public interface KeyWordsRepository
                extends JpaRepository<KeyWordsDO, Integer>, KeyWordsRepositoryCustom {

}