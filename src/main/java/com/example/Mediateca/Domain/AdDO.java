package com.example.Mediateca.Domain;

import java.util.Date;

import com.example.Mediateca.DTOs.AdDTO;

import jakarta.persistence.*;

/**
 * Entidad que representa a un anuncio en la aplicacion, conteniendo toda la
 * informacion de este.
 * Esta entidad esta mapeada a la tabla 'ANUNCIOS' en la base de datos.
 */
@Entity
@Table(name = "ANUNCIOS")
public class AdDO {

    // Identificador del anuncio.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ANUNCIO")
    private Long id;

    // Anio de publicacion del anuncio.
    @Column(name = "ANIO")
    private Integer year;

    // Tipo de producto que se anuncia
    @Column(name = "PRODUCTO")
    private String product;

    // El anunciante.
    @Column(name = "ANUNCIANTE")
    private String advertiser;

    // Tipo de publicidad.
    @Column(name = "TIPO_PUBLICIDAD")
    private String adType;

    // Publico objetivo del anuncio.
    @Column(name = "TARGET")
    private String target;

    // Presupuesto del anuncio.
    @Column(name = "PRESUPUESTO")
    private Integer budget;

    // Objetivo del anuncio.
    @Column(name = "OBJETIVO")
    private String objective;

    // Rol del primer menor.
    @ManyToOne
    @JoinColumn(name = "ROL_MENOR_1")
    private ChildRoleTypeDO childRoleTypeDO;

    // Genero del primer menor.
    @Column(name = "GENERO_MENOR_1")
    private String childGender;

    // Protagonismo del primer menor.
    @Column(name = "PROTAGONISMO_MENOR_1")
    private String protagonism;

    // Rol del segundo menor.
    @ManyToOne
    @JoinColumn(name = "ROL_MENOR_2")
    private ChildRoleTypeDO childRoleTypeDO2;

    // Genero del segundo menor.
    @Column(name = "GENERO_MENOR_2")
    private String childGender2;

    // Protagonismo del segundo menor.
    @Column(name = "PROTAGONISMO_MENOR_2")
    private String protagonism2;

    // Rol del tercer menor.
    @ManyToOne
    @JoinColumn(name = "ROL_MENOR_3")
    private ChildRoleTypeDO childRoleTypeDO3;

    // Genero del tercer menor.
    @Column(name = "GENERO_MENOR_3")
    private String childGender3;

    // Protagonismo del tercer menor.
    @Column(name = "PROTAGONISMO_MENOR_3")
    private String protagonism3;

    // Sinopsis del anuncio.
    @Column(name = "SINOPSIS")
    private String synopsis;

    // URL en el que se encuentra el archivo multimedia del anuncio.
    @Column(name = "MULTIMEDIA_URL")
    private String mediaUrl;

    // Relacion de genero entre los participantes del anuncio
    @Column(name = "RELACION_GENERO")
    private String genderRelation;

    // Rol de genero del hombre.
    @ManyToOne
    @JoinColumn(name = "ROL_GENERO_HOMBRE")
    private GenderRoleMaleTypeDO genderRoleMaleTypeDO;

    // Rol de genero de la mujer
    @ManyToOne
    @JoinColumn(name = "ROL_GENERO_MUJER")
    private GenderRoleFemaleTypeDO genderRoleFemaleTypeDO;

    // Nombre del medio en el que se anuncia.
    @ManyToOne
    @JoinColumn(name = "NOMBRE_MEDIO")
    private MediaDO mediaDO;

    // Tipo de medio en el que se anuncia.
    @ManyToOne
    @JoinColumn(name = "TIPO_MEDIO_COMUNICACION")
    private MediaTypeDO mediaTypeDO;

    // Tematica del anuncio.
    @ManyToOne
    @JoinColumn(name = "TEMATICA")
    private TopicDO topicDO;

    // Palabras clave asociadas al anuncio.
    @Column(name = "PALABRAS_CLAVE")
    private String keyWords;

