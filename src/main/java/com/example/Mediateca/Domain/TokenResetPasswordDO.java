package com.example.Mediateca.Domain;

import java.time.LocalDateTime;

import jakarta.persistence.*;

/**
 * Entidad que representa un token de reinicio de contrasenia en la aplicacion.
 * Este token es utilizado para gestionar la autenticacion de los usuarios que
 * desean restablecer su contrasenia
 * La entidad está mapeada a la tabla 'TOKENS_REINICIO_PASSWORD' en la base de
 * datos.
 */
@Entity
@Table(name = "TOKENS_REINICIO_PASSWORD")
public class TokenResetPasswordDO {

    // Identificador del token.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TOKEN")
    private Long tokenId;

    // Identificador del usuario que pide reestablecer su contrania.
    @Column(name = "ID_USUARIO")
    private Integer userId;

    // Cadena unica del token.
    @Column(name = "TOKEN")
    private String token;

    // Fecha en la que expira el token.
    @Column(name = "FECHA_EXPIRACION")
    private LocalDateTime expirationDate;

    // Fecha en la que se crea el token.
    @Column(name = "FECHA_CREACION")
    private LocalDateTime creationDate = LocalDateTime.now();

    // Indica si el token ya ha sido usado para reestablecer la contrasenia.
    @Column(name = "USADO")
    private Boolean used = false;

    public TokenResetPasswordDO() {
    }

    public Long getTokenId() {
        return tokenId;
    }

    public void setTokenId(Long tokenId) {
        this.tokenId = tokenId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Boolean getUsed() {
        return used;
    }

    public void setUsed(Boolean used) {
        this.used = used;
    }
}