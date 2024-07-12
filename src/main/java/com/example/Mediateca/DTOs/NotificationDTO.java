package com.example.Mediateca.DTOs;

import java.util.Date;

import com.example.Mediateca.Domain.NotificationDO;

/**
 * Clase de Transferencia de Datos para las notificaciones que se envian a los
 * usuarios en la aplicacion.
 */
public class NotificationDTO {
    private Integer id;
    private String username;
    private Integer userId;
    private Long adId;
    private String message;
    private String status;

    private Date checkedDate;

    public NotificationDTO() {
    }

    // Getters y setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Long getAdId() {
        return adId;
    }

    public void setAdId(Long adId) {
        this.adId = adId;
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

    /**
     * Asigna los valores de un objeto NotificationDO a este NotificationDTO,
     * transformando y adaptando los datos del dominio
     * para su uso en la capa de logica de negocio.
     *
     * @param notificationDO El objeto de dominio NotificationDO desde el cual se
     *                       extraen los datos.
     */
    public void fromDO(NotificationDO notificationDO) {
        this.setId(notificationDO.getId());
        this.setUsername(notificationDO.getUser().getUsername());
        this.setUserId(notificationDO.getUser().getId());
        this.setAdId(notificationDO.getAd().getId());
        this.setMessage(notificationDO.getMessage());
        this.setStatus(notificationDO.getStatus());
        this.setCheckedDate(notificationDO.getCheckedDate());
    }

}