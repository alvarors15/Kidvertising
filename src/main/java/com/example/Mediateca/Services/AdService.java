package com.example.Mediateca.Services;

import com.example.Mediateca.Domain.AdDO;
import com.example.Mediateca.Domain.ChildRoleTypeDO;
import com.example.Mediateca.Domain.GenderRoleFemaleTypeDO;
import com.example.Mediateca.Domain.GenderRoleMaleTypeDO;
import com.example.Mediateca.Domain.MediaDO;
import com.example.Mediateca.Domain.MediaTypeDO;
import com.example.Mediateca.Domain.TopicDO;
import com.example.Mediateca.Domain.UserDO;
import com.example.Mediateca.Repositories.AdRepository.AdRepository;
import com.example.Mediateca.Utils.FileStorageUtils;
import com.example.Mediateca.DTOs.AdDTO;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Servicio para manejar todas las operaciones relacionadas con anuncios.
 * Este servicio ofrece metodos para obtener, crear, modificar y eliminar
 * anuncios, asi como para obtener estadisticas.
 */
@Service
@Transactional
public class AdService {

    @Autowired
    private AdRepository adRepository;

    // Servicios las categorias
    @Autowired
    private ChildRoleTypeService childRoleTypeService;
    @Autowired
    private GenderRoleMaleTypeService genderRoleMaleTypeService;
    @Autowired
    private GenderRoleFemaleTypeService genderRoleFemaleTypeService;
    @Autowired
    private MediaService mediaService;
    @Autowired
    private MediaTypeService mediaTypeService;
    @Autowired
    private UserService userService;
    @Autowired
    private TopicService topicService;
    @Autowired
    private KeyWordsService keyWordsService;

    // Servicio de almacenaje de archivos
    @Autowired
    private FileStorageUtils fileStorageService;

    /**
     * Obtiene todos los anuncios de la base de datos.
     *
     * @return una lista de AdDTO.
     */
    public List<AdDTO> getAllAds() {
        List<AdDO> adDOList = adRepository.findAll();
        List<AdDTO> listAdDTO = new ArrayList<AdDTO>();
        for (AdDO adDO : adDOList) {
            AdDTO adDTO = new AdDTO();
            adDTO.fromDO(adDO);
            listAdDTO.add(adDTO);
        }
        return listAdDTO;
    }

    /**
     * Obtiene un anuncio por su identificador en forma de AdDTO.
     *
     * @param id identificador del anuncio.
     * @return AdDTO del anuncio.
     */
    public AdDTO getAdById(Long id) {
        AdDO adDO = adRepository.findAdById(id);
        AdDTO adDTO = new AdDTO();
        adDTO.fromDO(adDO);
        return adDTO;
    }

    /**
     * Obtiene un anuncio por su identificador en forma de AdDO.
     *
     * @param id identificador del anuncio.
     * @return AdDO del anuncio.
     */
    public AdDO getAdDOById(Long id) {
        AdDO adDO = adRepository.findAdById(id);
        return adDO;
    }

    /**
     * Elimina un anuncio de la base de datos por su identificador.
     *
     * @param id identificador del anuncio a eliminar.
     */
    public void deleteAd(Long id) {
        adRepository.deleteById(id);
    }

    /**
     * Elimina todos los anuncios de un usuario de la base de datos.
     *
     * @param userId identificador usuario.
     * @return el numero de anuncios eliminados.
     */
    public Integer deleteAllUserAds(Integer userId) {
        Integer deletedCount = adRepository.deleteByUserId(userId);
        return deletedCount;
    }

    /**
     * Actualiza la informacion de un anuncio en la base de datos.
     *
     * @param adDO el objeto AdDO del anuncio a actualizar.
     * @return el AdDO actualizado.
     */
    @SuppressWarnings("null")
    public AdDO update(AdDO adDO) {
        return adRepository.save(adDO);
    }

    /**
     * Obtiene una lista de anuncios creados por un usuario.
     *
     * @param userId identificador del usuario.
     * @return la lista de AdDTO de anuncios del usuario.
     */
    public List<AdDTO> getByUser(Integer userId) {
        List<AdDO> listAdDO = adRepository.findAdByUserId(userId);
        List<AdDTO> listAdDTO = new ArrayList<AdDTO>();
        for (AdDO adDO : listAdDO) {
            AdDTO adDTO = new AdDTO();
            adDTO.fromDO(adDO);
            listAdDTO.add(adDTO);
        }
        return listAdDTO;
    }