    // Usuario que ha creado el anuncio.
    @ManyToOne(optional = true)
    @JoinColumn(name = "ID_USUARIO", nullable = true)
    private UserDO user;

    // Fecha de moficiacion del anuncio.
    @Column(name = "FECHA_MODIFICACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificationDate;

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

    public ChildRoleTypeDO getChildRoleTypeDO() {
        return childRoleTypeDO;
    }

    public void setChildRoleTypeDO(ChildRoleTypeDO childRoleTypeDO) {
        this.childRoleTypeDO = childRoleTypeDO;
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

    public ChildRoleTypeDO getChildRoleTypeDO2() {
        return childRoleTypeDO2;
    }

    public void setChildRoleTypeDO2(ChildRoleTypeDO childRoleTypeDO2) {
        this.childRoleTypeDO2 = childRoleTypeDO2;
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

    public ChildRoleTypeDO getChildRoleTypeDO3() {
        return childRoleTypeDO3;
    }

    public void setChildRoleTypeDO3(ChildRoleTypeDO childRoleTypeDO3) {
        this.childRoleTypeDO3 = childRoleTypeDO3;
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

    public GenderRoleMaleTypeDO getGenderRoleMaleTypeDO() {
        return genderRoleMaleTypeDO;
    }

    public void setGenderRoleMaleTypeDO(GenderRoleMaleTypeDO genderRoleMaleTypeDO) {
        this.genderRoleMaleTypeDO = genderRoleMaleTypeDO;
    }

    public GenderRoleFemaleTypeDO getGenderRoleFemaleTypeDO() {
        return genderRoleFemaleTypeDO;
    }

    public void setGenderRoleFemaleTypeDO(GenderRoleFemaleTypeDO genderRoleFemaleTypeDO) {
        this.genderRoleFemaleTypeDO = genderRoleFemaleTypeDO;
    }

    public TopicDO getTopicDO() {
        return topicDO;
    }

    public void setTopicDO(TopicDO topicDO) {
        this.topicDO = topicDO;
    }

    public MediaDO getMediaDO() {
        return mediaDO;
    }

    public void setMediaDO(MediaDO mediaDO) {
        this.mediaDO = mediaDO;
    }

    public MediaTypeDO getMediaTypeDO() {
        return mediaTypeDO;
    }

    public void setMediaTypeDO(MediaTypeDO mediaTypeDO) {
        this.mediaTypeDO = mediaTypeDO;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }

    public UserDO getUser() {
        return user;
    }

    public void setUser(UserDO user) {
        this.user = user;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    /**
     * Asigna propiedades de un AdDTO a este objeto AdDO.
     *
     * @param adDTO  DTO que contiene informacion del anuncio.
     * @param userDO Usuario que ha creado el anuncio.
     */
    public void fromDTO(AdDTO adDTO, UserDO userDO) {
        this.setId(adDTO.getId());
        this.setYear(adDTO.getYear());
        this.setProduct(adDTO.getProduct());
        this.setAdvertiser(adDTO.getAdvertiser());

        this.setAdType(adDTO.getAdType());
        this.setTarget(adDTO.getTarget());
        this.setBudget(adDTO.getBudget());
        this.setObjective(adDTO.getObjective());

        this.setChildGender(adDTO.getChildGender());
        this.setProtagonism(adDTO.getProtagonism());

        this.setChildGender2(adDTO.getChildGender2());
        this.setProtagonism2(adDTO.getProtagonism2());

        this.setChildGender3(adDTO.getChildGender3());
        this.setProtagonism3(adDTO.getProtagonism3());

        this.setSynopsis(adDTO.getSynopsis());
        this.setMediaUrl(adDTO.getMediaUrl());
        this.setKeyWords(adDTO.getKeyWords());
        this.setModificationDate(adDTO.getModificationDate());
        this.setUser(userDO);
        this.setGenderRelation(adDTO.getGenderRelation());

    }

}