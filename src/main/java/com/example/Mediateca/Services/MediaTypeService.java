package com.example.Mediateca.Services;

import com.example.Mediateca.DTOs.MediaTypeDTO;
import com.example.Mediateca.Domain.MediaTypeDO;
import com.example.Mediateca.Repositories.MediaTypeRepository.MediaTypeRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Servicio para la gestion del tipo de medio de comunicacion de los anuncios.
 */
@Service
@Transactional
public class MediaTypeService {

    @Autowired
    private MediaTypeRepository mediaTypeRepository;

    /**
     * Recupera todos los tipos de medios de comunicacion de la base de datos
     *
     * @return una lista de MediaTypeDTO.
     */
    public List<MediaTypeDTO> getAll() {
        List<MediaTypeDO> mediaTypeDOList = mediaTypeRepository.findAll();
        List<MediaTypeDTO> listMediaTypeDTO = new ArrayList<MediaTypeDTO>();
        for (MediaTypeDO mediaTypeDO : mediaTypeDOList) {
            MediaTypeDTO mediaTypeDTO = new MediaTypeDTO();
            mediaTypeDTO.fromDO(mediaTypeDO);
            listMediaTypeDTO.add(mediaTypeDTO);
        }
        return listMediaTypeDTO;
    }

    /**
     * Obtiene el tipo de medio de comunicacion basado en el tipo del medio de
     * comunicacion.
     *
     * @param type el tipo del medio de comunicacion a buscar en bbdd
     * @return un MediaTypeDO correspondiente al tipo de medio de comunicacion
     *         buscado, o null si no se encuentra.
     */
    public MediaTypeDO getDOByType(String type) {
        MediaTypeDO mediaTypeDO = mediaTypeRepository.findByType(type);
        if (mediaTypeDO == null) {
            return null;
        }
        return mediaTypeDO;
    }

    /**
     * Crea un nuevo tipo de medio de comunicacion en la base de datos.
     *
     * @param type el tipo de medio de comunicacion.
     * @return el MediaTypeDO creado.
     */
    @Transactional
    public MediaTypeDO save(String type) {
        MediaTypeDO mediaTypeDO = new MediaTypeDO();
        mediaTypeDO.setMediaType(type);
        return mediaTypeRepository.save(mediaTypeDO);
    }

    /**
     * Elimina una lista de tipos de medios de comunicacion por sus identificadores.
     *
     * @param idList la lista de identificadores de los tipos de medios de
     *               comunicacion a
     *               eliminar.
     */
    @SuppressWarnings("null")
    public void deleteByIdList(List<Integer> idList) {
        mediaTypeRepository.deleteByIdList(idList);
    }
}