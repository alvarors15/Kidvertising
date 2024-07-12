package com.example.Mediateca.Services;

import com.example.Mediateca.DTOs.MediaDTO;
import com.example.Mediateca.Domain.MediaDO;
import com.example.Mediateca.Repositories.MediaRepository.MediaRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Servicio para la gestion del medio de los anuncios.
 */
@Service
@Transactional
public class MediaService {

    @Autowired
    private MediaRepository mediaRepository;

    /**
     * Recupera todos los medios de la base de datos
     *
     * @return una lista de MediaDTO.
     */
    public List<MediaDTO> getAll() {
        List<MediaDO> mediaDOList = mediaRepository.findAll();
        List<MediaDTO> listMediaDTO = new ArrayList<MediaDTO>();
        for (MediaDO mediaDO : mediaDOList) {
            MediaDTO mediaDTO = new MediaDTO();
            mediaDTO.fromDO(mediaDO);
            listMediaDTO.add(mediaDTO);
        }
        return listMediaDTO;
    }

    /**
     * Obtiene el medio basado en el nombre del medio.
     *
     * @param name el nombre del medio a buscar en bbdd
     * @return un MediaDO correspondiente al medio buscado, o null si
     *         no se encuentra.
     */
    public MediaDO getDOByName(String name) {
        MediaDO mediaDO = mediaRepository.findByName(name);
        if (mediaDO == null) {
            return null;
        }
        return mediaDO;
    }

    /**
     * Crea un nuevo medio en la base de datos.
     *
     * @param name el nombre del medio.
     * @return el MediaDO creado.
     */
    @Transactional
    public MediaDO save(String name) {
        MediaDO mediaDO = new MediaDO();
        mediaDO.setMediaName(name);
        return mediaRepository.save(mediaDO);
    }

    /**
     * Elimina una lista de medios por sus identificadores.
     *
     * @param idList la lista de identificadores de los medios a
     *               eliminar.
     */
    @SuppressWarnings("null")
    public void deleteByIdList(List<Integer> idList) {
        mediaRepository.deleteByIdList(idList);
    }
}