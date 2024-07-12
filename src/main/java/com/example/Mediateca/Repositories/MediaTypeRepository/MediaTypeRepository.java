package com.example.Mediateca.Repositories.MediaTypeRepository;

import com.example.Mediateca.Domain.MediaTypeDO;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para manejar las operaciones en bbdd para los objetos de tipo
 * medio comunicacion MediaTypeDO.
 * Extiende de JpaRepository para usar sus funcionalidades basicas y ademas
 * integra nuevas funcionalidades definidas en
 * MediaTypeRepositoryCustom.
 */
@Repository
@Primary
public interface MediaTypeRepository extends JpaRepository<MediaTypeDO, Integer>, MediaTypeRepositoryCustom {

}