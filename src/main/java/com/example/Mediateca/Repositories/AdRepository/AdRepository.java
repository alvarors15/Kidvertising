package com.example.Mediateca.Repositories.AdRepository;

import com.example.Mediateca.Domain.AdDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para manejar las operaciones en bbdd para los objetos de anuncios
 * AdDO.
 * Extiende de JpaRepository para usar sus funcionalidades basicas y ademas
 * integra nuevas funcionalidades definidas en
 * AdRepositoryCustom.
 */
@Repository
public interface AdRepository extends JpaRepository<AdDO, Long>, AdRepositoryCustom {

}