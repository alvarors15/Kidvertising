package com.example.Mediateca.Domain;

import java.util.Date;

import jakarta.persistence.*;

/**
 * Entidad que representa una notificacion enviada a un usuario en la
 * aplicacion.
 * Las notificaciones tienen el usuario que la ha enviado y el anuncio dobre el
 * que trata la notificacion.
 * Esta entidad está mapeada a la tabla 'NOTIFICACIONES' en la base de datos.
 */
@Entity
@Table(name = "NOTIFICACIONES")
public class NotificationDO {

    // Identificador de la notificacion.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_NOTIFICACION")
    private int id;

    // Usuario que envia la notificacion.
    @ManyToOne
    @JoinColumn(name = "ID_USUARIO")
    private UserDO user;

    // Anuncio sobre el que trata la notificacion.
    @ManyToOne
    @JoinColumn(name = "ID_ANUNCIO")
    private AdDO ad;

    @Column(name = "MENSAJE")
    private String message;

    // Estado de la notificacion (Aprobada, Rechazada o Pendiente)
    @Column(name = "ESTADO")
    private String status;

    // Fecha en la que se resuelve la notificacion.
    @Column(name = "FECHA_RESOLUCION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date checkedDate = new Date();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserDO getUser() {
        return user;
    }

    public void setUser(UserDO user) {
        this.user = user;
    }

    public AdDO getAd() {
        return ad;
    }

    public void setAd(AdDO ad) {
        this.ad = ad;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCheckedDate() {
        return checkedDate;
    }

    public void setCheckedDate(Date checkedDate) {
        this.checkedDate = checkedDate;
    }

}