    /**
     * Obtiene los ultimas archivos de videos, imagenes y audios que se han subido a
     * la aplicacion y los agrupa
     * en un mapa por tipo de multimedia.
     *
     * @return un mapa que contiene listas de AdDTO para videos, imagenes y
     *         audios.
     */
    public Map<String, List<AdDTO>> getLastsMultimedia() {
        Map<String, List<AdDTO>> adByMultimediaMap = new HashMap<>();
        List<AdDO> listAdDOVideos = adRepository.findLastsVideos();
        List<AdDO> listAdDOImages = adRepository.findLastsImages();
        List<AdDO> listAdDOAudios = adRepository.findLastsAudios();
        List<AdDTO> listAdDTOVideos = new ArrayList<AdDTO>();
        List<AdDTO> listAdDTOImages = new ArrayList<AdDTO>();
        List<AdDTO> listAdDTOAudios = new ArrayList<AdDTO>();
        for (AdDO adDO : listAdDOVideos) {
            AdDTO adDTO = new AdDTO();
            adDTO.fromDO(adDO);
            listAdDTOVideos.add(adDTO);
        }
        for (AdDO adDO : listAdDOImages) {
            AdDTO adDTO = new AdDTO();
            adDTO.fromDO(adDO);
            listAdDTOImages.add(adDTO);
        }
        for (AdDO adDO : listAdDOAudios) {
            AdDTO adDTO = new AdDTO();
            adDTO.fromDO(adDO);
            listAdDTOAudios.add(adDTO);
        }
        adByMultimediaMap.put("Videos", listAdDTOVideos);
        adByMultimediaMap.put("Images", listAdDTOImages);
        adByMultimediaMap.put("Audios", listAdDTOAudios);

        return adByMultimediaMap;
    }

    /**
     * Cuenta el total de anuncios en la base de datos.
     *
     * @return el numero total de anuncios.
     */
    public Integer countAllAds() {
        return adRepository.countAllAds();
    }

    /**
     * Recupera el numero de anuncios que hay para cada elemento de cada categoria
     *
     * @return un mapa con sub-mapas que contienen las cuentas de anuncios por cada
     *         categoria especificada.
     */
    public Map<String, Map<String, Integer>> getAllCounts() {

        Map<String, Map<String, Integer>> mapAllCounts = new HashMap<>();

        Map<String, Integer> genderRelationCounts = adRepository.countAdsByGenderRelation();

        Map<String, Integer> maleRoleCounts = adRepository.countAdsByMaleRole();

        Map<String, Integer> femaleRoleCounts = adRepository.countAdsByFemaleRole();

        Map<String, Integer> childRoleCounts = adRepository.countAdsByChildRole();

        Map<String, Integer> childGenderCounts = adRepository.countAdsByChildGender();

        Map<String, Integer> protagonismCounts = adRepository.countAdsByProtagonism();

        Map<String, Integer> topicCounts = adRepository.countAdsByTopic();

        Map<String, Integer> mediaCounts = adRepository.countAdsByMedia();

        Map<String, Integer> mediaTypeCounts = adRepository.countAdsByMediaType();

        Map<String, Integer> advertisingTypeCounts = adRepository.countAdsByAdvertisingType();

        Map<String, Integer> yearCounts = adRepository.countAdsByDecade();

        mapAllCounts.put("RELACION GENERO", genderRelationCounts);
        mapAllCounts.put("ROL HOMBRE", maleRoleCounts);
        mapAllCounts.put("ROL MUJER", femaleRoleCounts);
        mapAllCounts.put("ROL MENOR", childRoleCounts);
        mapAllCounts.put("GENERO MENOR", childGenderCounts);
        mapAllCounts.put("PROTAGONISMO", protagonismCounts);
        mapAllCounts.put("TEMATICA", topicCounts);
        mapAllCounts.put("MEDIO", mediaCounts);
        mapAllCounts.put("TIPO MEDIO", mediaTypeCounts);
        mapAllCounts.put("TIPO PUBLICIDAD", advertisingTypeCounts);
        mapAllCounts.put("ANIO", yearCounts);

        return mapAllCounts;
    }

