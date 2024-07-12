package com.example.Mediateca.DTOs;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.Mediateca.Domain.AdDO;

/**
 * Clase de transferencia de datos para los anuncios en la aplicacion.
 * Este DTO encapsula la informacion de los anuncios permitiendo su facil
 * transporte entre la capa de logica
 * y la capa de presentacion.
 */

public class AdDTO {

    private Long id;
    private Integer year;
    private String product;
    private String advertiser;
    private String adType;
    private String media;
    private String mediaName;
    private String target;
    private Integer budget;
    private String objective;
    private String genderRoleMale;
    private String genderRoleFemale;

    private String childRole;
    private String childGender;
    private String protagonism;

    private String childRole2;
    private String childGender2;
    private String protagonism2;

    private String childRole3;
    private String childGender3;
    private String protagonism3;

    private String topicName;
    private String keyWords;
    private String synopsis;
    private String mediaUrl;
    private String genderRelation;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.S")
    private Date modificationDate;

    private Boolean isEditable = Boolean.FALSE;
    private String userEmail;
    private String username;
    private Integer userId;

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getAdvertiser() {
        return advertiser;
    }

    public void setAdvertiser(String advertiser) {
        this.advertiser = advertiser;
    }

    public String getAdType() {
        return adType;
    }

    public void setAdType(String adType) {
        this.adType = adType;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public String getMediaName() {
        return mediaName;
    }

    public void setMediaName(String mediaName) {
        this.mediaName = mediaName;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Integer getBudget() {
        return budget;
    }

    public void setBudget(Integer budget) {
        this.budget = budget;
    }

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public String getGenderRoleMale() {
        return genderRoleMale;
    }

    public void setGenderRoleMale(String genderRoleMale) {
        this.genderRoleMale = genderRoleMale;
    }

    public String getGenderRoleFemale() {
        return genderRoleFemale;
    }

    public void setGenderRoleFemale(String genderRoleFemale) {
        this.genderRoleFemale = genderRoleFemale;
    }

    public String getChildRole() {
        return childRole;
    }

    public void setChildRole(String childRole) {
        this.childRole = childRole;
    }

    public String getChildGender() {
        return childGender;
    }

    public void setChildGender(String childGender) {
        this.childGender = childGender;
    }

    public String getProtagonism() {
        return protagonism;
    }

    public void setProtagonism(String protagonism) {
        this.protagonism = protagonism;
    }

    public String getChildRole2() {
        return childRole2;
    }

    public void setChildRole2(String childRole2) {
        this.childRole2 = childRole2;
    }

    public String getChildGender2() {
        return childGender2;
    }

    public void setChildGender2(String childGender2) {
        this.childGender2 = childGender2;
    }

    public String getProtagonism2() {
        return protagonism2;
    }

    public void setProtagonism2(String protagonism2) {
        this.protagonism2 = protagonism2;
    }

    public String getChildRole3() {
        return childRole3;
    }

    public void setChildRole3(String childRole3) {
        this.childRole3 = childRole3;
    }

    public String getChildGender3() {
        return childGender3;
    }

    public void setChildGender3(String childGender3) {
        this.childGender3 = childGender3;
    }

    public String getProtagonism3() {
        return protagonism3;
    }

    public void setProtagonism3(String protagonism3) {
        this.protagonism3 = protagonism3;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public String getGenderRelation() {
        return genderRelation;
    }

    public void setGenderRelation(String genderRelation) {
        this.genderRelation = genderRelation;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }

    public Boolean getIsEditable() {
        return isEditable;
    }

    public void setIsEditable(Boolean isEditable) {
        this.isEditable = isEditable;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Asigna los valores de un objeto AdDO a este AdDTO,
     * transformando y adaptando los datos del dominio
     * para su uso en la capa de logica de negocio.
     *
     * @param adDO El objeto de dominio AdDO desde el cual se extraen los datos.
     */
    public void fromDO(AdDO adDO) {
        this.setId(adDO.getId());
        this.setYear(adDO.getYear());
        this.setProduct(adDO.getProduct());
        this.setAdvertiser(adDO.getAdvertiser());

        this.setAdType(adDO.getAdType());
        if (adDO.getMediaTypeDO() != null) {
            this.setMedia(adDO.getMediaTypeDO().getMediaType());
        }
        if (adDO.getMediaDO() != null) {
            this.setMediaName(adDO.getMediaDO().getMediaName());
        }
        this.setTarget(adDO.getTarget());
        this.setBudget(adDO.getBudget());
        this.setObjective(adDO.getObjective());
        if (adDO.getGenderRoleMaleTypeDO() != null) {
            this.setGenderRoleMale(adDO.getGenderRoleMaleTypeDO().getGenderRoleMaleType());
        }
        if (adDO.getGenderRoleFemaleTypeDO() != null) {
            this.setGenderRoleFemale(adDO.getGenderRoleFemaleTypeDO().getGenderRoleFemaleType());
        }

        this.setChildGender(adDO.getChildGender());
        if (adDO.getChildRoleTypeDO() != null) {
            this.setChildRole(adDO.getChildRoleTypeDO().getChildRole());
        }
        this.setProtagonism(adDO.getProtagonism());

        this.setChildGender2(adDO.getChildGender2());
        if (adDO.getChildRoleTypeDO2() != null) {
            this.setChildRole2(adDO.getChildRoleTypeDO2().getChildRole());
        }
        this.setProtagonism2(adDO.getProtagonism2());

        this.setChildGender3(adDO.getChildGender3());
        if (adDO.getChildRoleTypeDO3() != null) {
            this.setChildRole3(adDO.getChildRoleTypeDO3().getChildRole());
        }
        this.setProtagonism3(adDO.getProtagonism3());

        if (adDO.getTopicDO() != null) {
            this.setTopicName(adDO.getTopicDO().getTopicName());
        }

        this.setKeyWords(adDO.getKeyWords());

        this.setSynopsis(adDO.getSynopsis());
        this.setMediaUrl(adDO.getMediaUrl());
        this.setGenderRelation(adDO.getGenderRelation());

        if (adDO.getUser() != null) {
            this.setUserEmail(adDO.getUser().getEmail());
            this.setUsername(adDO.getUser().getUsername());
            this.setUserId(adDO.getUser().getId());
        }

        this.setModificationDate(adDO.getModificationDate());

        // Si han pasado menos de 7 dias, se puede editar el anuncio
        if (adDO.getModificationDate() != null) {
            Date ahora = new Date();
            long diff = ahora.getTime() - adDO.getModificationDate().getTime();
            this.setIsEditable(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) < 7);
        }

    }

}