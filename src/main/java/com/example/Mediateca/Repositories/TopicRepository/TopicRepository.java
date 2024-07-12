package com.example.Mediateca.Repositories.TopicRepository;

import com.example.Mediateca.Domain.TopicDO;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para manejar las operaciones en bbdd para los objetos de
 * tematicas TopicDO.
 * Extiende de JpaRepository para usar sus funcionalidades basicas y ademas
 * integra nuevas funcionalidades definidas en
 * TopicRepositoryCustom.
 */
@Repository
@Primary
public interface TopicRepository
        extends JpaRepository<TopicDO, Integer>, TopicRepositoryCustom {

}