    /**
     * Almacena/actualiza un anuncio en base de datos, junto a su archivo
     * multimedia.
     * Tambien almacena las nuevas categorias que se agregan con el anuncio
     *
     * @param ad               AdDTO del anuncio.
     * @param keyWordsSelected las palabras clave del anuncio.
     * @param file             el archivo multimedia del anuncio.
     * 
     * @return el AdDO que se almacena en bbdd.
     */
    public AdDO saveAd(AdDTO ad, List<String> keyWordsSelected, MultipartFile file) {
        Boolean checkChildGender2 = Boolean.FALSE;
        Boolean checkChildGender3 = Boolean.FALSE;

        // Eliminar la coma al final de las categorias
        ad.setChildRole(ad.getChildRole().replaceAll(",$", ""));
        if (ad.getChildGender2() != null && !ad.getChildGender2().isEmpty()) {
            ad.setChildRole2(ad.getChildRole2().replaceAll(",$", ""));
            checkChildGender2 = Boolean.TRUE;
        }

        if (ad.getChildGender3() != null && !ad.getChildGender3().isEmpty()) {
            ad.setChildRole3(ad.getChildRole3().replaceAll(",$", ""));
            checkChildGender3 = Boolean.TRUE;
        }

        ad.setGenderRoleMale(ad.getGenderRoleMale().replaceAll(",$", ""));
        ad.setGenderRoleFemale(ad.getGenderRoleFemale().replaceAll(",$", ""));
        ad.setMediaName(ad.getMediaName().replaceAll(",$", ""));
        ad.setMedia(ad.getMedia().replaceAll(",$", ""));
        ad.setTopicName(ad.getTopicName().replaceAll(",$", ""));
        ad.setGenderRelation(ad.getGenderRelation().replaceAll(",$", ""));

        ChildRoleTypeDO childRoleType = childRoleTypeService
                .getDOByRole(ad.getChildRole());

        ChildRoleTypeDO childRoleType2 = null;
        if (checkChildGender2) {
            childRoleType2 = childRoleTypeService
                    .getDOByRole(ad.getChildRole2());
        }

        ChildRoleTypeDO childRoleType3 = null;
        if (checkChildGender3) {
            childRoleType3 = childRoleTypeService
                    .getDOByRole(ad.getChildRole3());
        }

        GenderRoleMaleTypeDO genderRoleMaleType = genderRoleMaleTypeService
                .getDOByRole(ad.getGenderRoleMale());
        GenderRoleFemaleTypeDO genderRoleFemaleType = genderRoleFemaleTypeService
                .getDOByRole(ad.getGenderRoleFemale());
        MediaDO media = mediaService.getDOByName(ad.getMediaName());
        MediaTypeDO mediaType = mediaTypeService.getDOByType(ad.getMedia());

        List<String> keyWordsList = keyWordsService.getWordsByList(keyWordsSelected);

        TopicDO topicDO = topicService.getDOByName(ad.getTopicName());

        UserDO userDO = null;

        // Buscamos en bbdd si el anuncio tiene usuario asignado, si no ponemos el de la
        // sesion
        if (ad.getUserEmail() != null && !ad.getUserEmail().isEmpty()) {
            userDO = userService.getDOByEmail(ad.getUserEmail());
        } else {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName();
            userDO = userService.getDOByEmail(username);
        }

        AdDO editedAD = new AdDO();
        editedAD.fromDTO(ad, userDO);
        if (childRoleType == null) {
            childRoleType = childRoleTypeService.save(ad.getChildRole());
        }
        editedAD.setChildRoleTypeDO(childRoleType);

        if (childRoleType2 == null && checkChildGender2) {
            childRoleType2 = childRoleTypeService.save(ad.getChildRole2());
        }
        editedAD.setChildRoleTypeDO2(childRoleType2);

        if (childRoleType3 == null && checkChildGender3) {
            childRoleType3 = childRoleTypeService.save(ad.getChildRole3());
        }
        editedAD.setChildRoleTypeDO3(childRoleType3);

        if (genderRoleMaleType == null) {
            genderRoleMaleType = genderRoleMaleTypeService.save(ad.getGenderRoleMale());
        }

        editedAD.setGenderRoleMaleTypeDO(genderRoleMaleType);

        if (genderRoleFemaleType == null) {
            genderRoleFemaleType = genderRoleFemaleTypeService.save(ad.getGenderRoleFemale());
        }

        editedAD.setGenderRoleFemaleTypeDO(genderRoleFemaleType);

        if (media == null) {
            media = mediaService.save(ad.getMediaName());
        }

        editedAD.setMediaDO(media);

        if (mediaType == null) {
            mediaType = mediaTypeService.save(ad.getMedia());
        }

        editedAD.setMediaTypeDO(mediaType);

        if (topicDO == null) {
            topicDO = topicService.save(ad.getTopicName());
        }

        // Si hay alguna palabra clave que no esta en bbdd, la agregamos
        if (keyWordsSelected != null && !keyWordsSelected.isEmpty()) {
            // Ponemos todas las palabras con la inicial en mayuscula
            List<String> capitalizedWords = keyWordsSelected.stream()
                    .filter(s -> s != null && !s.isEmpty())
                    .map(s -> s.substring(0, 1).toUpperCase() + s.substring(1))
                    .collect(Collectors.toList());

            // Guarda las palabras clave que no existen en keyWordsList
            for (String keyWord : capitalizedWords) {
                if (keyWordsList != null && !keyWordsList.isEmpty() && !keyWord.isEmpty()
                        && !keyWordsList.contains(keyWord)) {
                    keyWordsService.save(keyWord);
                }
            }

            editedAD.setKeyWords(String.join(", ", capitalizedWords));
        }

        editedAD.setTopicDO(topicDO);

        // Guardamos el archivo del anuncio en el servidor y la ruta en bbdd
        // Eliminamos tambien el anterior archivo del anuncio
        if (file != null && !file.isEmpty()) {
            String rutaArchivo = fileStorageService.store(file);
            if (editedAD.getId() != null) {
                AdDO adDOAux = this.getAdDOById(editedAD.getId());
                fileStorageService.delete(adDOAux.getMediaUrl());
            }
            editedAD.setMediaUrl(rutaArchivo);
        } else if (editedAD.getId() != null) {
            // Si no le hemos pasado un archivo, le asignamos el que ya estaba en bbdd
            AdDO adDOAux = this.getAdDOById(editedAD.getId());
            editedAD.setMediaUrl(adDOAux.getMediaUrl());
        }

        this.update(editedAD);

        return editedAD;
    }
}