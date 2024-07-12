package com.example.Mediateca.Repositories.MediaRepository;

import com.example.Mediateca.Domain.MediaDO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para manejar las operaciones en bbdd para los objetos de medios
 * MediaDO.
 * Extiende de JpaRepository para usar sus funcionalidades basicas y ademas
 * integra nuevas funcionalidades definidas en
 * MediaRepositoryCustom.
 */
@Repository
public interface MediaRepository extends JpaRepository<MediaDO, Integer>, MediaRepositoryCustom {

}