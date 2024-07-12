package com.example.Mediateca.Repositories.NotificationRepository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Mediateca.Domain.NotificationDO;

/**
 * Repositorio para manejar las operaciones en bbdd para los objetos de
 * notificacion NotificationDO.
 * Extiende de JpaRepository para usar sus funcionalidades basicas y ademas
 * integra nuevas funcionalidades definidas en
 * NotificationRepositoryCustom.
 */
@Repository
public interface NotificationRepository extends JpaRepository<NotificationDO, Integer>, NotificationRepositoryCustom {

}
